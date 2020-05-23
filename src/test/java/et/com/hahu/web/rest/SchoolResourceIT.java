package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.School;
import et.com.hahu.domain.UserGroup;
import et.com.hahu.domain.User;
import et.com.hahu.repository.SchoolRepository;
import et.com.hahu.service.SchoolService;
import et.com.hahu.service.dto.SchoolDTO;
import et.com.hahu.service.mapper.SchoolMapper;
import et.com.hahu.service.dto.SchoolCriteria;
import et.com.hahu.service.SchoolQueryService;

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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import et.com.hahu.domain.enumeration.ContentType;
import et.com.hahu.domain.enumeration.ContentType;
/**
 * Integration tests for the {@link SchoolResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SchoolResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FEATURED_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FEATURED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FEATURED_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FEATURED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PHONE = "+313241834374";
    private static final String UPDATED_PHONE = "+487472667745";

    private static final String DEFAULT_EMAIL = "\"p^@pH.w[L";
    private static final String UPDATED_EMAIL = "16>iF@r.<l";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final ContentType DEFAULT_ABOUT_TYPE = ContentType.TEXT;
    private static final ContentType UPDATED_ABOUT_TYPE = ContentType.HTML;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final ContentType DEFAULT_LOCATION_TYPE = ContentType.TEXT;
    private static final ContentType UPDATED_LOCATION_TYPE = ContentType.HTML;

    @Autowired
    private SchoolRepository schoolRepository;

    @Mock
    private SchoolRepository schoolRepositoryMock;

    @Autowired
    private SchoolMapper schoolMapper;

    @Mock
    private SchoolService schoolServiceMock;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolQueryService schoolQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchoolMockMvc;

    private School school;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createEntity(EntityManager em) {
        School school = new School()
            .name(DEFAULT_NAME)
            .featuredImage(DEFAULT_FEATURED_IMAGE)
            .featuredImageContentType(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .website(DEFAULT_WEBSITE)
            .about(DEFAULT_ABOUT)
            .aboutType(DEFAULT_ABOUT_TYPE)
            .location(DEFAULT_LOCATION)
            .locationType(DEFAULT_LOCATION_TYPE);
        return school;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createUpdatedEntity(EntityManager em) {
        School school = new School()
            .name(UPDATED_NAME)
            .featuredImage(UPDATED_FEATURED_IMAGE)
            .featuredImageContentType(UPDATED_FEATURED_IMAGE_CONTENT_TYPE)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .about(UPDATED_ABOUT)
            .aboutType(UPDATED_ABOUT_TYPE)
            .location(UPDATED_LOCATION)
            .locationType(UPDATED_LOCATION_TYPE);
        return school;
    }

    @BeforeEach
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchool.getFeaturedImage()).isEqualTo(DEFAULT_FEATURED_IMAGE);
        assertThat(testSchool.getFeaturedImageContentType()).isEqualTo(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE);
        assertThat(testSchool.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSchool.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSchool.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testSchool.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testSchool.getAboutType()).isEqualTo(DEFAULT_ABOUT_TYPE);
        assertThat(testSchool.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testSchool.getLocationType()).isEqualTo(DEFAULT_LOCATION_TYPE);
    }

    @Test
    @Transactional
    public void createSchoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School with an existing ID
        school.setId(1L);
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].featuredImageContentType").value(hasItem(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].featuredImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].aboutType").value(hasItem(DEFAULT_ABOUT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSchoolsWithEagerRelationshipsIsEnabled() throws Exception {
        when(schoolServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSchoolMockMvc.perform(get("/api/schools?eagerload=true"))
            .andExpect(status().isOk());

        verify(schoolServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSchoolsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(schoolServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSchoolMockMvc.perform(get("/api/schools?eagerload=true"))
            .andExpect(status().isOk());

        verify(schoolServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.featuredImageContentType").value(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.featuredImage").value(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE)))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()))
            .andExpect(jsonPath("$.aboutType").value(DEFAULT_ABOUT_TYPE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.locationType").value(DEFAULT_LOCATION_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getSchoolsByIdFiltering() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        Long id = school.getId();

        defaultSchoolShouldBeFound("id.equals=" + id);
        defaultSchoolShouldNotBeFound("id.notEquals=" + id);

        defaultSchoolShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSchoolShouldNotBeFound("id.greaterThan=" + id);

        defaultSchoolShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSchoolShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSchoolsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name equals to DEFAULT_NAME
        defaultSchoolShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the schoolList where name equals to UPDATED_NAME
        defaultSchoolShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSchoolsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name not equals to DEFAULT_NAME
        defaultSchoolShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the schoolList where name not equals to UPDATED_NAME
        defaultSchoolShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSchoolsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSchoolShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the schoolList where name equals to UPDATED_NAME
        defaultSchoolShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSchoolsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name is not null
        defaultSchoolShouldBeFound("name.specified=true");

        // Get all the schoolList where name is null
        defaultSchoolShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSchoolsByNameContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name contains DEFAULT_NAME
        defaultSchoolShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the schoolList where name contains UPDATED_NAME
        defaultSchoolShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSchoolsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where name does not contain DEFAULT_NAME
        defaultSchoolShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the schoolList where name does not contain UPDATED_NAME
        defaultSchoolShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSchoolsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phone equals to DEFAULT_PHONE
        defaultSchoolShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the schoolList where phone equals to UPDATED_PHONE
        defaultSchoolShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phone not equals to DEFAULT_PHONE
        defaultSchoolShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the schoolList where phone not equals to UPDATED_PHONE
        defaultSchoolShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultSchoolShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the schoolList where phone equals to UPDATED_PHONE
        defaultSchoolShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phone is not null
        defaultSchoolShouldBeFound("phone.specified=true");

        // Get all the schoolList where phone is null
        defaultSchoolShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllSchoolsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phone contains DEFAULT_PHONE
        defaultSchoolShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the schoolList where phone contains UPDATED_PHONE
        defaultSchoolShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where phone does not contain DEFAULT_PHONE
        defaultSchoolShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the schoolList where phone does not contain UPDATED_PHONE
        defaultSchoolShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllSchoolsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email equals to DEFAULT_EMAIL
        defaultSchoolShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the schoolList where email equals to UPDATED_EMAIL
        defaultSchoolShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSchoolsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email not equals to DEFAULT_EMAIL
        defaultSchoolShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the schoolList where email not equals to UPDATED_EMAIL
        defaultSchoolShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSchoolsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSchoolShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the schoolList where email equals to UPDATED_EMAIL
        defaultSchoolShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSchoolsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email is not null
        defaultSchoolShouldBeFound("email.specified=true");

        // Get all the schoolList where email is null
        defaultSchoolShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllSchoolsByEmailContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email contains DEFAULT_EMAIL
        defaultSchoolShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the schoolList where email contains UPDATED_EMAIL
        defaultSchoolShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllSchoolsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where email does not contain DEFAULT_EMAIL
        defaultSchoolShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the schoolList where email does not contain UPDATED_EMAIL
        defaultSchoolShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllSchoolsByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where website equals to DEFAULT_WEBSITE
        defaultSchoolShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the schoolList where website equals to UPDATED_WEBSITE
        defaultSchoolShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByWebsiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where website not equals to DEFAULT_WEBSITE
        defaultSchoolShouldNotBeFound("website.notEquals=" + DEFAULT_WEBSITE);

        // Get all the schoolList where website not equals to UPDATED_WEBSITE
        defaultSchoolShouldBeFound("website.notEquals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultSchoolShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the schoolList where website equals to UPDATED_WEBSITE
        defaultSchoolShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where website is not null
        defaultSchoolShouldBeFound("website.specified=true");

        // Get all the schoolList where website is null
        defaultSchoolShouldNotBeFound("website.specified=false");
    }
                @Test
    @Transactional
    public void getAllSchoolsByWebsiteContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where website contains DEFAULT_WEBSITE
        defaultSchoolShouldBeFound("website.contains=" + DEFAULT_WEBSITE);

        // Get all the schoolList where website contains UPDATED_WEBSITE
        defaultSchoolShouldNotBeFound("website.contains=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByWebsiteNotContainsSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where website does not contain DEFAULT_WEBSITE
        defaultSchoolShouldNotBeFound("website.doesNotContain=" + DEFAULT_WEBSITE);

        // Get all the schoolList where website does not contain UPDATED_WEBSITE
        defaultSchoolShouldBeFound("website.doesNotContain=" + UPDATED_WEBSITE);
    }


    @Test
    @Transactional
    public void getAllSchoolsByAboutTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where aboutType equals to DEFAULT_ABOUT_TYPE
        defaultSchoolShouldBeFound("aboutType.equals=" + DEFAULT_ABOUT_TYPE);

        // Get all the schoolList where aboutType equals to UPDATED_ABOUT_TYPE
        defaultSchoolShouldNotBeFound("aboutType.equals=" + UPDATED_ABOUT_TYPE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByAboutTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where aboutType not equals to DEFAULT_ABOUT_TYPE
        defaultSchoolShouldNotBeFound("aboutType.notEquals=" + DEFAULT_ABOUT_TYPE);

        // Get all the schoolList where aboutType not equals to UPDATED_ABOUT_TYPE
        defaultSchoolShouldBeFound("aboutType.notEquals=" + UPDATED_ABOUT_TYPE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByAboutTypeIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where aboutType in DEFAULT_ABOUT_TYPE or UPDATED_ABOUT_TYPE
        defaultSchoolShouldBeFound("aboutType.in=" + DEFAULT_ABOUT_TYPE + "," + UPDATED_ABOUT_TYPE);

        // Get all the schoolList where aboutType equals to UPDATED_ABOUT_TYPE
        defaultSchoolShouldNotBeFound("aboutType.in=" + UPDATED_ABOUT_TYPE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByAboutTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where aboutType is not null
        defaultSchoolShouldBeFound("aboutType.specified=true");

        // Get all the schoolList where aboutType is null
        defaultSchoolShouldNotBeFound("aboutType.specified=false");
    }

    @Test
    @Transactional
    public void getAllSchoolsByLocationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where locationType equals to DEFAULT_LOCATION_TYPE
        defaultSchoolShouldBeFound("locationType.equals=" + DEFAULT_LOCATION_TYPE);

        // Get all the schoolList where locationType equals to UPDATED_LOCATION_TYPE
        defaultSchoolShouldNotBeFound("locationType.equals=" + UPDATED_LOCATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByLocationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where locationType not equals to DEFAULT_LOCATION_TYPE
        defaultSchoolShouldNotBeFound("locationType.notEquals=" + DEFAULT_LOCATION_TYPE);

        // Get all the schoolList where locationType not equals to UPDATED_LOCATION_TYPE
        defaultSchoolShouldBeFound("locationType.notEquals=" + UPDATED_LOCATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByLocationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where locationType in DEFAULT_LOCATION_TYPE or UPDATED_LOCATION_TYPE
        defaultSchoolShouldBeFound("locationType.in=" + DEFAULT_LOCATION_TYPE + "," + UPDATED_LOCATION_TYPE);

        // Get all the schoolList where locationType equals to UPDATED_LOCATION_TYPE
        defaultSchoolShouldNotBeFound("locationType.in=" + UPDATED_LOCATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllSchoolsByLocationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList where locationType is not null
        defaultSchoolShouldBeFound("locationType.specified=true");

        // Get all the schoolList where locationType is null
        defaultSchoolShouldNotBeFound("locationType.specified=false");
    }

    @Test
    @Transactional
    public void getAllSchoolsByUserGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        UserGroup userGroup = UserGroupResourceIT.createEntity(em);
        em.persist(userGroup);
        em.flush();
        school.addUserGroup(userGroup);
        schoolRepository.saveAndFlush(school);
        Long userGroupId = userGroup.getId();

        // Get all the schoolList where userGroup equals to userGroupId
        defaultSchoolShouldBeFound("userGroupId.equals=" + userGroupId);

        // Get all the schoolList where userGroup equals to userGroupId + 1
        defaultSchoolShouldNotBeFound("userGroupId.equals=" + (userGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllSchoolsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        school.addUser(user);
        schoolRepository.saveAndFlush(school);
        Long userId = user.getId();

        // Get all the schoolList where user equals to userId
        defaultSchoolShouldBeFound("userId.equals=" + userId);

        // Get all the schoolList where user equals to userId + 1
        defaultSchoolShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSchoolShouldBeFound(String filter) throws Exception {
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].featuredImageContentType").value(hasItem(DEFAULT_FEATURED_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].featuredImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_FEATURED_IMAGE))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].aboutType").value(hasItem(DEFAULT_ABOUT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())));

        // Check, that the count call also returns 1
        restSchoolMockMvc.perform(get("/api/schools/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSchoolShouldNotBeFound(String filter) throws Exception {
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSchoolMockMvc.perform(get("/api/schools/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).get();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool
            .name(UPDATED_NAME)
            .featuredImage(UPDATED_FEATURED_IMAGE)
            .featuredImageContentType(UPDATED_FEATURED_IMAGE_CONTENT_TYPE)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .website(UPDATED_WEBSITE)
            .about(UPDATED_ABOUT)
            .aboutType(UPDATED_ABOUT_TYPE)
            .location(UPDATED_LOCATION)
            .locationType(UPDATED_LOCATION_TYPE);
        SchoolDTO schoolDTO = schoolMapper.toDto(updatedSchool);

        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchool.getFeaturedImage()).isEqualTo(UPDATED_FEATURED_IMAGE);
        assertThat(testSchool.getFeaturedImageContentType()).isEqualTo(UPDATED_FEATURED_IMAGE_CONTENT_TYPE);
        assertThat(testSchool.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSchool.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSchool.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testSchool.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testSchool.getAboutType()).isEqualTo(UPDATED_ABOUT_TYPE);
        assertThat(testSchool.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testSchool.getLocationType()).isEqualTo(UPDATED_LOCATION_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Delete the school
        restSchoolMockMvc.perform(delete("/api/schools/{id}", school.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
