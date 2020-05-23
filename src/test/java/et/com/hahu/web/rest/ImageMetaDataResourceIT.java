package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.ImageMetaData;
import et.com.hahu.domain.Image;
import et.com.hahu.repository.ImageMetaDataRepository;
import et.com.hahu.service.ImageMetaDataService;
import et.com.hahu.service.dto.ImageMetaDataDTO;
import et.com.hahu.service.mapper.ImageMetaDataMapper;
import et.com.hahu.service.dto.ImageMetaDataCriteria;
import et.com.hahu.service.ImageMetaDataQueryService;

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
 * Integration tests for the {@link ImageMetaDataResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ImageMetaDataResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ImageMetaDataRepository imageMetaDataRepository;

    @Autowired
    private ImageMetaDataMapper imageMetaDataMapper;

    @Autowired
    private ImageMetaDataService imageMetaDataService;

    @Autowired
    private ImageMetaDataQueryService imageMetaDataQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImageMetaDataMockMvc;

    private ImageMetaData imageMetaData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageMetaData createEntity(EntityManager em) {
        ImageMetaData imageMetaData = new ImageMetaData()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return imageMetaData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageMetaData createUpdatedEntity(EntityManager em) {
        ImageMetaData imageMetaData = new ImageMetaData()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        return imageMetaData;
    }

    @BeforeEach
    public void initTest() {
        imageMetaData = createEntity(em);
    }

    @Test
    @Transactional
    public void createImageMetaData() throws Exception {
        int databaseSizeBeforeCreate = imageMetaDataRepository.findAll().size();
        // Create the ImageMetaData
        ImageMetaDataDTO imageMetaDataDTO = imageMetaDataMapper.toDto(imageMetaData);
        restImageMetaDataMockMvc.perform(post("/api/image-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageMetaDataDTO)))
            .andExpect(status().isCreated());

        // Validate the ImageMetaData in the database
        List<ImageMetaData> imageMetaDataList = imageMetaDataRepository.findAll();
        assertThat(imageMetaDataList).hasSize(databaseSizeBeforeCreate + 1);
        ImageMetaData testImageMetaData = imageMetaDataList.get(imageMetaDataList.size() - 1);
        assertThat(testImageMetaData.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImageMetaData.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createImageMetaDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageMetaDataRepository.findAll().size();

        // Create the ImageMetaData with an existing ID
        imageMetaData.setId(1L);
        ImageMetaDataDTO imageMetaDataDTO = imageMetaDataMapper.toDto(imageMetaData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMetaDataMockMvc.perform(post("/api/image-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageMetaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImageMetaData in the database
        List<ImageMetaData> imageMetaDataList = imageMetaDataRepository.findAll();
        assertThat(imageMetaDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImageMetaData() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList
        restImageMetaDataMockMvc.perform(get("/api/image-meta-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageMetaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getImageMetaData() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get the imageMetaData
        restImageMetaDataMockMvc.perform(get("/api/image-meta-data/{id}", imageMetaData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imageMetaData.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getImageMetaDataByIdFiltering() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        Long id = imageMetaData.getId();

        defaultImageMetaDataShouldBeFound("id.equals=" + id);
        defaultImageMetaDataShouldNotBeFound("id.notEquals=" + id);

        defaultImageMetaDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultImageMetaDataShouldNotBeFound("id.greaterThan=" + id);

        defaultImageMetaDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultImageMetaDataShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllImageMetaDataByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where name equals to DEFAULT_NAME
        defaultImageMetaDataShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the imageMetaDataList where name equals to UPDATED_NAME
        defaultImageMetaDataShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllImageMetaDataByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where name not equals to DEFAULT_NAME
        defaultImageMetaDataShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the imageMetaDataList where name not equals to UPDATED_NAME
        defaultImageMetaDataShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllImageMetaDataByNameIsInShouldWork() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where name in DEFAULT_NAME or UPDATED_NAME
        defaultImageMetaDataShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the imageMetaDataList where name equals to UPDATED_NAME
        defaultImageMetaDataShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllImageMetaDataByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where name is not null
        defaultImageMetaDataShouldBeFound("name.specified=true");

        // Get all the imageMetaDataList where name is null
        defaultImageMetaDataShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllImageMetaDataByNameContainsSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where name contains DEFAULT_NAME
        defaultImageMetaDataShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the imageMetaDataList where name contains UPDATED_NAME
        defaultImageMetaDataShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllImageMetaDataByNameNotContainsSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where name does not contain DEFAULT_NAME
        defaultImageMetaDataShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the imageMetaDataList where name does not contain UPDATED_NAME
        defaultImageMetaDataShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllImageMetaDataByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where value equals to DEFAULT_VALUE
        defaultImageMetaDataShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the imageMetaDataList where value equals to UPDATED_VALUE
        defaultImageMetaDataShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllImageMetaDataByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where value not equals to DEFAULT_VALUE
        defaultImageMetaDataShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the imageMetaDataList where value not equals to UPDATED_VALUE
        defaultImageMetaDataShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllImageMetaDataByValueIsInShouldWork() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultImageMetaDataShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the imageMetaDataList where value equals to UPDATED_VALUE
        defaultImageMetaDataShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllImageMetaDataByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where value is not null
        defaultImageMetaDataShouldBeFound("value.specified=true");

        // Get all the imageMetaDataList where value is null
        defaultImageMetaDataShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllImageMetaDataByValueContainsSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where value contains DEFAULT_VALUE
        defaultImageMetaDataShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the imageMetaDataList where value contains UPDATED_VALUE
        defaultImageMetaDataShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllImageMetaDataByValueNotContainsSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        // Get all the imageMetaDataList where value does not contain DEFAULT_VALUE
        defaultImageMetaDataShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the imageMetaDataList where value does not contain UPDATED_VALUE
        defaultImageMetaDataShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllImageMetaDataByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);
        Image image = ImageResourceIT.createEntity(em);
        em.persist(image);
        em.flush();
        imageMetaData.setImage(image);
        imageMetaDataRepository.saveAndFlush(imageMetaData);
        Long imageId = image.getId();

        // Get all the imageMetaDataList where image equals to imageId
        defaultImageMetaDataShouldBeFound("imageId.equals=" + imageId);

        // Get all the imageMetaDataList where image equals to imageId + 1
        defaultImageMetaDataShouldNotBeFound("imageId.equals=" + (imageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImageMetaDataShouldBeFound(String filter) throws Exception {
        restImageMetaDataMockMvc.perform(get("/api/image-meta-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageMetaData.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restImageMetaDataMockMvc.perform(get("/api/image-meta-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImageMetaDataShouldNotBeFound(String filter) throws Exception {
        restImageMetaDataMockMvc.perform(get("/api/image-meta-data?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImageMetaDataMockMvc.perform(get("/api/image-meta-data/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingImageMetaData() throws Exception {
        // Get the imageMetaData
        restImageMetaDataMockMvc.perform(get("/api/image-meta-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageMetaData() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        int databaseSizeBeforeUpdate = imageMetaDataRepository.findAll().size();

        // Update the imageMetaData
        ImageMetaData updatedImageMetaData = imageMetaDataRepository.findById(imageMetaData.getId()).get();
        // Disconnect from session so that the updates on updatedImageMetaData are not directly saved in db
        em.detach(updatedImageMetaData);
        updatedImageMetaData
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        ImageMetaDataDTO imageMetaDataDTO = imageMetaDataMapper.toDto(updatedImageMetaData);

        restImageMetaDataMockMvc.perform(put("/api/image-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageMetaDataDTO)))
            .andExpect(status().isOk());

        // Validate the ImageMetaData in the database
        List<ImageMetaData> imageMetaDataList = imageMetaDataRepository.findAll();
        assertThat(imageMetaDataList).hasSize(databaseSizeBeforeUpdate);
        ImageMetaData testImageMetaData = imageMetaDataList.get(imageMetaDataList.size() - 1);
        assertThat(testImageMetaData.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImageMetaData.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingImageMetaData() throws Exception {
        int databaseSizeBeforeUpdate = imageMetaDataRepository.findAll().size();

        // Create the ImageMetaData
        ImageMetaDataDTO imageMetaDataDTO = imageMetaDataMapper.toDto(imageMetaData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageMetaDataMockMvc.perform(put("/api/image-meta-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(imageMetaDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImageMetaData in the database
        List<ImageMetaData> imageMetaDataList = imageMetaDataRepository.findAll();
        assertThat(imageMetaDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImageMetaData() throws Exception {
        // Initialize the database
        imageMetaDataRepository.saveAndFlush(imageMetaData);

        int databaseSizeBeforeDelete = imageMetaDataRepository.findAll().size();

        // Delete the imageMetaData
        restImageMetaDataMockMvc.perform(delete("/api/image-meta-data/{id}", imageMetaData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImageMetaData> imageMetaDataList = imageMetaDataRepository.findAll();
        assertThat(imageMetaDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
