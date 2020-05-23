package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.SchoolProgress;
import et.com.hahu.domain.User;
import et.com.hahu.repository.SchoolProgressRepository;
import et.com.hahu.service.SchoolProgressService;
import et.com.hahu.service.dto.SchoolProgressDTO;
import et.com.hahu.service.mapper.SchoolProgressMapper;
import et.com.hahu.service.dto.SchoolProgressCriteria;
import et.com.hahu.service.SchoolProgressQueryService;

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
 * Integration tests for the {@link SchoolProgressResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SchoolProgressResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final String DEFAULT_SEMESTER = "AAAAAAAAAA";
    private static final String UPDATED_SEMESTER = "BBBBBBBBBB";

    private static final Double DEFAULT_RESULT = 1D;
    private static final Double UPDATED_RESULT = 2D;
    private static final Double SMALLER_RESULT = 1D - 1D;

    @Autowired
    private SchoolProgressRepository schoolProgressRepository;

    @Autowired
    private SchoolProgressMapper schoolProgressMapper;

    @Autowired
    private SchoolProgressService schoolProgressService;

    @Autowired
    private SchoolProgressQueryService schoolProgressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchoolProgressMockMvc;

    private SchoolProgress schoolProgress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchoolProgress createEntity(EntityManager em) {
        SchoolProgress schoolProgress = new SchoolProgress()
            .subject(DEFAULT_SUBJECT)
            .year(DEFAULT_YEAR)
            .semester(DEFAULT_SEMESTER)
            .result(DEFAULT_RESULT);
        return schoolProgress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchoolProgress createUpdatedEntity(EntityManager em) {
        SchoolProgress schoolProgress = new SchoolProgress()
            .subject(UPDATED_SUBJECT)
            .year(UPDATED_YEAR)
            .semester(UPDATED_SEMESTER)
            .result(UPDATED_RESULT);
        return schoolProgress;
    }

    @BeforeEach
    public void initTest() {
        schoolProgress = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchoolProgress() throws Exception {
        int databaseSizeBeforeCreate = schoolProgressRepository.findAll().size();
        // Create the SchoolProgress
        SchoolProgressDTO schoolProgressDTO = schoolProgressMapper.toDto(schoolProgress);
        restSchoolProgressMockMvc.perform(post("/api/school-progresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schoolProgressDTO)))
            .andExpect(status().isCreated());

        // Validate the SchoolProgress in the database
        List<SchoolProgress> schoolProgressList = schoolProgressRepository.findAll();
        assertThat(schoolProgressList).hasSize(databaseSizeBeforeCreate + 1);
        SchoolProgress testSchoolProgress = schoolProgressList.get(schoolProgressList.size() - 1);
        assertThat(testSchoolProgress.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testSchoolProgress.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSchoolProgress.getSemester()).isEqualTo(DEFAULT_SEMESTER);
        assertThat(testSchoolProgress.getResult()).isEqualTo(DEFAULT_RESULT);
    }

    @Test
    @Transactional
    public void createSchoolProgressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolProgressRepository.findAll().size();

        // Create the SchoolProgress with an existing ID
        schoolProgress.setId(1L);
        SchoolProgressDTO schoolProgressDTO = schoolProgressMapper.toDto(schoolProgress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolProgressMockMvc.perform(post("/api/school-progresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schoolProgressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolProgress in the database
        List<SchoolProgress> schoolProgressList = schoolProgressRepository.findAll();
        assertThat(schoolProgressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSchoolProgresses() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList
        restSchoolProgressMockMvc.perform(get("/api/school-progresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSchoolProgress() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get the schoolProgress
        restSchoolProgressMockMvc.perform(get("/api/school-progresses/{id}", schoolProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schoolProgress.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.semester").value(DEFAULT_SEMESTER))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.doubleValue()));
    }


    @Test
    @Transactional
    public void getSchoolProgressesByIdFiltering() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        Long id = schoolProgress.getId();

        defaultSchoolProgressShouldBeFound("id.equals=" + id);
        defaultSchoolProgressShouldNotBeFound("id.notEquals=" + id);

        defaultSchoolProgressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSchoolProgressShouldNotBeFound("id.greaterThan=" + id);

        defaultSchoolProgressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSchoolProgressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSchoolProgressesBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where subject equals to DEFAULT_SUBJECT
        defaultSchoolProgressShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the schoolProgressList where subject equals to UPDATED_SUBJECT
        defaultSchoolProgressShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesBySubjectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where subject not equals to DEFAULT_SUBJECT
        defaultSchoolProgressShouldNotBeFound("subject.notEquals=" + DEFAULT_SUBJECT);

        // Get all the schoolProgressList where subject not equals to UPDATED_SUBJECT
        defaultSchoolProgressShouldBeFound("subject.notEquals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultSchoolProgressShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the schoolProgressList where subject equals to UPDATED_SUBJECT
        defaultSchoolProgressShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where subject is not null
        defaultSchoolProgressShouldBeFound("subject.specified=true");

        // Get all the schoolProgressList where subject is null
        defaultSchoolProgressShouldNotBeFound("subject.specified=false");
    }
                @Test
    @Transactional
    public void getAllSchoolProgressesBySubjectContainsSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where subject contains DEFAULT_SUBJECT
        defaultSchoolProgressShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the schoolProgressList where subject contains UPDATED_SUBJECT
        defaultSchoolProgressShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where subject does not contain DEFAULT_SUBJECT
        defaultSchoolProgressShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the schoolProgressList where subject does not contain UPDATED_SUBJECT
        defaultSchoolProgressShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }


    @Test
    @Transactional
    public void getAllSchoolProgressesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where year equals to DEFAULT_YEAR
        defaultSchoolProgressShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the schoolProgressList where year equals to UPDATED_YEAR
        defaultSchoolProgressShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where year not equals to DEFAULT_YEAR
        defaultSchoolProgressShouldNotBeFound("year.notEquals=" + DEFAULT_YEAR);

        // Get all the schoolProgressList where year not equals to UPDATED_YEAR
        defaultSchoolProgressShouldBeFound("year.notEquals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultSchoolProgressShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the schoolProgressList where year equals to UPDATED_YEAR
        defaultSchoolProgressShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where year is not null
        defaultSchoolProgressShouldBeFound("year.specified=true");

        // Get all the schoolProgressList where year is null
        defaultSchoolProgressShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where year is greater than or equal to DEFAULT_YEAR
        defaultSchoolProgressShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the schoolProgressList where year is greater than or equal to UPDATED_YEAR
        defaultSchoolProgressShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where year is less than or equal to DEFAULT_YEAR
        defaultSchoolProgressShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the schoolProgressList where year is less than or equal to SMALLER_YEAR
        defaultSchoolProgressShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where year is less than DEFAULT_YEAR
        defaultSchoolProgressShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the schoolProgressList where year is less than UPDATED_YEAR
        defaultSchoolProgressShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where year is greater than DEFAULT_YEAR
        defaultSchoolProgressShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the schoolProgressList where year is greater than SMALLER_YEAR
        defaultSchoolProgressShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }


    @Test
    @Transactional
    public void getAllSchoolProgressesBySemesterIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where semester equals to DEFAULT_SEMESTER
        defaultSchoolProgressShouldBeFound("semester.equals=" + DEFAULT_SEMESTER);

        // Get all the schoolProgressList where semester equals to UPDATED_SEMESTER
        defaultSchoolProgressShouldNotBeFound("semester.equals=" + UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesBySemesterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where semester not equals to DEFAULT_SEMESTER
        defaultSchoolProgressShouldNotBeFound("semester.notEquals=" + DEFAULT_SEMESTER);

        // Get all the schoolProgressList where semester not equals to UPDATED_SEMESTER
        defaultSchoolProgressShouldBeFound("semester.notEquals=" + UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesBySemesterIsInShouldWork() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where semester in DEFAULT_SEMESTER or UPDATED_SEMESTER
        defaultSchoolProgressShouldBeFound("semester.in=" + DEFAULT_SEMESTER + "," + UPDATED_SEMESTER);

        // Get all the schoolProgressList where semester equals to UPDATED_SEMESTER
        defaultSchoolProgressShouldNotBeFound("semester.in=" + UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesBySemesterIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where semester is not null
        defaultSchoolProgressShouldBeFound("semester.specified=true");

        // Get all the schoolProgressList where semester is null
        defaultSchoolProgressShouldNotBeFound("semester.specified=false");
    }
                @Test
    @Transactional
    public void getAllSchoolProgressesBySemesterContainsSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where semester contains DEFAULT_SEMESTER
        defaultSchoolProgressShouldBeFound("semester.contains=" + DEFAULT_SEMESTER);

        // Get all the schoolProgressList where semester contains UPDATED_SEMESTER
        defaultSchoolProgressShouldNotBeFound("semester.contains=" + UPDATED_SEMESTER);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesBySemesterNotContainsSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where semester does not contain DEFAULT_SEMESTER
        defaultSchoolProgressShouldNotBeFound("semester.doesNotContain=" + DEFAULT_SEMESTER);

        // Get all the schoolProgressList where semester does not contain UPDATED_SEMESTER
        defaultSchoolProgressShouldBeFound("semester.doesNotContain=" + UPDATED_SEMESTER);
    }


    @Test
    @Transactional
    public void getAllSchoolProgressesByResultIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where result equals to DEFAULT_RESULT
        defaultSchoolProgressShouldBeFound("result.equals=" + DEFAULT_RESULT);

        // Get all the schoolProgressList where result equals to UPDATED_RESULT
        defaultSchoolProgressShouldNotBeFound("result.equals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where result not equals to DEFAULT_RESULT
        defaultSchoolProgressShouldNotBeFound("result.notEquals=" + DEFAULT_RESULT);

        // Get all the schoolProgressList where result not equals to UPDATED_RESULT
        defaultSchoolProgressShouldBeFound("result.notEquals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByResultIsInShouldWork() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where result in DEFAULT_RESULT or UPDATED_RESULT
        defaultSchoolProgressShouldBeFound("result.in=" + DEFAULT_RESULT + "," + UPDATED_RESULT);

        // Get all the schoolProgressList where result equals to UPDATED_RESULT
        defaultSchoolProgressShouldNotBeFound("result.in=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where result is not null
        defaultSchoolProgressShouldBeFound("result.specified=true");

        // Get all the schoolProgressList where result is null
        defaultSchoolProgressShouldNotBeFound("result.specified=false");
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByResultIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where result is greater than or equal to DEFAULT_RESULT
        defaultSchoolProgressShouldBeFound("result.greaterThanOrEqual=" + DEFAULT_RESULT);

        // Get all the schoolProgressList where result is greater than or equal to UPDATED_RESULT
        defaultSchoolProgressShouldNotBeFound("result.greaterThanOrEqual=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByResultIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where result is less than or equal to DEFAULT_RESULT
        defaultSchoolProgressShouldBeFound("result.lessThanOrEqual=" + DEFAULT_RESULT);

        // Get all the schoolProgressList where result is less than or equal to SMALLER_RESULT
        defaultSchoolProgressShouldNotBeFound("result.lessThanOrEqual=" + SMALLER_RESULT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByResultIsLessThanSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where result is less than DEFAULT_RESULT
        defaultSchoolProgressShouldNotBeFound("result.lessThan=" + DEFAULT_RESULT);

        // Get all the schoolProgressList where result is less than UPDATED_RESULT
        defaultSchoolProgressShouldBeFound("result.lessThan=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSchoolProgressesByResultIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        // Get all the schoolProgressList where result is greater than DEFAULT_RESULT
        defaultSchoolProgressShouldNotBeFound("result.greaterThan=" + DEFAULT_RESULT);

        // Get all the schoolProgressList where result is greater than SMALLER_RESULT
        defaultSchoolProgressShouldBeFound("result.greaterThan=" + SMALLER_RESULT);
    }


    @Test
    @Transactional
    public void getAllSchoolProgressesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        schoolProgress.setUser(user);
        schoolProgressRepository.saveAndFlush(schoolProgress);
        Long userId = user.getId();

        // Get all the schoolProgressList where user equals to userId
        defaultSchoolProgressShouldBeFound("userId.equals=" + userId);

        // Get all the schoolProgressList where user equals to userId + 1
        defaultSchoolProgressShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSchoolProgressShouldBeFound(String filter) throws Exception {
        restSchoolProgressMockMvc.perform(get("/api/school-progresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].semester").value(hasItem(DEFAULT_SEMESTER)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.doubleValue())));

        // Check, that the count call also returns 1
        restSchoolProgressMockMvc.perform(get("/api/school-progresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSchoolProgressShouldNotBeFound(String filter) throws Exception {
        restSchoolProgressMockMvc.perform(get("/api/school-progresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSchoolProgressMockMvc.perform(get("/api/school-progresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSchoolProgress() throws Exception {
        // Get the schoolProgress
        restSchoolProgressMockMvc.perform(get("/api/school-progresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchoolProgress() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        int databaseSizeBeforeUpdate = schoolProgressRepository.findAll().size();

        // Update the schoolProgress
        SchoolProgress updatedSchoolProgress = schoolProgressRepository.findById(schoolProgress.getId()).get();
        // Disconnect from session so that the updates on updatedSchoolProgress are not directly saved in db
        em.detach(updatedSchoolProgress);
        updatedSchoolProgress
            .subject(UPDATED_SUBJECT)
            .year(UPDATED_YEAR)
            .semester(UPDATED_SEMESTER)
            .result(UPDATED_RESULT);
        SchoolProgressDTO schoolProgressDTO = schoolProgressMapper.toDto(updatedSchoolProgress);

        restSchoolProgressMockMvc.perform(put("/api/school-progresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schoolProgressDTO)))
            .andExpect(status().isOk());

        // Validate the SchoolProgress in the database
        List<SchoolProgress> schoolProgressList = schoolProgressRepository.findAll();
        assertThat(schoolProgressList).hasSize(databaseSizeBeforeUpdate);
        SchoolProgress testSchoolProgress = schoolProgressList.get(schoolProgressList.size() - 1);
        assertThat(testSchoolProgress.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testSchoolProgress.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSchoolProgress.getSemester()).isEqualTo(UPDATED_SEMESTER);
        assertThat(testSchoolProgress.getResult()).isEqualTo(UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void updateNonExistingSchoolProgress() throws Exception {
        int databaseSizeBeforeUpdate = schoolProgressRepository.findAll().size();

        // Create the SchoolProgress
        SchoolProgressDTO schoolProgressDTO = schoolProgressMapper.toDto(schoolProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolProgressMockMvc.perform(put("/api/school-progresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schoolProgressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolProgress in the database
        List<SchoolProgress> schoolProgressList = schoolProgressRepository.findAll();
        assertThat(schoolProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchoolProgress() throws Exception {
        // Initialize the database
        schoolProgressRepository.saveAndFlush(schoolProgress);

        int databaseSizeBeforeDelete = schoolProgressRepository.findAll().size();

        // Delete the schoolProgress
        restSchoolProgressMockMvc.perform(delete("/api/school-progresses/{id}", schoolProgress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchoolProgress> schoolProgressList = schoolProgressRepository.findAll();
        assertThat(schoolProgressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
