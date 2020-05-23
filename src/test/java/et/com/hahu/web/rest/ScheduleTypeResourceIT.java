package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.ScheduleType;
import et.com.hahu.domain.Schedule;
import et.com.hahu.repository.ScheduleTypeRepository;
import et.com.hahu.service.ScheduleTypeService;
import et.com.hahu.service.dto.ScheduleTypeDTO;
import et.com.hahu.service.mapper.ScheduleTypeMapper;
import et.com.hahu.service.dto.ScheduleTypeCriteria;
import et.com.hahu.service.ScheduleTypeQueryService;

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
 * Integration tests for the {@link ScheduleTypeResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScheduleTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ScheduleTypeRepository scheduleTypeRepository;

    @Autowired
    private ScheduleTypeMapper scheduleTypeMapper;

    @Autowired
    private ScheduleTypeService scheduleTypeService;

    @Autowired
    private ScheduleTypeQueryService scheduleTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduleTypeMockMvc;

    private ScheduleType scheduleType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleType createEntity(EntityManager em) {
        ScheduleType scheduleType = new ScheduleType()
            .name(DEFAULT_NAME);
        return scheduleType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduleType createUpdatedEntity(EntityManager em) {
        ScheduleType scheduleType = new ScheduleType()
            .name(UPDATED_NAME);
        return scheduleType;
    }

    @BeforeEach
    public void initTest() {
        scheduleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduleType() throws Exception {
        int databaseSizeBeforeCreate = scheduleTypeRepository.findAll().size();
        // Create the ScheduleType
        ScheduleTypeDTO scheduleTypeDTO = scheduleTypeMapper.toDto(scheduleType);
        restScheduleTypeMockMvc.perform(post("/api/schedule-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ScheduleType in the database
        List<ScheduleType> scheduleTypeList = scheduleTypeRepository.findAll();
        assertThat(scheduleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduleType testScheduleType = scheduleTypeList.get(scheduleTypeList.size() - 1);
        assertThat(testScheduleType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createScheduleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduleTypeRepository.findAll().size();

        // Create the ScheduleType with an existing ID
        scheduleType.setId(1L);
        ScheduleTypeDTO scheduleTypeDTO = scheduleTypeMapper.toDto(scheduleType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduleTypeMockMvc.perform(post("/api/schedule-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleType in the database
        List<ScheduleType> scheduleTypeList = scheduleTypeRepository.findAll();
        assertThat(scheduleTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScheduleTypes() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        // Get all the scheduleTypeList
        restScheduleTypeMockMvc.perform(get("/api/schedule-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getScheduleType() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        // Get the scheduleType
        restScheduleTypeMockMvc.perform(get("/api/schedule-types/{id}", scheduleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheduleType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getScheduleTypesByIdFiltering() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        Long id = scheduleType.getId();

        defaultScheduleTypeShouldBeFound("id.equals=" + id);
        defaultScheduleTypeShouldNotBeFound("id.notEquals=" + id);

        defaultScheduleTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScheduleTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultScheduleTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScheduleTypeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllScheduleTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        // Get all the scheduleTypeList where name equals to DEFAULT_NAME
        defaultScheduleTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the scheduleTypeList where name equals to UPDATED_NAME
        defaultScheduleTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScheduleTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        // Get all the scheduleTypeList where name not equals to DEFAULT_NAME
        defaultScheduleTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the scheduleTypeList where name not equals to UPDATED_NAME
        defaultScheduleTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScheduleTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        // Get all the scheduleTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultScheduleTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the scheduleTypeList where name equals to UPDATED_NAME
        defaultScheduleTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScheduleTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        // Get all the scheduleTypeList where name is not null
        defaultScheduleTypeShouldBeFound("name.specified=true");

        // Get all the scheduleTypeList where name is null
        defaultScheduleTypeShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllScheduleTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        // Get all the scheduleTypeList where name contains DEFAULT_NAME
        defaultScheduleTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the scheduleTypeList where name contains UPDATED_NAME
        defaultScheduleTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScheduleTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        // Get all the scheduleTypeList where name does not contain DEFAULT_NAME
        defaultScheduleTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the scheduleTypeList where name does not contain UPDATED_NAME
        defaultScheduleTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllScheduleTypesByScheduleIsEqualToSomething() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);
        Schedule schedule = ScheduleResourceIT.createEntity(em);
        em.persist(schedule);
        em.flush();
        scheduleType.addSchedule(schedule);
        scheduleTypeRepository.saveAndFlush(scheduleType);
        Long scheduleId = schedule.getId();

        // Get all the scheduleTypeList where schedule equals to scheduleId
        defaultScheduleTypeShouldBeFound("scheduleId.equals=" + scheduleId);

        // Get all the scheduleTypeList where schedule equals to scheduleId + 1
        defaultScheduleTypeShouldNotBeFound("scheduleId.equals=" + (scheduleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScheduleTypeShouldBeFound(String filter) throws Exception {
        restScheduleTypeMockMvc.perform(get("/api/schedule-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restScheduleTypeMockMvc.perform(get("/api/schedule-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScheduleTypeShouldNotBeFound(String filter) throws Exception {
        restScheduleTypeMockMvc.perform(get("/api/schedule-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScheduleTypeMockMvc.perform(get("/api/schedule-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingScheduleType() throws Exception {
        // Get the scheduleType
        restScheduleTypeMockMvc.perform(get("/api/schedule-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduleType() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        int databaseSizeBeforeUpdate = scheduleTypeRepository.findAll().size();

        // Update the scheduleType
        ScheduleType updatedScheduleType = scheduleTypeRepository.findById(scheduleType.getId()).get();
        // Disconnect from session so that the updates on updatedScheduleType are not directly saved in db
        em.detach(updatedScheduleType);
        updatedScheduleType
            .name(UPDATED_NAME);
        ScheduleTypeDTO scheduleTypeDTO = scheduleTypeMapper.toDto(updatedScheduleType);

        restScheduleTypeMockMvc.perform(put("/api/schedule-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ScheduleType in the database
        List<ScheduleType> scheduleTypeList = scheduleTypeRepository.findAll();
        assertThat(scheduleTypeList).hasSize(databaseSizeBeforeUpdate);
        ScheduleType testScheduleType = scheduleTypeList.get(scheduleTypeList.size() - 1);
        assertThat(testScheduleType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduleType() throws Exception {
        int databaseSizeBeforeUpdate = scheduleTypeRepository.findAll().size();

        // Create the ScheduleType
        ScheduleTypeDTO scheduleTypeDTO = scheduleTypeMapper.toDto(scheduleType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduleTypeMockMvc.perform(put("/api/schedule-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduleTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduleType in the database
        List<ScheduleType> scheduleTypeList = scheduleTypeRepository.findAll();
        assertThat(scheduleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduleType() throws Exception {
        // Initialize the database
        scheduleTypeRepository.saveAndFlush(scheduleType);

        int databaseSizeBeforeDelete = scheduleTypeRepository.findAll().size();

        // Delete the scheduleType
        restScheduleTypeMockMvc.perform(delete("/api/schedule-types/{id}", scheduleType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScheduleType> scheduleTypeList = scheduleTypeRepository.findAll();
        assertThat(scheduleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
