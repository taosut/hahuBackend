package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Notification;
import et.com.hahu.domain.NotificationMetaData;
import et.com.hahu.domain.User;
import et.com.hahu.domain.UserGroup;
import et.com.hahu.repository.NotificationRepository;
import et.com.hahu.service.NotificationService;
import et.com.hahu.service.dto.NotificationDTO;
import et.com.hahu.service.mapper.NotificationMapper;
import et.com.hahu.service.dto.NotificationCriteria;
import et.com.hahu.service.NotificationQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import et.com.hahu.domain.enumeration.ContentType;
/**
 * Integration tests for the {@link NotificationResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotificationResourceIT {

    private static final byte[] DEFAULT_FEATURED_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FEATURED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FEATURED_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FEATURED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ContentType DEFAULT_CONTENT_TYPE = ContentType.TEXT;
    private static final ContentType UPDATED_CONTENT_TYPE = ContentType.HTML;

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_MARK_AS_READ = false;
    private static final Boolean UPDATED_MARK_AS_READ = true;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationQueryService notificationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationMockMvc;

    private Notification notification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createEntity(EntityManager em) {
        Notification notification = new Notification()
            .featuredImage(DEFAULT_FEATURED_IMAGE)
            .featuredImageContentType(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .contentType(DEFAULT_CONTENT_TYPE)
            .link(DEFAULT_LINK)
            .date(DEFAULT_DATE)
            .markAsRead(DEFAULT_MARK_AS_READ);
        return notification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notification createUpdatedEntity(EntityManager em) {
        Notification notification = new Notification()
            .featuredImage(UPDATED_FEATURED_IMAGE)
            .featuredImageContentType(UPDATED_FEATURED_IMAGE_CONTENT_TYPE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .contentType(UPDATED_CONTENT_TYPE)
            .link(UPDATED_LINK)
            .date(UPDATED_DATE)
            .markAsRead(UPDATED_MARK_AS_READ);
        return notification;
    }

    @BeforeEach
    public void initTest() {
        notification = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotification() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();
        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isCreated());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate + 1);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getFeaturedImage()).isEqualTo(DEFAULT_FEATURED_IMAGE);
        assertThat(testNotification.getFeaturedImageContentType()).isEqualTo(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE);
        assertThat(testNotification.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotification.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testNotification.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testNotification.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testNotification.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNotification.isMarkAsRead()).isEqualTo(DEFAULT_MARK_AS_READ);
    }

    @Test
    @Transactional
    public void createNotificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationRepository.findAll().size();

        // Create the Notification with an existing ID
        notification.setId(1L);
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMockMvc.perform(post("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNotifications() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].featuredImageContentType").value(hasItem(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].featuredImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].markAsRead").value(hasItem(DEFAULT_MARK_AS_READ.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", notification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notification.getId().intValue()))
            .andExpect(jsonPath("$.featuredImageContentType").value(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.featuredImage").value(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE)))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.markAsRead").value(DEFAULT_MARK_AS_READ.booleanValue()));
    }


    @Test
    @Transactional
    public void getNotificationsByIdFiltering() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        Long id = notification.getId();

        defaultNotificationShouldBeFound("id.equals=" + id);
        defaultNotificationShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNotificationsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title equals to DEFAULT_TITLE
        defaultNotificationShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the notificationList where title equals to UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title not equals to DEFAULT_TITLE
        defaultNotificationShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the notificationList where title not equals to UPDATED_TITLE
        defaultNotificationShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNotificationShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the notificationList where title equals to UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title is not null
        defaultNotificationShouldBeFound("title.specified=true");

        // Get all the notificationList where title is null
        defaultNotificationShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificationsByTitleContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title contains DEFAULT_TITLE
        defaultNotificationShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the notificationList where title contains UPDATED_TITLE
        defaultNotificationShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where title does not contain DEFAULT_TITLE
        defaultNotificationShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the notificationList where title does not contain UPDATED_TITLE
        defaultNotificationShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllNotificationsByContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where contentType equals to DEFAULT_CONTENT_TYPE
        defaultNotificationShouldBeFound("contentType.equals=" + DEFAULT_CONTENT_TYPE);

        // Get all the notificationList where contentType equals to UPDATED_CONTENT_TYPE
        defaultNotificationShouldNotBeFound("contentType.equals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByContentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where contentType not equals to DEFAULT_CONTENT_TYPE
        defaultNotificationShouldNotBeFound("contentType.notEquals=" + DEFAULT_CONTENT_TYPE);

        // Get all the notificationList where contentType not equals to UPDATED_CONTENT_TYPE
        defaultNotificationShouldBeFound("contentType.notEquals=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where contentType in DEFAULT_CONTENT_TYPE or UPDATED_CONTENT_TYPE
        defaultNotificationShouldBeFound("contentType.in=" + DEFAULT_CONTENT_TYPE + "," + UPDATED_CONTENT_TYPE);

        // Get all the notificationList where contentType equals to UPDATED_CONTENT_TYPE
        defaultNotificationShouldNotBeFound("contentType.in=" + UPDATED_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where contentType is not null
        defaultNotificationShouldBeFound("contentType.specified=true");

        // Get all the notificationList where contentType is null
        defaultNotificationShouldNotBeFound("contentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsByLinkIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where link equals to DEFAULT_LINK
        defaultNotificationShouldBeFound("link.equals=" + DEFAULT_LINK);

        // Get all the notificationList where link equals to UPDATED_LINK
        defaultNotificationShouldNotBeFound("link.equals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLinkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where link not equals to DEFAULT_LINK
        defaultNotificationShouldNotBeFound("link.notEquals=" + DEFAULT_LINK);

        // Get all the notificationList where link not equals to UPDATED_LINK
        defaultNotificationShouldBeFound("link.notEquals=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLinkIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where link in DEFAULT_LINK or UPDATED_LINK
        defaultNotificationShouldBeFound("link.in=" + DEFAULT_LINK + "," + UPDATED_LINK);

        // Get all the notificationList where link equals to UPDATED_LINK
        defaultNotificationShouldNotBeFound("link.in=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLinkIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where link is not null
        defaultNotificationShouldBeFound("link.specified=true");

        // Get all the notificationList where link is null
        defaultNotificationShouldNotBeFound("link.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificationsByLinkContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where link contains DEFAULT_LINK
        defaultNotificationShouldBeFound("link.contains=" + DEFAULT_LINK);

        // Get all the notificationList where link contains UPDATED_LINK
        defaultNotificationShouldNotBeFound("link.contains=" + UPDATED_LINK);
    }

    @Test
    @Transactional
    public void getAllNotificationsByLinkNotContainsSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where link does not contain DEFAULT_LINK
        defaultNotificationShouldNotBeFound("link.doesNotContain=" + DEFAULT_LINK);

        // Get all the notificationList where link does not contain UPDATED_LINK
        defaultNotificationShouldBeFound("link.doesNotContain=" + UPDATED_LINK);
    }


    @Test
    @Transactional
    public void getAllNotificationsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where date equals to DEFAULT_DATE
        defaultNotificationShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the notificationList where date equals to UPDATED_DATE
        defaultNotificationShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where date not equals to DEFAULT_DATE
        defaultNotificationShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the notificationList where date not equals to UPDATED_DATE
        defaultNotificationShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where date in DEFAULT_DATE or UPDATED_DATE
        defaultNotificationShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the notificationList where date equals to UPDATED_DATE
        defaultNotificationShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNotificationsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where date is not null
        defaultNotificationShouldBeFound("date.specified=true");

        // Get all the notificationList where date is null
        defaultNotificationShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsByMarkAsReadIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where markAsRead equals to DEFAULT_MARK_AS_READ
        defaultNotificationShouldBeFound("markAsRead.equals=" + DEFAULT_MARK_AS_READ);

        // Get all the notificationList where markAsRead equals to UPDATED_MARK_AS_READ
        defaultNotificationShouldNotBeFound("markAsRead.equals=" + UPDATED_MARK_AS_READ);
    }

    @Test
    @Transactional
    public void getAllNotificationsByMarkAsReadIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where markAsRead not equals to DEFAULT_MARK_AS_READ
        defaultNotificationShouldNotBeFound("markAsRead.notEquals=" + DEFAULT_MARK_AS_READ);

        // Get all the notificationList where markAsRead not equals to UPDATED_MARK_AS_READ
        defaultNotificationShouldBeFound("markAsRead.notEquals=" + UPDATED_MARK_AS_READ);
    }

    @Test
    @Transactional
    public void getAllNotificationsByMarkAsReadIsInShouldWork() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where markAsRead in DEFAULT_MARK_AS_READ or UPDATED_MARK_AS_READ
        defaultNotificationShouldBeFound("markAsRead.in=" + DEFAULT_MARK_AS_READ + "," + UPDATED_MARK_AS_READ);

        // Get all the notificationList where markAsRead equals to UPDATED_MARK_AS_READ
        defaultNotificationShouldNotBeFound("markAsRead.in=" + UPDATED_MARK_AS_READ);
    }

    @Test
    @Transactional
    public void getAllNotificationsByMarkAsReadIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        // Get all the notificationList where markAsRead is not null
        defaultNotificationShouldBeFound("markAsRead.specified=true");

        // Get all the notificationList where markAsRead is null
        defaultNotificationShouldNotBeFound("markAsRead.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationsByNotificationMetaDataIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);
        NotificationMetaData notificationMetaData = NotificationMetaDataResourceIT.createEntity(em);
        em.persist(notificationMetaData);
        em.flush();
        notification.addNotificationMetaData(notificationMetaData);
        notificationRepository.saveAndFlush(notification);
        Long notificationMetaDataId = notificationMetaData.getId();

        // Get all the notificationList where notificationMetaData equals to notificationMetaDataId
        defaultNotificationShouldBeFound("notificationMetaDataId.equals=" + notificationMetaDataId);

        // Get all the notificationList where notificationMetaData equals to notificationMetaDataId + 1
        defaultNotificationShouldNotBeFound("notificationMetaDataId.equals=" + (notificationMetaDataId + 1));
    }


    @Test
    @Transactional
    public void getAllNotificationsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        notification.setUser(user);
        notificationRepository.saveAndFlush(notification);
        Long userId = user.getId();

        // Get all the notificationList where user equals to userId
        defaultNotificationShouldBeFound("userId.equals=" + userId);

        // Get all the notificationList where user equals to userId + 1
        defaultNotificationShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllNotificationsByUserGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);
        UserGroup userGroup = UserGroupResourceIT.createEntity(em);
        em.persist(userGroup);
        em.flush();
        notification.setUserGroup(userGroup);
        notificationRepository.saveAndFlush(notification);
        Long userGroupId = userGroup.getId();

        // Get all the notificationList where userGroup equals to userGroupId
        defaultNotificationShouldBeFound("userGroupId.equals=" + userGroupId);

        // Get all the notificationList where userGroup equals to userGroupId + 1
        defaultNotificationShouldNotBeFound("userGroupId.equals=" + (userGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationShouldBeFound(String filter) throws Exception {
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notification.getId().intValue())))
            .andExpect(jsonPath("$.[*].featuredImageContentType").value(hasItem(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].featuredImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE))))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].markAsRead").value(hasItem(DEFAULT_MARK_AS_READ.booleanValue())));

        // Check, that the count call also returns 1
        restNotificationMockMvc.perform(get("/api/notifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationShouldNotBeFound(String filter) throws Exception {
        restNotificationMockMvc.perform(get("/api/notifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationMockMvc.perform(get("/api/notifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNotification() throws Exception {
        // Get the notification
        restNotificationMockMvc.perform(get("/api/notifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Update the notification
        Notification updatedNotification = notificationRepository.findById(notification.getId()).get();
        // Disconnect from session so that the updates on updatedNotification are not directly saved in db
        em.detach(updatedNotification);
        updatedNotification
            .featuredImage(UPDATED_FEATURED_IMAGE)
            .featuredImageContentType(UPDATED_FEATURED_IMAGE_CONTENT_TYPE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .contentType(UPDATED_CONTENT_TYPE)
            .link(UPDATED_LINK)
            .date(UPDATED_DATE)
            .markAsRead(UPDATED_MARK_AS_READ);
        NotificationDTO notificationDTO = notificationMapper.toDto(updatedNotification);

        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isOk());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
        Notification testNotification = notificationList.get(notificationList.size() - 1);
        assertThat(testNotification.getFeaturedImage()).isEqualTo(UPDATED_FEATURED_IMAGE);
        assertThat(testNotification.getFeaturedImageContentType()).isEqualTo(UPDATED_FEATURED_IMAGE_CONTENT_TYPE);
        assertThat(testNotification.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotification.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNotification.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testNotification.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testNotification.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNotification.isMarkAsRead()).isEqualTo(UPDATED_MARK_AS_READ);
    }

    @Test
    @Transactional
    public void updateNonExistingNotification() throws Exception {
        int databaseSizeBeforeUpdate = notificationRepository.findAll().size();

        // Create the Notification
        NotificationDTO notificationDTO = notificationMapper.toDto(notification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMockMvc.perform(put("/api/notifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notification in the database
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotification() throws Exception {
        // Initialize the database
        notificationRepository.saveAndFlush(notification);

        int databaseSizeBeforeDelete = notificationRepository.findAll().size();

        // Delete the notification
        restNotificationMockMvc.perform(delete("/api/notifications/{id}", notification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notification> notificationList = notificationRepository.findAll();
        assertThat(notificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
