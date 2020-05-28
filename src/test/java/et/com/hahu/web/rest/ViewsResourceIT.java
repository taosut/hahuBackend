package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Views;
import et.com.hahu.domain.User;
import et.com.hahu.domain.Post;
import et.com.hahu.repository.ViewsRepository;
import et.com.hahu.service.ViewsService;
import et.com.hahu.service.dto.ViewsDTO;
import et.com.hahu.service.mapper.ViewsMapper;
import et.com.hahu.service.dto.ViewsCriteria;
import et.com.hahu.service.ViewsQueryService;

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
 * Integration tests for the {@link ViewsResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ViewsResourceIT {

    private static final Instant DEFAULT_REGISTERED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTERED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ViewsRepository viewsRepository;

    @Autowired
    private ViewsMapper viewsMapper;

    @Autowired
    private ViewsService viewsService;

    @Autowired
    private ViewsQueryService viewsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViewsMockMvc;

    private Views views;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Views createEntity(EntityManager em) {
        Views views = new Views()
            .registeredTime(DEFAULT_REGISTERED_TIME);
        return views;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Views createUpdatedEntity(EntityManager em) {
        Views views = new Views()
            .registeredTime(UPDATED_REGISTERED_TIME);
        return views;
    }

    @BeforeEach
    public void initTest() {
        views = createEntity(em);
    }

    @Test
    @Transactional
    public void createViews() throws Exception {
        int databaseSizeBeforeCreate = viewsRepository.findAll().size();
        // Create the Views
        ViewsDTO viewsDTO = viewsMapper.toDto(views);
        restViewsMockMvc.perform(post("/api/views")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(viewsDTO)))
            .andExpect(status().isCreated());

        // Validate the Views in the database
        List<Views> viewsList = viewsRepository.findAll();
        assertThat(viewsList).hasSize(databaseSizeBeforeCreate + 1);
        Views testViews = viewsList.get(viewsList.size() - 1);
        assertThat(testViews.getRegisteredTime()).isEqualTo(DEFAULT_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void createViewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = viewsRepository.findAll().size();

        // Create the Views with an existing ID
        views.setId(1L);
        ViewsDTO viewsDTO = viewsMapper.toDto(views);

        // An entity with an existing ID cannot be created, so this API call must fail
        restViewsMockMvc.perform(post("/api/views")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(viewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Views in the database
        List<Views> viewsList = viewsRepository.findAll();
        assertThat(viewsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllViews() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        // Get all the viewsList
        restViewsMockMvc.perform(get("/api/views?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(views.getId().intValue())))
            .andExpect(jsonPath("$.[*].registeredTime").value(hasItem(DEFAULT_REGISTERED_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getViews() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        // Get the views
        restViewsMockMvc.perform(get("/api/views/{id}", views.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(views.getId().intValue()))
            .andExpect(jsonPath("$.registeredTime").value(DEFAULT_REGISTERED_TIME.toString()));
    }


    @Test
    @Transactional
    public void getViewsByIdFiltering() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        Long id = views.getId();

        defaultViewsShouldBeFound("id.equals=" + id);
        defaultViewsShouldNotBeFound("id.notEquals=" + id);

        defaultViewsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultViewsShouldNotBeFound("id.greaterThan=" + id);

        defaultViewsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultViewsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllViewsByRegisteredTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        // Get all the viewsList where registeredTime equals to DEFAULT_REGISTERED_TIME
        defaultViewsShouldBeFound("registeredTime.equals=" + DEFAULT_REGISTERED_TIME);

        // Get all the viewsList where registeredTime equals to UPDATED_REGISTERED_TIME
        defaultViewsShouldNotBeFound("registeredTime.equals=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllViewsByRegisteredTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        // Get all the viewsList where registeredTime not equals to DEFAULT_REGISTERED_TIME
        defaultViewsShouldNotBeFound("registeredTime.notEquals=" + DEFAULT_REGISTERED_TIME);

        // Get all the viewsList where registeredTime not equals to UPDATED_REGISTERED_TIME
        defaultViewsShouldBeFound("registeredTime.notEquals=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllViewsByRegisteredTimeIsInShouldWork() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        // Get all the viewsList where registeredTime in DEFAULT_REGISTERED_TIME or UPDATED_REGISTERED_TIME
        defaultViewsShouldBeFound("registeredTime.in=" + DEFAULT_REGISTERED_TIME + "," + UPDATED_REGISTERED_TIME);

        // Get all the viewsList where registeredTime equals to UPDATED_REGISTERED_TIME
        defaultViewsShouldNotBeFound("registeredTime.in=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllViewsByRegisteredTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        // Get all the viewsList where registeredTime is not null
        defaultViewsShouldBeFound("registeredTime.specified=true");

        // Get all the viewsList where registeredTime is null
        defaultViewsShouldNotBeFound("registeredTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        views.setUser(user);
        viewsRepository.saveAndFlush(views);
        Long userId = user.getId();

        // Get all the viewsList where user equals to userId
        defaultViewsShouldBeFound("userId.equals=" + userId);

        // Get all the viewsList where user equals to userId + 1
        defaultViewsShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllViewsByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        views.setPost(post);
        viewsRepository.saveAndFlush(views);
        Long postId = post.getId();

        // Get all the viewsList where post equals to postId
        defaultViewsShouldBeFound("postId.equals=" + postId);

        // Get all the viewsList where post equals to postId + 1
        defaultViewsShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultViewsShouldBeFound(String filter) throws Exception {
        restViewsMockMvc.perform(get("/api/views?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(views.getId().intValue())))
            .andExpect(jsonPath("$.[*].registeredTime").value(hasItem(DEFAULT_REGISTERED_TIME.toString())));

        // Check, that the count call also returns 1
        restViewsMockMvc.perform(get("/api/views/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultViewsShouldNotBeFound(String filter) throws Exception {
        restViewsMockMvc.perform(get("/api/views?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restViewsMockMvc.perform(get("/api/views/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingViews() throws Exception {
        // Get the views
        restViewsMockMvc.perform(get("/api/views/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateViews() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        int databaseSizeBeforeUpdate = viewsRepository.findAll().size();

        // Update the views
        Views updatedViews = viewsRepository.findById(views.getId()).get();
        // Disconnect from session so that the updates on updatedViews are not directly saved in db
        em.detach(updatedViews);
        updatedViews
            .registeredTime(UPDATED_REGISTERED_TIME);
        ViewsDTO viewsDTO = viewsMapper.toDto(updatedViews);

        restViewsMockMvc.perform(put("/api/views")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(viewsDTO)))
            .andExpect(status().isOk());

        // Validate the Views in the database
        List<Views> viewsList = viewsRepository.findAll();
        assertThat(viewsList).hasSize(databaseSizeBeforeUpdate);
        Views testViews = viewsList.get(viewsList.size() - 1);
        assertThat(testViews.getRegisteredTime()).isEqualTo(UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingViews() throws Exception {
        int databaseSizeBeforeUpdate = viewsRepository.findAll().size();

        // Create the Views
        ViewsDTO viewsDTO = viewsMapper.toDto(views);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViewsMockMvc.perform(put("/api/views")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(viewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Views in the database
        List<Views> viewsList = viewsRepository.findAll();
        assertThat(viewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteViews() throws Exception {
        // Initialize the database
        viewsRepository.saveAndFlush(views);

        int databaseSizeBeforeDelete = viewsRepository.findAll().size();

        // Delete the views
        restViewsMockMvc.perform(delete("/api/views/{id}", views.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Views> viewsList = viewsRepository.findAll();
        assertThat(viewsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
