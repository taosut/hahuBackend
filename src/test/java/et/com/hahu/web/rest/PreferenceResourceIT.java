package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Preference;
import et.com.hahu.domain.User;
import et.com.hahu.domain.Category;
import et.com.hahu.repository.PreferenceRepository;
import et.com.hahu.service.PreferenceService;
import et.com.hahu.service.dto.PreferenceDTO;
import et.com.hahu.service.mapper.PreferenceMapper;
import et.com.hahu.service.dto.PreferenceCriteria;
import et.com.hahu.service.PreferenceQueryService;

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
 * Integration tests for the {@link PreferenceResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PreferenceResourceIT {

    private static final Boolean DEFAULT_HAS_PREFERECE_SELECTED = false;
    private static final Boolean UPDATED_HAS_PREFERECE_SELECTED = true;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Mock
    private PreferenceRepository preferenceRepositoryMock;

    @Autowired
    private PreferenceMapper preferenceMapper;

    @Mock
    private PreferenceService preferenceServiceMock;

    @Autowired
    private PreferenceService preferenceService;

    @Autowired
    private PreferenceQueryService preferenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreferenceMockMvc;

    private Preference preference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preference createEntity(EntityManager em) {
        Preference preference = new Preference()
            .hasPrefereceSelected(DEFAULT_HAS_PREFERECE_SELECTED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        preference.setUser(user);
        return preference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Preference createUpdatedEntity(EntityManager em) {
        Preference preference = new Preference()
            .hasPrefereceSelected(UPDATED_HAS_PREFERECE_SELECTED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        preference.setUser(user);
        return preference;
    }

    @BeforeEach
    public void initTest() {
        preference = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreference() throws Exception {
        int databaseSizeBeforeCreate = preferenceRepository.findAll().size();
        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);
        restPreferenceMockMvc.perform(post("/api/preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeCreate + 1);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
        assertThat(testPreference.isHasPrefereceSelected()).isEqualTo(DEFAULT_HAS_PREFERECE_SELECTED);

        // Validate the id for MapsId, the ids must be same
        assertThat(testPreference.getId()).isEqualTo(testPreference.getUser().getId());
    }

    @Test
    @Transactional
    public void createPreferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preferenceRepository.findAll().size();

        // Create the Preference with an existing ID
        preference.setId(1L);
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreferenceMockMvc.perform(post("/api/preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updatePreferenceMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);
        int databaseSizeBeforeCreate = preferenceRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the preference
        Preference updatedPreference = preferenceRepository.findById(preference.getId()).get();
        // Disconnect from session so that the updates on updatedPreference are not directly saved in db
        em.detach(updatedPreference);

        // Update the User with new association value
        updatedPreference.setUser(user);
        PreferenceDTO updatedPreferenceDTO = preferenceMapper.toDto(updatedPreference);

        // Update the entity
        restPreferenceMockMvc.perform(put("/api/preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreferenceDTO)))
            .andExpect(status().isOk());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeCreate);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testPreference.getId()).isEqualTo(testPreference.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllPreferences() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get all the preferenceList
        restPreferenceMockMvc.perform(get("/api/preferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preference.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasPrefereceSelected").value(hasItem(DEFAULT_HAS_PREFERECE_SELECTED.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPreferencesWithEagerRelationshipsIsEnabled() throws Exception {
        when(preferenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPreferenceMockMvc.perform(get("/api/preferences?eagerload=true"))
            .andExpect(status().isOk());

        verify(preferenceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPreferencesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(preferenceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPreferenceMockMvc.perform(get("/api/preferences?eagerload=true"))
            .andExpect(status().isOk());

        verify(preferenceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get the preference
        restPreferenceMockMvc.perform(get("/api/preferences/{id}", preference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preference.getId().intValue()))
            .andExpect(jsonPath("$.hasPrefereceSelected").value(DEFAULT_HAS_PREFERECE_SELECTED.booleanValue()));
    }


    @Test
    @Transactional
    public void getPreferencesByIdFiltering() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        Long id = preference.getId();

        defaultPreferenceShouldBeFound("id.equals=" + id);
        defaultPreferenceShouldNotBeFound("id.notEquals=" + id);

        defaultPreferenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPreferenceShouldNotBeFound("id.greaterThan=" + id);

        defaultPreferenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPreferenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPreferencesByHasPrefereceSelectedIsEqualToSomething() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get all the preferenceList where hasPrefereceSelected equals to DEFAULT_HAS_PREFERECE_SELECTED
        defaultPreferenceShouldBeFound("hasPrefereceSelected.equals=" + DEFAULT_HAS_PREFERECE_SELECTED);

        // Get all the preferenceList where hasPrefereceSelected equals to UPDATED_HAS_PREFERECE_SELECTED
        defaultPreferenceShouldNotBeFound("hasPrefereceSelected.equals=" + UPDATED_HAS_PREFERECE_SELECTED);
    }

    @Test
    @Transactional
    public void getAllPreferencesByHasPrefereceSelectedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get all the preferenceList where hasPrefereceSelected not equals to DEFAULT_HAS_PREFERECE_SELECTED
        defaultPreferenceShouldNotBeFound("hasPrefereceSelected.notEquals=" + DEFAULT_HAS_PREFERECE_SELECTED);

        // Get all the preferenceList where hasPrefereceSelected not equals to UPDATED_HAS_PREFERECE_SELECTED
        defaultPreferenceShouldBeFound("hasPrefereceSelected.notEquals=" + UPDATED_HAS_PREFERECE_SELECTED);
    }

    @Test
    @Transactional
    public void getAllPreferencesByHasPrefereceSelectedIsInShouldWork() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get all the preferenceList where hasPrefereceSelected in DEFAULT_HAS_PREFERECE_SELECTED or UPDATED_HAS_PREFERECE_SELECTED
        defaultPreferenceShouldBeFound("hasPrefereceSelected.in=" + DEFAULT_HAS_PREFERECE_SELECTED + "," + UPDATED_HAS_PREFERECE_SELECTED);

        // Get all the preferenceList where hasPrefereceSelected equals to UPDATED_HAS_PREFERECE_SELECTED
        defaultPreferenceShouldNotBeFound("hasPrefereceSelected.in=" + UPDATED_HAS_PREFERECE_SELECTED);
    }

    @Test
    @Transactional
    public void getAllPreferencesByHasPrefereceSelectedIsNullOrNotNull() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        // Get all the preferenceList where hasPrefereceSelected is not null
        defaultPreferenceShouldBeFound("hasPrefereceSelected.specified=true");

        // Get all the preferenceList where hasPrefereceSelected is null
        defaultPreferenceShouldNotBeFound("hasPrefereceSelected.specified=false");
    }

    @Test
    @Transactional
    public void getAllPreferencesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = preference.getUser();
        preferenceRepository.saveAndFlush(preference);
        Long userId = user.getId();

        // Get all the preferenceList where user equals to userId
        defaultPreferenceShouldBeFound("userId.equals=" + userId);

        // Get all the preferenceList where user equals to userId + 1
        defaultPreferenceShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllPreferencesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);
        Category category = CategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        preference.addCategory(category);
        preferenceRepository.saveAndFlush(preference);
        Long categoryId = category.getId();

        // Get all the preferenceList where category equals to categoryId
        defaultPreferenceShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the preferenceList where category equals to categoryId + 1
        defaultPreferenceShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPreferenceShouldBeFound(String filter) throws Exception {
        restPreferenceMockMvc.perform(get("/api/preferences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preference.getId().intValue())))
            .andExpect(jsonPath("$.[*].hasPrefereceSelected").value(hasItem(DEFAULT_HAS_PREFERECE_SELECTED.booleanValue())));

        // Check, that the count call also returns 1
        restPreferenceMockMvc.perform(get("/api/preferences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPreferenceShouldNotBeFound(String filter) throws Exception {
        restPreferenceMockMvc.perform(get("/api/preferences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPreferenceMockMvc.perform(get("/api/preferences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPreference() throws Exception {
        // Get the preference
        restPreferenceMockMvc.perform(get("/api/preferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();

        // Update the preference
        Preference updatedPreference = preferenceRepository.findById(preference.getId()).get();
        // Disconnect from session so that the updates on updatedPreference are not directly saved in db
        em.detach(updatedPreference);
        updatedPreference
            .hasPrefereceSelected(UPDATED_HAS_PREFERECE_SELECTED);
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(updatedPreference);

        restPreferenceMockMvc.perform(put("/api/preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preferenceDTO)))
            .andExpect(status().isOk());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
        Preference testPreference = preferenceList.get(preferenceList.size() - 1);
        assertThat(testPreference.isHasPrefereceSelected()).isEqualTo(UPDATED_HAS_PREFERECE_SELECTED);
    }

    @Test
    @Transactional
    public void updateNonExistingPreference() throws Exception {
        int databaseSizeBeforeUpdate = preferenceRepository.findAll().size();

        // Create the Preference
        PreferenceDTO preferenceDTO = preferenceMapper.toDto(preference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreferenceMockMvc.perform(put("/api/preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Preference in the database
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePreference() throws Exception {
        // Initialize the database
        preferenceRepository.saveAndFlush(preference);

        int databaseSizeBeforeDelete = preferenceRepository.findAll().size();

        // Delete the preference
        restPreferenceMockMvc.perform(delete("/api/preferences/{id}", preference.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Preference> preferenceList = preferenceRepository.findAll();
        assertThat(preferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
