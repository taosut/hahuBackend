package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Post;
import et.com.hahu.domain.PostMetaData;
import et.com.hahu.domain.Comment;
import et.com.hahu.domain.Likes;
import et.com.hahu.domain.User;
import et.com.hahu.domain.Category;
import et.com.hahu.domain.Tag;
import et.com.hahu.repository.PostRepository;
import et.com.hahu.service.PostService;
import et.com.hahu.service.dto.PostDTO;
import et.com.hahu.service.mapper.PostMapper;
import et.com.hahu.service.dto.PostCriteria;
import et.com.hahu.service.PostQueryService;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import et.com.hahu.domain.enumeration.ContentType;
/**
 * Integration tests for the {@link PostResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.TEXT;
    private static final ContentType UPDATED_CONTENT_TYPE = ContentType.HTML;

    private static final byte[] DEFAULT_FEATURED_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FEATURED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FEATURED_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FEATURED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_POSTED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POSTED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INSTANT_POST_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSTANT_POST_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PostRepository postRepository;

    @Mock
    private PostRepository postRepositoryMock;

    @Autowired
    private PostMapper postMapper;

    @Mock
    private PostService postServiceMock;

    @Autowired
    private PostService postService;

    @Autowired
    private PostQueryService postQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPostMockMvc;

    private Post post;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .contentType(DEFAULT_CONTENT_TYPE)
            .featuredImage(DEFAULT_FEATURED_IMAGE)
            .featuredImageContentType(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)
            .postedDate(DEFAULT_POSTED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .instantPostEndDate(DEFAULT_INSTANT_POST_END_DATE);
        return post;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Post createUpdatedEntity(EntityManager em) {
        Post post = new Post()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .contentType(UPDATED_CONTENT_TYPE)
            .featuredImage(UPDATED_FEATURED_IMAGE)
            .featuredImageContentType(UPDATED_FEATURED_IMAGE_CONTENT_TYPE)
            .postedDate(UPDATED_POSTED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .instantPostEndDate(UPDATED_INSTANT_POST_END_DATE);
        return post;
    }

    @BeforeEach
    public void initTest() {
        post = createEntity(em);
    }

    @Test
    @Transactional
    public void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);
        restPostMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPost.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPost.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testPost.getFeaturedImage()).isEqualTo(DEFAULT_FEATURED_IMAGE);
        assertThat(testPost.getFeaturedImageContentType()).isEqualTo(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE);
        assertThat(testPost.getPostedDate()).isEqualTo(DEFAULT_POSTED_DATE);
        assertThat(testPost.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testPost.getInstantPostEndDate()).isEqualTo(DEFAULT_INSTANT_POST_END_DATE);
    }

    @Test
    @Transactional
    public void createPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post with an existing ID
        post.setId(1L);
        PostDTO postDTO = postMapper.toDto(post);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPosts() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList
        restPostMockMvc.perform(get("/api/posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].featuredImageContentType").value(hasItem(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].featuredImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE))))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].instantPostEndDate").value(hasItem(DEFAULT_INSTANT_POST_END_DATE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPostsWithEagerRelationshipsIsEnabled() throws Exception {
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostMockMvc.perform(get("/api/posts?eagerload=true"))
            .andExpect(status().isOk());

        verify(postServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(postServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPostMockMvc.perform(get("/api/posts?eagerload=true"))
            .andExpect(status().isOk());

        verify(postServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", post.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(post.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.featuredImageContentType").value(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.featuredImage").value(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE)))
            .andExpect(jsonPath("$.postedDate").value(DEFAULT_POSTED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.instantPostEndDate").value(DEFAULT_INSTANT_POST_END_DATE.toString()));
    }


    @Test
    @Transactional
    public void getPostsByIdFiltering() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        Long id = post.getId();

        defaultPostShouldBeFound("id.equals=" + id);
        defaultPostShouldNotBeFound("id.notEquals=" + id);

        defaultPostShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPostShouldNotBeFound("id.greaterThan=" + id);

        defaultPostShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPostShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPostsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title equals to DEFAULT_TITLE
        defaultPostShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the postList where title equals to UPDATED_TITLE
        defaultPostShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPostsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title not equals to DEFAULT_TITLE
        defaultPostShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the postList where title not equals to UPDATED_TITLE
        defaultPostShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPostsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultPostShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the postList where title equals to UPDATED_TITLE
        defaultPostShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPostsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title is not null
        defaultPostShouldBeFound("title.specified=true");

        // Get all the postList where title is null
        defaultPostShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllPostsByTitleContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title contains DEFAULT_TITLE
        defaultPostShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the postList where title contains UPDATED_TITLE
        defaultPostShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllPostsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where title does not contain DEFAULT_TITLE
        defaultPostShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the postList where title does not contain UPDATED_TITLE
        defaultPostShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllPostsByContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where contentType equals to DEFAULT_CONTENT_TYPE
        defaultPostShouldBeFound("contentType.equals=" + DEFAULT_CONTENT_TYPE);

        // Get all the postList where contentType equals to UPDATED_CONTENT_TYPE
        defaultPostShouldNotBeFound("contentType.equals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByContentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where contentType not equals to DEFAULT_CONTENT_TYPE
        defaultPostShouldNotBeFound("contentType.notEquals=" + DEFAULT_CONTENT_TYPE);

        // Get all the postList where contentType not equals to UPDATED_CONTENT_TYPE
        defaultPostShouldBeFound("contentType.notEquals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where contentType in DEFAULT_CONTENT_TYPE or UPDATED_CONTENT_TYPE
        defaultPostShouldBeFound("contentType.in=" + DEFAULT_CONTENT_TYPE + "," + UPDATED_CONTENT_TYPE);

        // Get all the postList where contentType equals to UPDATED_CONTENT_TYPE
        defaultPostShouldNotBeFound("contentType.in=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllPostsByContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where contentType is not null
        defaultPostShouldBeFound("contentType.specified=true");

        // Get all the postList where contentType is null
        defaultPostShouldNotBeFound("contentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByPostedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postedDate equals to DEFAULT_POSTED_DATE
        defaultPostShouldBeFound("postedDate.equals=" + DEFAULT_POSTED_DATE);

        // Get all the postList where postedDate equals to UPDATED_POSTED_DATE
        defaultPostShouldNotBeFound("postedDate.equals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postedDate not equals to DEFAULT_POSTED_DATE
        defaultPostShouldNotBeFound("postedDate.notEquals=" + DEFAULT_POSTED_DATE);

        // Get all the postList where postedDate not equals to UPDATED_POSTED_DATE
        defaultPostShouldBeFound("postedDate.notEquals=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostedDateIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postedDate in DEFAULT_POSTED_DATE or UPDATED_POSTED_DATE
        defaultPostShouldBeFound("postedDate.in=" + DEFAULT_POSTED_DATE + "," + UPDATED_POSTED_DATE);

        // Get all the postList where postedDate equals to UPDATED_POSTED_DATE
        defaultPostShouldNotBeFound("postedDate.in=" + UPDATED_POSTED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByPostedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where postedDate is not null
        defaultPostShouldBeFound("postedDate.specified=true");

        // Get all the postList where postedDate is null
        defaultPostShouldNotBeFound("postedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultPostShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the postList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultPostShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultPostShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the postList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultPostShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultPostShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the postList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultPostShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where modifiedDate is not null
        defaultPostShouldBeFound("modifiedDate.specified=true");

        // Get all the postList where modifiedDate is null
        defaultPostShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByInstantPostEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where instantPostEndDate equals to DEFAULT_INSTANT_POST_END_DATE
        defaultPostShouldBeFound("instantPostEndDate.equals=" + DEFAULT_INSTANT_POST_END_DATE);

        // Get all the postList where instantPostEndDate equals to UPDATED_INSTANT_POST_END_DATE
        defaultPostShouldNotBeFound("instantPostEndDate.equals=" + UPDATED_INSTANT_POST_END_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByInstantPostEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where instantPostEndDate not equals to DEFAULT_INSTANT_POST_END_DATE
        defaultPostShouldNotBeFound("instantPostEndDate.notEquals=" + DEFAULT_INSTANT_POST_END_DATE);

        // Get all the postList where instantPostEndDate not equals to UPDATED_INSTANT_POST_END_DATE
        defaultPostShouldBeFound("instantPostEndDate.notEquals=" + UPDATED_INSTANT_POST_END_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByInstantPostEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where instantPostEndDate in DEFAULT_INSTANT_POST_END_DATE or UPDATED_INSTANT_POST_END_DATE
        defaultPostShouldBeFound("instantPostEndDate.in=" + DEFAULT_INSTANT_POST_END_DATE + "," + UPDATED_INSTANT_POST_END_DATE);

        // Get all the postList where instantPostEndDate equals to UPDATED_INSTANT_POST_END_DATE
        defaultPostShouldNotBeFound("instantPostEndDate.in=" + UPDATED_INSTANT_POST_END_DATE);
    }

    @Test
    @Transactional
    public void getAllPostsByInstantPostEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList where instantPostEndDate is not null
        defaultPostShouldBeFound("instantPostEndDate.specified=true");

        // Get all the postList where instantPostEndDate is null
        defaultPostShouldNotBeFound("instantPostEndDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPostsByPostMetaDataIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        PostMetaData postMetaData = PostMetaDataResourceIT.createEntity(em);
        em.persist(postMetaData);
        em.flush();
        post.addPostMetaData(postMetaData);
        postRepository.saveAndFlush(post);
        Long postMetaDataId = postMetaData.getId();

        // Get all the postList where postMetaData equals to postMetaDataId
        defaultPostShouldBeFound("postMetaDataId.equals=" + postMetaDataId);

        // Get all the postList where postMetaData equals to postMetaDataId + 1
        defaultPostShouldNotBeFound("postMetaDataId.equals=" + (postMetaDataId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        Comment comment = CommentResourceIT.createEntity(em);
        em.persist(comment);
        em.flush();
        post.addComment(comment);
        postRepository.saveAndFlush(post);
        Long commentId = comment.getId();

        // Get all the postList where comment equals to commentId
        defaultPostShouldBeFound("commentId.equals=" + commentId);

        // Get all the postList where comment equals to commentId + 1
        defaultPostShouldNotBeFound("commentId.equals=" + (commentId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByLikeIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        Likes like = LikesResourceIT.createEntity(em);
        em.persist(like);
        em.flush();
        post.addLike(like);
        postRepository.saveAndFlush(post);
        Long likeId = like.getId();

        // Get all the postList where like equals to likeId
        defaultPostShouldBeFound("likeId.equals=" + likeId);

        // Get all the postList where like equals to likeId + 1
        defaultPostShouldNotBeFound("likeId.equals=" + (likeId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        post.setUser(user);
        postRepository.saveAndFlush(post);
        Long userId = user.getId();

        // Get all the postList where user equals to userId
        defaultPostShouldBeFound("userId.equals=" + userId);

        // Get all the postList where user equals to userId + 1
        defaultPostShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        Category category = CategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        post.addCategory(category);
        postRepository.saveAndFlush(post);
        Long categoryId = category.getId();

        // Get all the postList where category equals to categoryId
        defaultPostShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the postList where category equals to categoryId + 1
        defaultPostShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }


    @Test
    @Transactional
    public void getAllPostsByTagIsEqualToSomething() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);
        Tag tag = TagResourceIT.createEntity(em);
        em.persist(tag);
        em.flush();
        post.addTag(tag);
        postRepository.saveAndFlush(post);
        Long tagId = tag.getId();

        // Get all the postList where tag equals to tagId
        defaultPostShouldBeFound("tagId.equals=" + tagId);

        // Get all the postList where tag equals to tagId + 1
        defaultPostShouldNotBeFound("tagId.equals=" + (tagId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPostShouldBeFound(String filter) throws Exception {
        restPostMockMvc.perform(get("/api/posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].featuredImageContentType").value(hasItem(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].featuredImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE))))
            .andExpect(jsonPath("$.[*].postedDate").value(hasItem(DEFAULT_POSTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].instantPostEndDate").value(hasItem(DEFAULT_INSTANT_POST_END_DATE.toString())));

        // Check, that the count call also returns 1
        restPostMockMvc.perform(get("/api/posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPostShouldNotBeFound(String filter) throws Exception {
        restPostMockMvc.perform(get("/api/posts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPostMockMvc.perform(get("/api/posts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post
        Post updatedPost = postRepository.findById(post.getId()).get();
        // Disconnect from session so that the updates on updatedPost are not directly saved in db
        em.detach(updatedPost);
        updatedPost
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .contentType(UPDATED_CONTENT_TYPE)
            .featuredImage(UPDATED_FEATURED_IMAGE)
            .featuredImageContentType(UPDATED_FEATURED_IMAGE_CONTENT_TYPE)
            .postedDate(UPDATED_POSTED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .instantPostEndDate(UPDATED_INSTANT_POST_END_DATE);
        PostDTO postDTO = postMapper.toDto(updatedPost);

        restPostMockMvc.perform(put("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPost.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPost.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testPost.getFeaturedImage()).isEqualTo(UPDATED_FEATURED_IMAGE);
        assertThat(testPost.getFeaturedImageContentType()).isEqualTo(UPDATED_FEATURED_IMAGE_CONTENT_TYPE);
        assertThat(testPost.getPostedDate()).isEqualTo(UPDATED_POSTED_DATE);
        assertThat(testPost.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testPost.getInstantPostEndDate()).isEqualTo(UPDATED_INSTANT_POST_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Create the Post
        PostDTO postDTO = postMapper.toDto(post);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostMockMvc.perform(put("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        int databaseSizeBeforeDelete = postRepository.findAll().size();

        // Delete the post
        restPostMockMvc.perform(delete("/api/posts/{id}", post.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
