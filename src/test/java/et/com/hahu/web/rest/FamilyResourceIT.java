package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Family;
import et.com.hahu.domain.User;
import et.com.hahu.repository.FamilyRepository;
import et.com.hahu.service.FamilyService;
import et.com.hahu.service.dto.FamilyDTO;
import et.com.hahu.service.mapper.FamilyMapper;
import et.com.hahu.service.dto.FamilyCriteria;
import et.com.hahu.service.FamilyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FamilyResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FamilyResourceIT {

    private static final Boolean DEFAULT_HAS_FAMILY_CONTROL = false;
    private static final Boolean UPDATED_HAS_FAMILY_CONTROL = true;

    @Autowired
    private FamilyRepository familyRepository;

    @Mock
    private FamilyRepository familyRepositoryMock;

    @Autowired
    private FamilyMapper familyMapper;

    @Mock
    private FamilyService familyServiceMock;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private FamilyQueryService familyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyMockMvc;

    private Family family;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Family createEntity(EntityManager em) {
        Family family = new Family()
            .hasFamilyControl(DEFAULT_HAS_FAMILY_CONTROL);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        family.setUser(user);
        return family;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Family createUpdatedEntity(EntityManager em) {
        Family family = new Family()
            .hasFamilyControl(UPDATED_HAS_FAMILY_CONTROL);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        family.setUser(user);
        return family;
    }

    @BeforeEach
    public void initTest() {
        family = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamily() throws Exception {
        int databaseSizeBeforeCreate = familyRepository.findAll().size();
        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);
        restFamilyMockMvc.perform(post("/api/families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isCreated());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeCreate + 1);
        Family testFamily = familyList.get(familyList.size() - 1);
        assertThat(testFamily.isHasFamilyControl()).isEqualTo(DEFAULT_HAS_FAMILY_CONTROL);

        // Validate the id for MapsId, the ids must be same
        assertThat(testFamily.getId()).isEqualTo(testFamily.getUser().getId());
    }

    @Test
    @Transactional
    public void createFamilyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familyRepository.findAll().size();

        // Create the Family with an existing ID
        family.setId(1L);
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyMockMvc.perform(post("/api/families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateFamilyMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);
        int databaseSizeBeforeCreate = familyRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the family
        Family updatedFamily = familyRepository.findById(family.getId()).get();
        // Disconnect from session so that the updates on updatedFamily are not directly saved in db
        em.detach(updatedFamily);

        // Update the User with new association value
        updatedFamily.setUser(user);
        FamilyDTO updatedFamilyDTO = familyMapper.toDto(updatedFamily);

        // Update the entity
        restFamilyMockMvc.perform(put("/api/families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamilyDTO)))
            .andExpect(status().isOk());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeCreate);
        Family testFamily = familyList.get(familyList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testFamily.getId()).isEqualTo(testFamily.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllFamilies() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get all the familyList
        restFamilyMockMvc.perform(get("/api/families?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(family.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasFamilyControl").value(hasItem(DEFAULT_HAS_FAMILY_CONTROL.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFamiliesWithEagerRelationshipsIsEnabled() throws Exception {
        when(familyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFamilyMockMvc.perform(get("/api/families?eagerload=true"))
            .andExpect(status().isOk());

        verify(familyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFamiliesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(familyServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFamilyMockMvc.perform(get("/api/families?eagerload=true"))
            .andExpect(status().isOk());

        verify(familyServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get the family
        restFamilyMockMvc.perform(get("/api/families/{id}", family.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(family.getId().intValue()))
            .andExpect(jsonPath("$.hasFamilyControl").value(DEFAULT_HAS_FAMILY_CONTROL.booleanValue()));
    }


    @Test
    @Transactional
    public void getFamiliesByIdFiltering() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        Long id = family.getId();

        defaultFamilyShouldBeFound("id.equals=" + id);
        defaultFamilyShouldNotBeFound("id.notEquals=" + id);

        defaultFamilyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFamilyShouldNotBeFound("id.greaterThan=" + id);

        defaultFamilyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFamilyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFamiliesByHasFamilyControlIsEqualToSomething() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get all the familyList where hasFamilyControl equals to DEFAULT_HAS_FAMILY_CONTROL
        defaultFamilyShouldBeFound("hasFamilyControl.equals=" + DEFAULT_HAS_FAMILY_CONTROL);

        // Get all the familyList where hasFamilyControl equals to UPDATED_HAS_FAMILY_CONTROL
        defaultFamilyShouldNotBeFound("hasFamilyControl.equals=" + UPDATED_HAS_FAMILY_CONTROL);
    }

    @Test
    @Transactional
    public void getAllFamiliesByHasFamilyControlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get all the familyList where hasFamilyControl not equals to DEFAULT_HAS_FAMILY_CONTROL
        defaultFamilyShouldNotBeFound("hasFamilyControl.notEquals=" + DEFAULT_HAS_FAMILY_CONTROL);

        // Get all the familyList where hasFamilyControl not equals to UPDATED_HAS_FAMILY_CONTROL
        defaultFamilyShouldBeFound("hasFamilyControl.notEquals=" + UPDATED_HAS_FAMILY_CONTROL);
    }

    @Test
    @Transactional
    public void getAllFamiliesByHasFamilyControlIsInShouldWork() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get all the familyList where hasFamilyControl in DEFAULT_HAS_FAMILY_CONTROL or UPDATED_HAS_FAMILY_CONTROL
        defaultFamilyShouldBeFound("hasFamilyControl.in=" + DEFAULT_HAS_FAMILY_CONTROL + "," + UPDATED_HAS_FAMILY_CONTROL);

        // Get all the familyList where hasFamilyControl equals to UPDATED_HAS_FAMILY_CONTROL
        defaultFamilyShouldNotBeFound("hasFamilyControl.in=" + UPDATED_HAS_FAMILY_CONTROL);
    }

    @Test
    @Transactional
    public void getAllFamiliesByHasFamilyControlIsNullOrNotNull() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        // Get all the familyList where hasFamilyControl is not null
        defaultFamilyShouldBeFound("hasFamilyControl.specified=true");

        // Get all the familyList where hasFamilyControl is null
        defaultFamilyShouldNotBeFound("hasFamilyControl.specified=false");
    }

    @Test
    @Transactional
    public void getAllFamiliesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = family.getUser();
        familyRepository.saveAndFlush(family);
        Long userId = user.getId();

        // Get all the familyList where user equals to userId
        defaultFamilyShouldBeFound("userId.equals=" + userId);

        // Get all the familyList where user equals to userId + 1
        defaultFamilyShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllFamiliesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);
        User parent = UserResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        family.addParent(parent);
        familyRepository.saveAndFlush(family);
        Long parentId = parent.getId();

        // Get all the familyList where parent equals to parentId
        defaultFamilyShouldBeFound("parentId.equals=" + parentId);

        // Get all the familyList where parent equals to parentId + 1
        defaultFamilyShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFamilyShouldBeFound(String filter) throws Exception {
        restFamilyMockMvc.perform(get("/api/families?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(family.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasFamilyControl").value(hasItem(DEFAULT_HAS_FAMILY_CONTROL.booleanValue())));

        // Check, that the count call also returns 1
        restFamilyMockMvc.perform(get("/api/families/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFamilyShouldNotBeFound(String filter) throws Exception {
        restFamilyMockMvc.perform(get("/api/families?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFamilyMockMvc.perform(get("/api/families/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFamily() throws Exception {
        // Get the family
        restFamilyMockMvc.perform(get("/api/families/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        int databaseSizeBeforeUpdate = familyRepository.findAll().size();

        // Update the family
        Family updatedFamily = familyRepository.findById(family.getId()).get();
        // Disconnect from session so that the updates on updatedFamily are not directly saved in db
        em.detach(updatedFamily);
        updatedFamily
            .hasFamilyControl(UPDATED_HAS_FAMILY_CONTROL);
        FamilyDTO familyDTO = familyMapper.toDto(updatedFamily);

        restFamilyMockMvc.perform(put("/api/families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isOk());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
        Family testFamily = familyList.get(familyList.size() - 1);
        assertThat(testFamily.isHasFamilyControl()).isEqualTo(UPDATED_HAS_FAMILY_CONTROL);
    }

    @Test
    @Transactional
    public void updateNonExistingFamily() throws Exception {
        int databaseSizeBeforeUpdate = familyRepository.findAll().size();

        // Create the Family
        FamilyDTO familyDTO = familyMapper.toDto(family);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMockMvc.perform(put("/api/families")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(familyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Family in the database
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFamily() throws Exception {
        // Initialize the database
        familyRepository.saveAndFlush(family);

        int databaseSizeBeforeDelete = familyRepository.findAll().size();

        // Delete the family
        restFamilyMockMvc.perform(delete("/api/families/{id}", family.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Family> familyList = familyRepository.findAll();
        assertThat(familyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
