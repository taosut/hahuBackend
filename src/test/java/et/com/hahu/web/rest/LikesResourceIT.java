package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Likes;
import et.com.hahu.domain.User;
import et.com.hahu.domain.Post;
import et.com.hahu.domain.Comment;
import et.com.hahu.repository.LikesRepository;
import et.com.hahu.service.LikesService;
import et.com.hahu.service.dto.LikesDTO;
import et.com.hahu.service.mapper.LikesMapper;
import et.com.hahu.service.dto.LikesCriteria;
import et.com.hahu.service.LikesQueryService;

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
 * Integration tests for the {@link LikesResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LikesResourceIT {

    private static final Instant DEFAULT_REGISTERED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTERED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private LikesMapper likesMapper;

    @Autowired
    private LikesService likesService;

    @Autowired
    private LikesQueryService likesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikesMockMvc;

    private Likes likes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Likes createEntity(EntityManager em) {
        Likes likes = new Likes()
            .registeredTime(DEFAULT_REGISTERED_TIME);
        return likes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Likes createUpdatedEntity(EntityManager em) {
        Likes likes = new Likes()
            .registeredTime(UPDATED_REGISTERED_TIME);
        return likes;
    }

    @BeforeEach
    public void initTest() {
        likes = createEntity(em);
    }

    @Test
    @Transactional
    public void createLikes() throws Exception {
        int databaseSizeBeforeCreate = likesRepository.findAll().size();
        // Create the Likes
        LikesDTO likesDTO = likesMapper.toDto(likes);
        restLikesMockMvc.perform(post("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isCreated());

        // Validate the Likes in the database
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeCreate + 1);
        Likes testLikes = likesList.get(likesList.size() - 1);
        assertThat(testLikes.getRegisteredTime()).isEqualTo(DEFAULT_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void createLikesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likesRepository.findAll().size();

        // Create the Likes with an existing ID
        likes.setId(1L);
        LikesDTO likesDTO = likesMapper.toDto(likes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikesMockMvc.perform(post("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Likes in the database
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        // Get all the likesList
        restLikesMockMvc.perform(get("/api/likes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likes.getId().intValue())))
            .andExpect(jsonPath("$.[*].registeredTime").value(hasItem(DEFAULT_REGISTERED_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        // Get the likes
        restLikesMockMvc.perform(get("/api/likes/{id}", likes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(likes.getId().intValue()))
            .andExpect(jsonPath("$.registeredTime").value(DEFAULT_REGISTERED_TIME.toString()));
    }


    @Test
    @Transactional
    public void getLikesByIdFiltering() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        Long id = likes.getId();

        defaultLikesShouldBeFound("id.equals=" + id);
        defaultLikesShouldNotBeFound("id.notEquals=" + id);

        defaultLikesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLikesShouldNotBeFound("id.greaterThan=" + id);

        defaultLikesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLikesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLikesByRegisteredTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        // Get all the likesList where registeredTime equals to DEFAULT_REGISTERED_TIME
        defaultLikesShouldBeFound("registeredTime.equals=" + DEFAULT_REGISTERED_TIME);

        // Get all the likesList where registeredTime equals to UPDATED_REGISTERED_TIME
        defaultLikesShouldNotBeFound("registeredTime.equals=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllLikesByRegisteredTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        // Get all the likesList where registeredTime not equals to DEFAULT_REGISTERED_TIME
        defaultLikesShouldNotBeFound("registeredTime.notEquals=" + DEFAULT_REGISTERED_TIME);

        // Get all the likesList where registeredTime not equals to UPDATED_REGISTERED_TIME
        defaultLikesShouldBeFound("registeredTime.notEquals=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllLikesByRegisteredTimeIsInShouldWork() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        // Get all the likesList where registeredTime in DEFAULT_REGISTERED_TIME or UPDATED_REGISTERED_TIME
        defaultLikesShouldBeFound("registeredTime.in=" + DEFAULT_REGISTERED_TIME + "," + UPDATED_REGISTERED_TIME);

        // Get all the likesList where registeredTime equals to UPDATED_REGISTERED_TIME
        defaultLikesShouldNotBeFound("registeredTime.in=" + UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void getAllLikesByRegisteredTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        // Get all the likesList where registeredTime is not null
        defaultLikesShouldBeFound("registeredTime.specified=true");

        // Get all the likesList where registeredTime is null
        defaultLikesShouldNotBeFound("registeredTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllLikesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        likes.setUser(user);
        likesRepository.saveAndFlush(likes);
        Long userId = user.getId();

        // Get all the likesList where user equals to userId
        defaultLikesShouldBeFound("userId.equals=" + userId);

        // Get all the likesList where user equals to userId + 1
        defaultLikesShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllLikesByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        likes.setPost(post);
        likesRepository.saveAndFlush(likes);
        Long postId = post.getId();

        // Get all the likesList where post equals to postId
        defaultLikesShouldBeFound("postId.equals=" + postId);

        // Get all the likesList where post equals to postId + 1
        defaultLikesShouldNotBeFound("postId.equals=" + (postId + 1));
    }


    @Test
    @Transactional
    public void getAllLikesByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);
        Comment comment = CommentResourceIT.createEntity(em);
        em.persist(comment);
        em.flush();
        likes.setComment(comment);
        likesRepository.saveAndFlush(likes);
        Long commentId = comment.getId();

        // Get all the likesList where comment equals to commentId
        defaultLikesShouldBeFound("commentId.equals=" + commentId);

        // Get all the likesList where comment equals to commentId + 1
        defaultLikesShouldNotBeFound("commentId.equals=" + (commentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLikesShouldBeFound(String filter) throws Exception {
        restLikesMockMvc.perform(get("/api/likes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likes.getId().intValue())))
            .andExpect(jsonPath("$.[*].registeredTime").value(hasItem(DEFAULT_REGISTERED_TIME.toString())));

        // Check, that the count call also returns 1
        restLikesMockMvc.perform(get("/api/likes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLikesShouldNotBeFound(String filter) throws Exception {
        restLikesMockMvc.perform(get("/api/likes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLikesMockMvc.perform(get("/api/likes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLikes() throws Exception {
        // Get the likes
        restLikesMockMvc.perform(get("/api/likes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        int databaseSizeBeforeUpdate = likesRepository.findAll().size();

        // Update the likes
        Likes updatedLikes = likesRepository.findById(likes.getId()).get();
        // Disconnect from session so that the updates on updatedLikes are not directly saved in db
        em.detach(updatedLikes);
        updatedLikes
            .registeredTime(UPDATED_REGISTERED_TIME);
        LikesDTO likesDTO = likesMapper.toDto(updatedLikes);

        restLikesMockMvc.perform(put("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isOk());

        // Validate the Likes in the database
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeUpdate);
        Likes testLikes = likesList.get(likesList.size() - 1);
        assertThat(testLikes.getRegisteredTime()).isEqualTo(UPDATED_REGISTERED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingLikes() throws Exception {
        int databaseSizeBeforeUpdate = likesRepository.findAll().size();

        // Create the Likes
        LikesDTO likesDTO = likesMapper.toDto(likes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikesMockMvc.perform(put("/api/likes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(likesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Likes in the database
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLikes() throws Exception {
        // Initialize the database
        likesRepository.saveAndFlush(likes);

        int databaseSizeBeforeDelete = likesRepository.findAll().size();

        // Delete the likes
        restLikesMockMvc.perform(delete("/api/likes/{id}", likes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Likes> likesList = likesRepository.findAll();
        assertThat(likesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
