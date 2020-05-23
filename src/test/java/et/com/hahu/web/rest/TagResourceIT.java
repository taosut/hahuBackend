package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Tag;
import et.com.hahu.domain.Post;
import et.com.hahu.repository.TagRepository;
import et.com.hahu.service.TagService;
import et.com.hahu.service.dto.TagDTO;
import et.com.hahu.service.mapper.TagMapper;
import et.com.hahu.service.dto.TagCriteria;
import et.com.hahu.service.TagQueryService;

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
 * Integration tests for the {@link TagResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagQueryService tagQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTagMockMvc;

    private Tag tag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tag createEntity(EntityManager em) {
        Tag tag = new Tag()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return tag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tag createUpdatedEntity(EntityManager em) {
        Tag tag = new Tag()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return tag;
    }

    @BeforeEach
    public void initTest() {
        tag = createEntity(em);
    }

    @Test
    @Transactional
    public void createTag() throws Exception {
        int databaseSizeBeforeCreate = tagRepository.findAll().size();
        // Create the Tag
        TagDTO tagDTO = tagMapper.toDto(tag);
        restTagMockMvc.perform(post("/api/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isCreated());

        // Validate the Tag in the database
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeCreate + 1);
        Tag testTag = tagList.get(tagList.size() - 1);
        assertThat(testTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTag.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagRepository.findAll().size();

        // Create the Tag with an existing ID
        tag.setId(1L);
        TagDTO tagDTO = tagMapper.toDto(tag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagMockMvc.perform(post("/api/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tag in the database
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTags() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList
        restTagMockMvc.perform(get("/api/tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTag() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get the tag
        restTagMockMvc.perform(get("/api/tags/{id}", tag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getTagsByIdFiltering() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        Long id = tag.getId();

        defaultTagShouldBeFound("id.equals=" + id);
        defaultTagShouldNotBeFound("id.notEquals=" + id);

        defaultTagShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTagShouldNotBeFound("id.greaterThan=" + id);

        defaultTagShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTagShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where name equals to DEFAULT_NAME
        defaultTagShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tagList where name equals to UPDATED_NAME
        defaultTagShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where name not equals to DEFAULT_NAME
        defaultTagShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the tagList where name not equals to UPDATED_NAME
        defaultTagShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTagShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tagList where name equals to UPDATED_NAME
        defaultTagShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where name is not null
        defaultTagShouldBeFound("name.specified=true");

        // Get all the tagList where name is null
        defaultTagShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllTagsByNameContainsSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where name contains DEFAULT_NAME
        defaultTagShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the tagList where name contains UPDATED_NAME
        defaultTagShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where name does not contain DEFAULT_NAME
        defaultTagShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the tagList where name does not contain UPDATED_NAME
        defaultTagShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllTagsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where description equals to DEFAULT_DESCRIPTION
        defaultTagShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the tagList where description equals to UPDATED_DESCRIPTION
        defaultTagShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where description not equals to DEFAULT_DESCRIPTION
        defaultTagShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the tagList where description not equals to UPDATED_DESCRIPTION
        defaultTagShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTagShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the tagList where description equals to UPDATED_DESCRIPTION
        defaultTagShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where description is not null
        defaultTagShouldBeFound("description.specified=true");

        // Get all the tagList where description is null
        defaultTagShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllTagsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where description contains DEFAULT_DESCRIPTION
        defaultTagShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the tagList where description contains UPDATED_DESCRIPTION
        defaultTagShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        // Get all the tagList where description does not contain DEFAULT_DESCRIPTION
        defaultTagShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the tagList where description does not contain UPDATED_DESCRIPTION
        defaultTagShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllTagsByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        tag.addPost(post);
        tagRepository.saveAndFlush(tag);
        Long postId = post.getId();

        // Get all the tagList where post equals to postId
        defaultTagShouldBeFound("postId.equals=" + postId);

        // Get all the tagList where post equals to postId + 1
        defaultTagShouldNotBeFound("postId.equals=" + (postId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTagShouldBeFound(String filter) throws Exception {
        restTagMockMvc.perform(get("/api/tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restTagMockMvc.perform(get("/api/tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTagShouldNotBeFound(String filter) throws Exception {
        restTagMockMvc.perform(get("/api/tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTagMockMvc.perform(get("/api/tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTag() throws Exception {
        // Get the tag
        restTagMockMvc.perform(get("/api/tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTag() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        int databaseSizeBeforeUpdate = tagRepository.findAll().size();

        // Update the tag
        Tag updatedTag = tagRepository.findById(tag.getId()).get();
        // Disconnect from session so that the updates on updatedTag are not directly saved in db
        em.detach(updatedTag);
        updatedTag
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        TagDTO tagDTO = tagMapper.toDto(updatedTag);

        restTagMockMvc.perform(put("/api/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isOk());

        // Validate the Tag in the database
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeUpdate);
        Tag testTag = tagList.get(tagList.size() - 1);
        assertThat(testTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTag.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTag() throws Exception {
        int databaseSizeBeforeUpdate = tagRepository.findAll().size();

        // Create the Tag
        TagDTO tagDTO = tagMapper.toDto(tag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagMockMvc.perform(put("/api/tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tag in the database
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTag() throws Exception {
        // Initialize the database
        tagRepository.saveAndFlush(tag);

        int databaseSizeBeforeDelete = tagRepository.findAll().size();

        // Delete the tag
        restTagMockMvc.perform(delete("/api/tags/{id}", tag.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tag> tagList = tagRepository.findAll();
        assertThat(tagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
