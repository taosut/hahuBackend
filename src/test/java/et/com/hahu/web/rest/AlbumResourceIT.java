package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Album;
import et.com.hahu.domain.Image;
import et.com.hahu.domain.User;
import et.com.hahu.repository.AlbumRepository;
import et.com.hahu.service.AlbumService;
import et.com.hahu.service.dto.AlbumDTO;
import et.com.hahu.service.mapper.AlbumMapper;
import et.com.hahu.service.dto.AlbumCriteria;
import et.com.hahu.service.AlbumQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AlbumResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AlbumResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumQueryService albumQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlbumMockMvc;

    private Album album;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createEntity(EntityManager em) {
        Album album = new Album()
            .name(DEFAULT_NAME);
        return album;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createUpdatedEntity(EntityManager em) {
        Album album = new Album()
            .name(UPDATED_NAME);
        return album;
    }

    @BeforeEach
    public void initTest() {
        album = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbum() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate + 1);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album with an existing ID
        album.setId(1L);
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAlbums() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", album.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(album.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getAlbumsByIdFiltering() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        Long id = album.getId();

        defaultAlbumShouldBeFound("id.equals=" + id);
        defaultAlbumShouldNotBeFound("id.notEquals=" + id);

        defaultAlbumShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAlbumShouldNotBeFound("id.greaterThan=" + id);

        defaultAlbumShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAlbumShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAlbumsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name equals to DEFAULT_NAME
        defaultAlbumShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the albumList where name equals to UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name not equals to DEFAULT_NAME
        defaultAlbumShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the albumList where name not equals to UPDATED_NAME
        defaultAlbumShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAlbumShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the albumList where name equals to UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name is not null
        defaultAlbumShouldBeFound("name.specified=true");

        // Get all the albumList where name is null
        defaultAlbumShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAlbumsByNameContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name contains DEFAULT_NAME
        defaultAlbumShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the albumList where name contains UPDATED_NAME
        defaultAlbumShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAlbumsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList where name does not contain DEFAULT_NAME
        defaultAlbumShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the albumList where name does not contain UPDATED_NAME
        defaultAlbumShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAlbumsByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);
        Image image = ImageResourceIT.createEntity(em);
        em.persist(image);
        em.flush();
        album.addImage(image);
        albumRepository.saveAndFlush(album);
        Long imageId = image.getId();

        // Get all the albumList where image equals to imageId
        defaultAlbumShouldBeFound("imageId.equals=" + imageId);

        // Get all the albumList where image equals to imageId + 1
        defaultAlbumShouldNotBeFound("imageId.equals=" + (imageId + 1));
    }


    @Test
    @Transactional
    public void getAllAlbumsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        album.setUser(user);
        albumRepository.saveAndFlush(album);
        Long userId = user.getId();

        // Get all the albumList where user equals to userId
        defaultAlbumShouldBeFound("userId.equals=" + userId);

        // Get all the albumList where user equals to userId + 1
        defaultAlbumShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAlbumShouldBeFound(String filter) throws Exception {
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restAlbumMockMvc.perform(get("/api/albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAlbumShouldNotBeFound(String filter) throws Exception {
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAlbumMockMvc.perform(get("/api/albums/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAlbum() throws Exception {
        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Update the album
        Album updatedAlbum = albumRepository.findById(album.getId()).get();
        // Disconnect from session so that the updates on updatedAlbum are not directly saved in db
        em.detach(updatedAlbum);
        updatedAlbum
            .name(UPDATED_NAME);
        AlbumDTO albumDTO = albumMapper.toDto(updatedAlbum);

        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isOk());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbum() throws Exception {
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        int databaseSizeBeforeDelete = albumRepository.findAll().size();

        // Delete the album
        restAlbumMockMvc.perform(delete("/api/albums/{id}", album.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
