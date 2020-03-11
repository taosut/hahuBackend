package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.UsersConnection;
import et.com.hahu.domain.AdditionalUserInfo;
import et.com.hahu.repository.UsersConnectionRepository;
import et.com.hahu.service.UsersConnectionService;
import et.com.hahu.service.dto.UsersConnectionDTO;
import et.com.hahu.service.mapper.UsersConnectionMapper;
import et.com.hahu.service.dto.UsersConnectionCriteria;
import et.com.hahu.service.UsersConnectionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UsersConnectionResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class UsersConnectionResourceIT {

    private static final Instant DEFAULT_REGISTERED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTERED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private UsersConnectionMapper usersConnectionMapper;

    @Autowired
    private UsersConnectionService usersConnectionService;

    @Autowired
    private UsersConnectionQueryService usersConnectionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsersConnectionMockMvc;

    private UsersConnection usersConnection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsersConnection createEntity(EntityManager em) {
        UsersConnection usersConnection = new UsersConnection()
            .registeredTime(DEFAULT_REGISTERED_TIME);
        return usersConnection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsersConnection createUpdatedEntity(EntityManager em) {
        UsersConnection usersConnection = new UsersConnection()
            .registeredTime(UPDATED_REGISTERED_TIME);
        return usersConnection;
    }

    @BeforeEach
    public void initTest() {
        usersConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsersConnection() throws Exception {
        int databaseSizeBeforeCreate = usersConnectionRepository.findAll().size();

        // Create the UsersConnection
        UsersConnectionDTO usersConnectionDTO = usersConnectionMapper.toDto(usersConnection);
        restUsersConnectionMockMvc.perform(post("/api/users-connections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersConnectionDTO)))
            .andExpect(status().isCreated());

        // Validate the UsersConnection in the database
        List<UsersConnection> usersConnectionList = usersConnectionRepository.findAll();
        assertThat(usersConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        UsersConnection testUsersConnection = usersConnectionList.get(usersConnectionList.size() - 1);
        assertThat(testUsersConnection.getRegisteredTime()).isEqualTo(DEFAULT_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void createUsersConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersConnectionRepository.findAll().size();

        // Create the UsersConnection with an existing ID
        usersConnection.setId(1L);
        UsersConnectionDTO usersConnectionDTO = usersConnectionMapper.toDto(usersConnection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersConnectionMockMvc.perform(post("/api/users-connections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsersConnection in the database
        List<UsersConnection> usersConnectionList = usersConnectionRepository.findAll();
        assertThat(usersConnectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUsersConnections() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        // Get all the usersConnectionList
        restUsersConnectionMockMvc.perform(get("/api/users-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usersConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].registeredTime").value(hasItem(DEFAULT_REGISTERED_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getUsersConnection() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        // Get the usersConnection
        restUsersConnectionMockMvc.perform(get("/api/users-connections/{id}", usersConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usersConnection.getId().intValue()))
            .andExpect(jsonPath("$.registeredTime").value(DEFAULT_REGISTERED_TIME.toString()));
    }


    @Test
    @Transactional
    public void getUsersConnectionsByIdFiltering() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        Long id = usersConnection.getId();

        defaultUsersConnectionShouldBeFound("id.equals=" + id);
        defaultUsersConnectionShouldNotBeFound("id.notEquals=" + id);

        defaultUsersConnectionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsersConnectionShouldNotBeFound("id.greaterThan=" + id);

        defaultUsersConnectionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsersConnectionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUsersConnectionsByRegisteredTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        // Get all the usersConnectionList where registeredTime equals to DEFAULT_REGISTERED_TIME
        defaultUsersConnectionShouldBeFound("registeredTime.equals=" + DEFAULT_REGISTERED_TIME);

        // Get all the usersConnectionList where registeredTime equals to UPDATED_REGISTERED_TIME
        defaultUsersConnectionShouldNotBeFound("registeredTime.equals=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllUsersConnectionsByRegisteredTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        // Get all the usersConnectionList where registeredTime not equals to DEFAULT_REGISTERED_TIME
        defaultUsersConnectionShouldNotBeFound("registeredTime.notEquals=" + DEFAULT_REGISTERED_TIME);

        // Get all the usersConnectionList where registeredTime not equals to UPDATED_REGISTERED_TIME
        defaultUsersConnectionShouldBeFound("registeredTime.notEquals=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllUsersConnectionsByRegisteredTimeIsInShouldWork() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        // Get all the usersConnectionList where registeredTime in DEFAULT_REGISTERED_TIME or UPDATED_REGISTERED_TIME
        defaultUsersConnectionShouldBeFound("registeredTime.in=" + DEFAULT_REGISTERED_TIME + "," + UPDATED_REGISTERED_TIME);

        // Get all the usersConnectionList where registeredTime equals to UPDATED_REGISTERED_TIME
        defaultUsersConnectionShouldNotBeFound("registeredTime.in=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllUsersConnectionsByRegisteredTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        // Get all the usersConnectionList where registeredTime is not null
        defaultUsersConnectionShouldBeFound("registeredTime.specified=true");

        // Get all the usersConnectionList where registeredTime is null
        defaultUsersConnectionShouldNotBeFound("registeredTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllUsersConnectionsByFollowerIsEqualToSomething() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);
        AdditionalUserInfo follower = AdditionalUserInfoResourceIT.createEntity(em);
        em.persist(follower);
        em.flush();
        usersConnection.setFollower(follower);
        usersConnectionRepository.saveAndFlush(usersConnection);
        Long followerId = follower.getId();

        // Get all the usersConnectionList where follower equals to followerId
        defaultUsersConnectionShouldBeFound("followerId.equals=" + followerId);

        // Get all the usersConnectionList where follower equals to followerId + 1
        defaultUsersConnectionShouldNotBeFound("followerId.equals=" + (followerId + 1));
    }


    @Test
    @Transactional
    public void getAllUsersConnectionsByFollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);
        AdditionalUserInfo following = AdditionalUserInfoResourceIT.createEntity(em);
        em.persist(following);
        em.flush();
        usersConnection.setFollowing(following);
        usersConnectionRepository.saveAndFlush(usersConnection);
        Long followingId = following.getId();

        // Get all the usersConnectionList where following equals to followingId
        defaultUsersConnectionShouldBeFound("followingId.equals=" + followingId);

        // Get all the usersConnectionList where following equals to followingId + 1
        defaultUsersConnectionShouldNotBeFound("followingId.equals=" + (followingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsersConnectionShouldBeFound(String filter) throws Exception {
        restUsersConnectionMockMvc.perform(get("/api/users-connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usersConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].registeredTime").value(hasItem(DEFAULT_REGISTERED_TIME.toString())));

        // Check, that the count call also returns 1
        restUsersConnectionMockMvc.perform(get("/api/users-connections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsersConnectionShouldNotBeFound(String filter) throws Exception {
        restUsersConnectionMockMvc.perform(get("/api/users-connections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsersConnectionMockMvc.perform(get("/api/users-connections/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUsersConnection() throws Exception {
        // Get the usersConnection
        restUsersConnectionMockMvc.perform(get("/api/users-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsersConnection() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        int databaseSizeBeforeUpdate = usersConnectionRepository.findAll().size();

        // Update the usersConnection
        UsersConnection updatedUsersConnection = usersConnectionRepository.findById(usersConnection.getId()).get();
        // Disconnect from session so that the updates on updatedUsersConnection are not directly saved in db
        em.detach(updatedUsersConnection);
        updatedUsersConnection
            .registeredTime(UPDATED_REGISTERED_TIME);
        UsersConnectionDTO usersConnectionDTO = usersConnectionMapper.toDto(updatedUsersConnection);

        restUsersConnectionMockMvc.perform(put("/api/users-connections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersConnectionDTO)))
            .andExpect(status().isOk());

        // Validate the UsersConnection in the database
        List<UsersConnection> usersConnectionList = usersConnectionRepository.findAll();
        assertThat(usersConnectionList).hasSize(databaseSizeBeforeUpdate);
        UsersConnection testUsersConnection = usersConnectionList.get(usersConnectionList.size() - 1);
        assertThat(testUsersConnection.getRegisteredTime()).isEqualTo(UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingUsersConnection() throws Exception {
        int databaseSizeBeforeUpdate = usersConnectionRepository.findAll().size();

        // Create the UsersConnection
        UsersConnectionDTO usersConnectionDTO = usersConnectionMapper.toDto(usersConnection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersConnectionMockMvc.perform(put("/api/users-connections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usersConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsersConnection in the database
        List<UsersConnection> usersConnectionList = usersConnectionRepository.findAll();
        assertThat(usersConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsersConnection() throws Exception {
        // Initialize the database
        usersConnectionRepository.saveAndFlush(usersConnection);

        int databaseSizeBeforeDelete = usersConnectionRepository.findAll().size();

        // Delete the usersConnection
        restUsersConnectionMockMvc.perform(delete("/api/users-connections/{id}", usersConnection.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsersConnection> usersConnectionList = usersConnectionRepository.findAll();
        assertThat(usersConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
