package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.NotificationMetaData;
import et.com.hahu.domain.Notification;
import et.com.hahu.repository.NotificationMetaDataRepository;
import et.com.hahu.service.NotificationMetaDataService;
import et.com.hahu.service.dto.NotificationMetaDataDTO;
import et.com.hahu.service.mapper.NotificationMetaDataMapper;
import et.com.hahu.service.dto.NotificationMetaDataCriteria;
import et.com.hahu.service.NotificationMetaDataQueryService;

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
 * Integration tests for the {@link NotificationMetaDataResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NotificationMetaDataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BLOB_VALUE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOB_VALUE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BLOB_VALUE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BLOB_VALUE_CONTENT_TYPE = "image/png";

    @Autowired
    private NotificationMetaDataRepository notificationMetaDataRepository;

    @Autowired
    private NotificationMetaDataMapper notificationMetaDataMapper;

    @Autowired
    private NotificationMetaDataService notificationMetaDataService;

    @Autowired
    private NotificationMetaDataQueryService notificationMetaDataQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationMetaDataMockMvc;

    private NotificationMetaData notificationMetaData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationMetaData createEntity(EntityManager em) {
        NotificationMetaData notificationMetaData = new NotificationMetaData()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .blobValue(DEFAULT_BLOB_VALUE)
            .blobValueContentType(DEFAULT_BLOB_VALUE_CONTENT_TYPE);
        return notificationMetaData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationMetaData createUpdatedEntity(EntityManager em) {
        NotificationMetaData notificationMetaData = new NotificationMetaData()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .blobValue(UPDATED_BLOB_VALUE)
            .blobValueContentType(UPDATED_BLOB_VALUE_CONTENT_TYPE);
        return notificationMetaData;
    }

    @BeforeEach
    public void initTest() {
        notificationMetaData = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificationMetaData() throws Exception {
        int databaseSizeBeforeCreate = notificationMetaDataRepository.findAll().size();
        // Create the NotificationMetaData
        NotificationMetaDataDTO notificationMetaDataDTO = notificationMetaDataMapper.toDto(notificationMetaData);
        restNotificationMetaDataMockMvc.perform(post("/api/notification-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationMetaDataDTO)))
            .andExpect(status().isCreated());

        // Validate the NotificationMetaData in the database
        List<NotificationMetaData> notificationMetaDataList = notificationMetaDataRepository.findAll();
        assertThat(notificationMetaDataList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationMetaData testNotificationMetaData = notificationMetaDataList.get(notificationMetaDataList.size() - 1);
        assertThat(testNotificationMetaData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotificationMetaData.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testNotificationMetaData.getBlobValue()).isEqualTo(DEFAULT_BLOB_VALUE);
        assertThat(testNotificationMetaData.getBlobValueContentType()).isEqualTo(DEFAULT_BLOB_VALUE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createNotificationMetaDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationMetaDataRepository.findAll().size();

        // Create the NotificationMetaData with an existing ID
        notificationMetaData.setId(1L);
        NotificationMetaDataDTO notificationMetaDataDTO = notificationMetaDataMapper.toDto(notificationMetaData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationMetaDataMockMvc.perform(post("/api/notification-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationMetaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationMetaData in the database
        List<NotificationMetaData> notificationMetaDataList = notificationMetaDataRepository.findAll();
        assertThat(notificationMetaDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNotificationMetaData() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList
        restNotificationMetaDataMockMvc.perform(get("/api/notification-meta-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationMetaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].blobValueContentType").value(hasItem(DEFAULT_BLOB_VALUE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blobValue").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_VALUE))));
    }
    
    @Test
    @Transactional
    public void getNotificationMetaData() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get the notificationMetaData
        restNotificationMetaDataMockMvc.perform(get("/api/notification-meta-data/{id}", notificationMetaData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationMetaData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.blobValueContentType").value(DEFAULT_BLOB_VALUE_CONTENT_TYPE))
            .andExpect(jsonPath("$.blobValue").value(Base64Utils.encodeToString(DEFAULT_BLOB_VALUE)));
    }


    @Test
    @Transactional
    public void getNotificationMetaDataByIdFiltering() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        Long id = notificationMetaData.getId();

        defaultNotificationMetaDataShouldBeFound("id.equals=" + id);
        defaultNotificationMetaDataShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationMetaDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationMetaDataShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationMetaDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationMetaDataShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNotificationMetaDataByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where name equals to DEFAULT_NAME
        defaultNotificationMetaDataShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the notificationMetaDataList where name equals to UPDATED_NAME
        defaultNotificationMetaDataShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificationMetaDataByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where name not equals to DEFAULT_NAME
        defaultNotificationMetaDataShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the notificationMetaDataList where name not equals to UPDATED_NAME
        defaultNotificationMetaDataShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificationMetaDataByNameIsInShouldWork() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNotificationMetaDataShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the notificationMetaDataList where name equals to UPDATED_NAME
        defaultNotificationMetaDataShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificationMetaDataByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where name is not null
        defaultNotificationMetaDataShouldBeFound("name.specified=true");

        // Get all the notificationMetaDataList where name is null
        defaultNotificationMetaDataShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificationMetaDataByNameContainsSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where name contains DEFAULT_NAME
        defaultNotificationMetaDataShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the notificationMetaDataList where name contains UPDATED_NAME
        defaultNotificationMetaDataShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNotificationMetaDataByNameNotContainsSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where name does not contain DEFAULT_NAME
        defaultNotificationMetaDataShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the notificationMetaDataList where name does not contain UPDATED_NAME
        defaultNotificationMetaDataShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllNotificationMetaDataByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where value equals to DEFAULT_VALUE
        defaultNotificationMetaDataShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the notificationMetaDataList where value equals to UPDATED_VALUE
        defaultNotificationMetaDataShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNotificationMetaDataByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where value not equals to DEFAULT_VALUE
        defaultNotificationMetaDataShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the notificationMetaDataList where value not equals to UPDATED_VALUE
        defaultNotificationMetaDataShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNotificationMetaDataByValueIsInShouldWork() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultNotificationMetaDataShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the notificationMetaDataList where value equals to UPDATED_VALUE
        defaultNotificationMetaDataShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNotificationMetaDataByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where value is not null
        defaultNotificationMetaDataShouldBeFound("value.specified=true");

        // Get all the notificationMetaDataList where value is null
        defaultNotificationMetaDataShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllNotificationMetaDataByValueContainsSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where value contains DEFAULT_VALUE
        defaultNotificationMetaDataShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the notificationMetaDataList where value contains UPDATED_VALUE
        defaultNotificationMetaDataShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNotificationMetaDataByValueNotContainsSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        // Get all the notificationMetaDataList where value does not contain DEFAULT_VALUE
        defaultNotificationMetaDataShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the notificationMetaDataList where value does not contain UPDATED_VALUE
        defaultNotificationMetaDataShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllNotificationMetaDataByNotificationIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);
        Notification notification = NotificationResourceIT.createEntity(em);
        em.persist(notification);
        em.flush();
        notificationMetaData.setNotification(notification);
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);
        Long notificationId = notification.getId();

        // Get all the notificationMetaDataList where notification equals to notificationId
        defaultNotificationMetaDataShouldBeFound("notificationId.equals=" + notificationId);

        // Get all the notificationMetaDataList where notification equals to notificationId + 1
        defaultNotificationMetaDataShouldNotBeFound("notificationId.equals=" + (notificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationMetaDataShouldBeFound(String filter) throws Exception {
        restNotificationMetaDataMockMvc.perform(get("/api/notification-meta-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationMetaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].blobValueContentType").value(hasItem(DEFAULT_BLOB_VALUE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blobValue").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_VALUE))));

        // Check, that the count call also returns 1
        restNotificationMetaDataMockMvc.perform(get("/api/notification-meta-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationMetaDataShouldNotBeFound(String filter) throws Exception {
        restNotificationMetaDataMockMvc.perform(get("/api/notification-meta-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationMetaDataMockMvc.perform(get("/api/notification-meta-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNotificationMetaData() throws Exception {
        // Get the notificationMetaData
        restNotificationMetaDataMockMvc.perform(get("/api/notification-meta-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificationMetaData() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        int databaseSizeBeforeUpdate = notificationMetaDataRepository.findAll().size();

        // Update the notificationMetaData
        NotificationMetaData updatedNotificationMetaData = notificationMetaDataRepository.findById(notificationMetaData.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationMetaData are not directly saved in db
        em.detach(updatedNotificationMetaData);
        updatedNotificationMetaData
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .blobValue(UPDATED_BLOB_VALUE)
            .blobValueContentType(UPDATED_BLOB_VALUE_CONTENT_TYPE);
        NotificationMetaDataDTO notificationMetaDataDTO = notificationMetaDataMapper.toDto(updatedNotificationMetaData);

        restNotificationMetaDataMockMvc.perform(put("/api/notification-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationMetaDataDTO)))
            .andExpect(status().isOk());

        // Validate the NotificationMetaData in the database
        List<NotificationMetaData> notificationMetaDataList = notificationMetaDataRepository.findAll();
        assertThat(notificationMetaDataList).hasSize(databaseSizeBeforeUpdate);
        NotificationMetaData testNotificationMetaData = notificationMetaDataList.get(notificationMetaDataList.size() - 1);
        assertThat(testNotificationMetaData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotificationMetaData.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testNotificationMetaData.getBlobValue()).isEqualTo(UPDATED_BLOB_VALUE);
        assertThat(testNotificationMetaData.getBlobValueContentType()).isEqualTo(UPDATED_BLOB_VALUE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificationMetaData() throws Exception {
        int databaseSizeBeforeUpdate = notificationMetaDataRepository.findAll().size();

        // Create the NotificationMetaData
        NotificationMetaDataDTO notificationMetaDataDTO = notificationMetaDataMapper.toDto(notificationMetaData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationMetaDataMockMvc.perform(put("/api/notification-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationMetaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationMetaData in the database
        List<NotificationMetaData> notificationMetaDataList = notificationMetaDataRepository.findAll();
        assertThat(notificationMetaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificationMetaData() throws Exception {
        // Initialize the database
        notificationMetaDataRepository.saveAndFlush(notificationMetaData);

        int databaseSizeBeforeDelete = notificationMetaDataRepository.findAll().size();

        // Delete the notificationMetaData
        restNotificationMetaDataMockMvc.perform(delete("/api/notification-meta-data/{id}", notificationMetaData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationMetaData> notificationMetaDataList = notificationMetaDataRepository.findAll();
        assertThat(notificationMetaDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
