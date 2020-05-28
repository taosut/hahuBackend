package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Shares;
import et.com.hahu.domain.User;
import et.com.hahu.domain.Post;
import et.com.hahu.repository.SharesRepository;
import et.com.hahu.service.SharesService;
import et.com.hahu.service.dto.SharesDTO;
import et.com.hahu.service.mapper.SharesMapper;
import et.com.hahu.service.dto.SharesCriteria;
import et.com.hahu.service.SharesQueryService;

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
 * Integration tests for the {@link SharesResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SharesResourceIT {

    private static final Instant DEFAULT_REGISTERED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTERED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SharesRepository sharesRepository;

    @Autowired
    private SharesMapper sharesMapper;

    @Autowired
    private SharesService sharesService;

    @Autowired
    private SharesQueryService sharesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSharesMockMvc;

    private Shares shares;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shares createEntity(EntityManager em) {
        Shares shares = new Shares()
            .registeredTime(DEFAULT_REGISTERED_TIME);
        return shares;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shares createUpdatedEntity(EntityManager em) {
        Shares shares = new Shares()
            .registeredTime(UPDATED_REGISTERED_TIME);
        return shares;
    }

    @BeforeEach
    public void initTest() {
        shares = createEntity(em);
    }

    @Test
    @Transactional
    public void createShares() throws Exception {
        int databaseSizeBeforeCreate = sharesRepository.findAll().size();
        // Create the Shares
        SharesDTO sharesDTO = sharesMapper.toDto(shares);
        restSharesMockMvc.perform(post("/api/shares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharesDTO)))
            .andExpect(status().isCreated());

        // Validate the Shares in the database
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeCreate + 1);
        Shares testShares = sharesList.get(sharesList.size() - 1);
        assertThat(testShares.getRegisteredTime()).isEqualTo(DEFAULT_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void createSharesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sharesRepository.findAll().size();

        // Create the Shares with an existing ID
        shares.setId(1L);
        SharesDTO sharesDTO = sharesMapper.toDto(shares);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSharesMockMvc.perform(post("/api/shares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shares in the database
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShares() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        // Get all the sharesList
        restSharesMockMvc.perform(get("/api/shares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shares.getId().intValue())))
            .andExpect(jsonPath("$.[*].registeredTime").value(hasItem(DEFAULT_REGISTERED_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getShares() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        // Get the shares
        restSharesMockMvc.perform(get("/api/shares/{id}", shares.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shares.getId().intValue()))
            .andExpect(jsonPath("$.registeredTime").value(DEFAULT_REGISTERED_TIME.toString()));
    }


    @Test
    @Transactional
    public void getSharesByIdFiltering() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        Long id = shares.getId();

        defaultSharesShouldBeFound("id.equals=" + id);
        defaultSharesShouldNotBeFound("id.notEquals=" + id);

        defaultSharesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSharesShouldNotBeFound("id.greaterThan=" + id);

        defaultSharesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSharesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSharesByRegisteredTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        // Get all the sharesList where registeredTime equals to DEFAULT_REGISTERED_TIME
        defaultSharesShouldBeFound("registeredTime.equals=" + DEFAULT_REGISTERED_TIME);

        // Get all the sharesList where registeredTime equals to UPDATED_REGISTERED_TIME
        defaultSharesShouldNotBeFound("registeredTime.equals=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllSharesByRegisteredTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        // Get all the sharesList where registeredTime not equals to DEFAULT_REGISTERED_TIME
        defaultSharesShouldNotBeFound("registeredTime.notEquals=" + DEFAULT_REGISTERED_TIME);

        // Get all the sharesList where registeredTime not equals to UPDATED_REGISTERED_TIME
        defaultSharesShouldBeFound("registeredTime.notEquals=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllSharesByRegisteredTimeIsInShouldWork() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        // Get all the sharesList where registeredTime in DEFAULT_REGISTERED_TIME or UPDATED_REGISTERED_TIME
        defaultSharesShouldBeFound("registeredTime.in=" + DEFAULT_REGISTERED_TIME + "," + UPDATED_REGISTERED_TIME);

        // Get all the sharesList where registeredTime equals to UPDATED_REGISTERED_TIME
        defaultSharesShouldNotBeFound("registeredTime.in=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllSharesByRegisteredTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        // Get all the sharesList where registeredTime is not null
        defaultSharesShouldBeFound("registeredTime.specified=true");

        // Get all the sharesList where registeredTime is null
        defaultSharesShouldNotBeFound("registeredTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllSharesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        shares.setUser(user);
        sharesRepository.saveAndFlush(shares);
        Long userId = user.getId();

        // Get all the sharesList where user equals to userId
        defaultSharesShouldBeFound("userId.equals=" + userId);

        // Get all the sharesList where user equals to userId + 1
        defaultSharesShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllSharesByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        shares.setPost(post);
        sharesRepository.saveAndFlush(shares);
        Long postId = post.getId();

        // Get all the sharesList where post equals to postId
        defaultSharesShouldBeFound("postId.equals=" + postId);

        // Get all the sharesList where post equals to postId + 1
        defaultSharesShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSharesShouldBeFound(String filter) throws Exception {
        restSharesMockMvc.perform(get("/api/shares?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shares.getId().intValue())))
            .andExpect(jsonPath("$.[*].registeredTime").value(hasItem(DEFAULT_REGISTERED_TIME.toString())));

        // Check, that the count call also returns 1
        restSharesMockMvc.perform(get("/api/shares/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSharesShouldNotBeFound(String filter) throws Exception {
        restSharesMockMvc.perform(get("/api/shares?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSharesMockMvc.perform(get("/api/shares/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingShares() throws Exception {
        // Get the shares
        restSharesMockMvc.perform(get("/api/shares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShares() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        int databaseSizeBeforeUpdate = sharesRepository.findAll().size();

        // Update the shares
        Shares updatedShares = sharesRepository.findById(shares.getId()).get();
        // Disconnect from session so that the updates on updatedShares are not directly saved in db
        em.detach(updatedShares);
        updatedShares
            .registeredTime(UPDATED_REGISTERED_TIME);
        SharesDTO sharesDTO = sharesMapper.toDto(updatedShares);

        restSharesMockMvc.perform(put("/api/shares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharesDTO)))
            .andExpect(status().isOk());

        // Validate the Shares in the database
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeUpdate);
        Shares testShares = sharesList.get(sharesList.size() - 1);
        assertThat(testShares.getRegisteredTime()).isEqualTo(UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingShares() throws Exception {
        int databaseSizeBeforeUpdate = sharesRepository.findAll().size();

        // Create the Shares
        SharesDTO sharesDTO = sharesMapper.toDto(shares);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSharesMockMvc.perform(put("/api/shares")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sharesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shares in the database
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShares() throws Exception {
        // Initialize the database
        sharesRepository.saveAndFlush(shares);

        int databaseSizeBeforeDelete = sharesRepository.findAll().size();

        // Delete the shares
        restSharesMockMvc.perform(delete("/api/shares/{id}", shares.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shares> sharesList = sharesRepository.findAll();
        assertThat(sharesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
