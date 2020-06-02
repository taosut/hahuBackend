package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Profile;
import et.com.hahu.domain.User;
import et.com.hahu.repository.ProfileRepository;
import et.com.hahu.service.ProfileService;
import et.com.hahu.service.dto.ProfileDTO;
import et.com.hahu.service.mapper.ProfileMapper;
import et.com.hahu.service.dto.ProfileCriteria;
import et.com.hahu.service.ProfileQueryService;

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
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProfileResourceIT {

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final String DEFAULT_PHONE = "+775733863805";
    private static final String UPDATED_PHONE = "+376852504341";

    private static final byte[] DEFAULT_CURENT_PROFILE_PIC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CURENT_PROFILE_PIC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CURENT_PROFILE_PIC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CURENT_PROFILE_PIC_CONTENT_TYPE = "image/png";

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileQueryService profileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfileMockMvc;

    private Profile profile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
            .age(DEFAULT_AGE)
            .phone(DEFAULT_PHONE)
            .curentProfilePic(DEFAULT_CURENT_PROFILE_PIC)
            .curentProfilePicContentType(DEFAULT_CURENT_PROFILE_PIC_CONTENT_TYPE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        profile.setUser(user);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity(EntityManager em) {
        Profile profile = new Profile()
            .age(UPDATED_AGE)
            .phone(UPDATED_PHONE)
            .curentProfilePic(UPDATED_CURENT_PROFILE_PIC)
            .curentProfilePicContentType(UPDATED_CURENT_PROFILE_PIC_CONTENT_TYPE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        profile.setUser(user);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();
        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testProfile.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testProfile.getCurentProfilePic()).isEqualTo(DEFAULT_CURENT_PROFILE_PIC);
        assertThat(testProfile.getCurentProfilePicContentType()).isEqualTo(DEFAULT_CURENT_PROFILE_PIC_CONTENT_TYPE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testProfile.getId()).isEqualTo(testProfile.getUser().getId());
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateProfileMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);

        // Update the User with new association value
        updatedProfile.setUser(user);
        ProfileDTO updatedProfileDTO = profileMapper.toDto(updatedProfile);

        // Update the entity
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
        Profile testProfile = profileList.get(profileList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testProfile.getId()).isEqualTo(testProfile.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].curentProfilePicContentType").value(hasItem(DEFAULT_CURENT_PROFILE_PIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].curentProfilePic").value(hasItem(Base64Utils.encodeToString(DEFAULT_CURENT_PROFILE_PIC))));
    }
    
    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.curentProfilePicContentType").value(DEFAULT_CURENT_PROFILE_PIC_CONTENT_TYPE))
            .andExpect(jsonPath("$.curentProfilePic").value(Base64Utils.encodeToString(DEFAULT_CURENT_PROFILE_PIC)));
    }


    @Test
    @Transactional
    public void getProfilesByIdFiltering() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        Long id = profile.getId();

        defaultProfileShouldBeFound("id.equals=" + id);
        defaultProfileShouldNotBeFound("id.notEquals=" + id);

        defaultProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProfileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProfilesByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where age equals to DEFAULT_AGE
        defaultProfileShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the profileList where age equals to UPDATED_AGE
        defaultProfileShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllProfilesByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where age not equals to DEFAULT_AGE
        defaultProfileShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the profileList where age not equals to UPDATED_AGE
        defaultProfileShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllProfilesByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where age in DEFAULT_AGE or UPDATED_AGE
        defaultProfileShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the profileList where age equals to UPDATED_AGE
        defaultProfileShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllProfilesByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where age is not null
        defaultProfileShouldBeFound("age.specified=true");

        // Get all the profileList where age is null
        defaultProfileShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    public void getAllProfilesByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where age is greater than or equal to DEFAULT_AGE
        defaultProfileShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the profileList where age is greater than or equal to UPDATED_AGE
        defaultProfileShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllProfilesByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where age is less than or equal to DEFAULT_AGE
        defaultProfileShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the profileList where age is less than or equal to SMALLER_AGE
        defaultProfileShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    public void getAllProfilesByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where age is less than DEFAULT_AGE
        defaultProfileShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the profileList where age is less than UPDATED_AGE
        defaultProfileShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllProfilesByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where age is greater than DEFAULT_AGE
        defaultProfileShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the profileList where age is greater than SMALLER_AGE
        defaultProfileShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }


    @Test
    @Transactional
    public void getAllProfilesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone equals to DEFAULT_PHONE
        defaultProfileShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the profileList where phone equals to UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone not equals to DEFAULT_PHONE
        defaultProfileShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the profileList where phone not equals to UPDATED_PHONE
        defaultProfileShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultProfileShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the profileList where phone equals to UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone is not null
        defaultProfileShouldBeFound("phone.specified=true");

        // Get all the profileList where phone is null
        defaultProfileShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllProfilesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone contains DEFAULT_PHONE
        defaultProfileShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the profileList where phone contains UPDATED_PHONE
        defaultProfileShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllProfilesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profileList where phone does not contain DEFAULT_PHONE
        defaultProfileShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the profileList where phone does not contain UPDATED_PHONE
        defaultProfileShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllProfilesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = profile.getUser();
        profileRepository.saveAndFlush(profile);
        Long userId = user.getId();

        // Get all the profileList where user equals to userId
        defaultProfileShouldBeFound("userId.equals=" + userId);

        // Get all the profileList where user equals to userId + 1
        defaultProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProfileShouldBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].curentProfilePicContentType").value(hasItem(DEFAULT_CURENT_PROFILE_PIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].curentProfilePic").value(hasItem(Base64Utils.encodeToString(DEFAULT_CURENT_PROFILE_PIC))));

        // Check, that the count call also returns 1
        restProfileMockMvc.perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProfileShouldNotBeFound(String filter) throws Exception {
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProfileMockMvc.perform(get("/api/profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        // Disconnect from session so that the updates on updatedProfile are not directly saved in db
        em.detach(updatedProfile);
        updatedProfile
            .age(UPDATED_AGE)
            .phone(UPDATED_PHONE)
            .curentProfilePic(UPDATED_CURENT_PROFILE_PIC)
            .curentProfilePicContentType(UPDATED_CURENT_PROFILE_PIC_CONTENT_TYPE);
        ProfileDTO profileDTO = profileMapper.toDto(updatedProfile);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testProfile.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testProfile.getCurentProfilePic()).isEqualTo(UPDATED_CURENT_PROFILE_PIC);
        assertThat(testProfile.getCurentProfilePicContentType()).isEqualTo(UPDATED_CURENT_PROFILE_PIC_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile
        ProfileDTO profileDTO = profileMapper.toDto(profile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
