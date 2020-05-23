package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.PostMetaData;
import et.com.hahu.domain.Post;
import et.com.hahu.repository.PostMetaDataRepository;
import et.com.hahu.service.PostMetaDataService;
import et.com.hahu.service.dto.PostMetaDataDTO;
import et.com.hahu.service.mapper.PostMetaDataMapper;
import et.com.hahu.service.dto.PostMetaDataCriteria;
import et.com.hahu.service.PostMetaDataQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PostMetaDataResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PostMetaDataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BLOB_VALUE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOB_VALUE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BLOB_VALUE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BLOB_VALUE_CONTENT_TYPE = "image/png";

    @Autowired
    private PostMetaDataRepository postMetaDataRepository;

    @Autowired
    private PostMetaDataMapper postMetaDataMapper;

    @Autowired
    private PostMetaDataService postMetaDataService;

    @Autowired
    private PostMetaDataQueryService postMetaDataQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostMetaDataMockMvc;

    private PostMetaData postMetaData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostMetaData createEntity(EntityManager em) {
        PostMetaData postMetaData = new PostMetaData()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .blobValue(DEFAULT_BLOB_VALUE)
            .blobValueContentType(DEFAULT_BLOB_VALUE_CONTENT_TYPE);
        return postMetaData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PostMetaData createUpdatedEntity(EntityManager em) {
        PostMetaData postMetaData = new PostMetaData()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .blobValue(UPDATED_BLOB_VALUE)
            .blobValueContentType(UPDATED_BLOB_VALUE_CONTENT_TYPE);
        return postMetaData;
    }

    @BeforeEach
    public void initTest() {
        postMetaData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPostMetaData() throws Exception {
        int databaseSizeBeforeCreate = postMetaDataRepository.findAll().size();
        // Create the PostMetaData
        PostMetaDataDTO postMetaDataDTO = postMetaDataMapper.toDto(postMetaData);
        restPostMetaDataMockMvc.perform(post("/api/post-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postMetaDataDTO)))
            .andExpect(status().isCreated());

        // Validate the PostMetaData in the database
        List<PostMetaData> postMetaDataList = postMetaDataRepository.findAll();
        assertThat(postMetaDataList).hasSize(databaseSizeBeforeCreate + 1);
        PostMetaData testPostMetaData = postMetaDataList.get(postMetaDataList.size() - 1);
        assertThat(testPostMetaData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPostMetaData.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPostMetaData.getBlobValue()).isEqualTo(DEFAULT_BLOB_VALUE);
        assertThat(testPostMetaData.getBlobValueContentType()).isEqualTo(DEFAULT_BLOB_VALUE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createPostMetaDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postMetaDataRepository.findAll().size();

        // Create the PostMetaData with an existing ID
        postMetaData.setId(1L);
        PostMetaDataDTO postMetaDataDTO = postMetaDataMapper.toDto(postMetaData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMetaDataMockMvc.perform(post("/api/post-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postMetaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostMetaData in the database
        List<PostMetaData> postMetaDataList = postMetaDataRepository.findAll();
        assertThat(postMetaDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPostMetaData() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList
        restPostMetaDataMockMvc.perform(get("/api/post-meta-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postMetaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].blobValueContentType").value(hasItem(DEFAULT_BLOB_VALUE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blobValue").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_VALUE))));
    }
    
    @Test
    @Transactional
    public void getPostMetaData() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get the postMetaData
        restPostMetaDataMockMvc.perform(get("/api/post-meta-data/{id}", postMetaData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(postMetaData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.blobValueContentType").value(DEFAULT_BLOB_VALUE_CONTENT_TYPE))
            .andExpect(jsonPath("$.blobValue").value(Base64Utils.encodeToString(DEFAULT_BLOB_VALUE)));
    }


    @Test
    @Transactional
    public void getPostMetaDataByIdFiltering() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        Long id = postMetaData.getId();

        defaultPostMetaDataShouldBeFound("id.equals=" + id);
        defaultPostMetaDataShouldNotBeFound("id.notEquals=" + id);

        defaultPostMetaDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostMetaDataShouldNotBeFound("id.greaterThan=" + id);

        defaultPostMetaDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostMetaDataShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPostMetaDataByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where name equals to DEFAULT_NAME
        defaultPostMetaDataShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the postMetaDataList where name equals to UPDATED_NAME
        defaultPostMetaDataShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostMetaDataByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where name not equals to DEFAULT_NAME
        defaultPostMetaDataShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the postMetaDataList where name not equals to UPDATED_NAME
        defaultPostMetaDataShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostMetaDataByNameIsInShouldWork() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPostMetaDataShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the postMetaDataList where name equals to UPDATED_NAME
        defaultPostMetaDataShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostMetaDataByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where name is not null
        defaultPostMetaDataShouldBeFound("name.specified=true");

        // Get all the postMetaDataList where name is null
        defaultPostMetaDataShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostMetaDataByNameContainsSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where name contains DEFAULT_NAME
        defaultPostMetaDataShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the postMetaDataList where name contains UPDATED_NAME
        defaultPostMetaDataShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPostMetaDataByNameNotContainsSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where name does not contain DEFAULT_NAME
        defaultPostMetaDataShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the postMetaDataList where name does not contain UPDATED_NAME
        defaultPostMetaDataShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPostMetaDataByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where value equals to DEFAULT_VALUE
        defaultPostMetaDataShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the postMetaDataList where value equals to UPDATED_VALUE
        defaultPostMetaDataShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPostMetaDataByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where value not equals to DEFAULT_VALUE
        defaultPostMetaDataShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the postMetaDataList where value not equals to UPDATED_VALUE
        defaultPostMetaDataShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPostMetaDataByValueIsInShouldWork() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPostMetaDataShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the postMetaDataList where value equals to UPDATED_VALUE
        defaultPostMetaDataShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPostMetaDataByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where value is not null
        defaultPostMetaDataShouldBeFound("value.specified=true");

        // Get all the postMetaDataList where value is null
        defaultPostMetaDataShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostMetaDataByValueContainsSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where value contains DEFAULT_VALUE
        defaultPostMetaDataShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the postMetaDataList where value contains UPDATED_VALUE
        defaultPostMetaDataShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPostMetaDataByValueNotContainsSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        // Get all the postMetaDataList where value does not contain DEFAULT_VALUE
        defaultPostMetaDataShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the postMetaDataList where value does not contain UPDATED_VALUE
        defaultPostMetaDataShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllPostMetaDataByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        postMetaData.setPost(post);
        postMetaDataRepository.saveAndFlush(postMetaData);
        Long postId = post.getId();

        // Get all the postMetaDataList where post equals to postId
        defaultPostMetaDataShouldBeFound("postId.equals=" + postId);

        // Get all the postMetaDataList where post equals to postId + 1
        defaultPostMetaDataShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostMetaDataShouldBeFound(String filter) throws Exception {
        restPostMetaDataMockMvc.perform(get("/api/post-meta-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(postMetaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].blobValueContentType").value(hasItem(DEFAULT_BLOB_VALUE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blobValue").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_VALUE))));

        // Check, that the count call also returns 1
        restPostMetaDataMockMvc.perform(get("/api/post-meta-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostMetaDataShouldNotBeFound(String filter) throws Exception {
        restPostMetaDataMockMvc.perform(get("/api/post-meta-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostMetaDataMockMvc.perform(get("/api/post-meta-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPostMetaData() throws Exception {
        // Get the postMetaData
        restPostMetaDataMockMvc.perform(get("/api/post-meta-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePostMetaData() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        int databaseSizeBeforeUpdate = postMetaDataRepository.findAll().size();

        // Update the postMetaData
        PostMetaData updatedPostMetaData = postMetaDataRepository.findById(postMetaData.getId()).get();
        // Disconnect from session so that the updates on updatedPostMetaData are not directly saved in db
        em.detach(updatedPostMetaData);
        updatedPostMetaData
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .blobValue(UPDATED_BLOB_VALUE)
            .blobValueContentType(UPDATED_BLOB_VALUE_CONTENT_TYPE);
        PostMetaDataDTO postMetaDataDTO = postMetaDataMapper.toDto(updatedPostMetaData);

        restPostMetaDataMockMvc.perform(put("/api/post-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postMetaDataDTO)))
            .andExpect(status().isOk());

        // Validate the PostMetaData in the database
        List<PostMetaData> postMetaDataList = postMetaDataRepository.findAll();
        assertThat(postMetaDataList).hasSize(databaseSizeBeforeUpdate);
        PostMetaData testPostMetaData = postMetaDataList.get(postMetaDataList.size() - 1);
        assertThat(testPostMetaData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPostMetaData.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPostMetaData.getBlobValue()).isEqualTo(UPDATED_BLOB_VALUE);
        assertThat(testPostMetaData.getBlobValueContentType()).isEqualTo(UPDATED_BLOB_VALUE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingPostMetaData() throws Exception {
        int databaseSizeBeforeUpdate = postMetaDataRepository.findAll().size();

        // Create the PostMetaData
        PostMetaDataDTO postMetaDataDTO = postMetaDataMapper.toDto(postMetaData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMetaDataMockMvc.perform(put("/api/post-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postMetaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PostMetaData in the database
        List<PostMetaData> postMetaDataList = postMetaDataRepository.findAll();
        assertThat(postMetaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePostMetaData() throws Exception {
        // Initialize the database
        postMetaDataRepository.saveAndFlush(postMetaData);

        int databaseSizeBeforeDelete = postMetaDataRepository.findAll().size();

        // Delete the postMetaData
        restPostMetaDataMockMvc.perform(delete("/api/post-meta-data/{id}", postMetaData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PostMetaData> postMetaDataList = postMetaDataRepository.findAll();
        assertThat(postMetaDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
