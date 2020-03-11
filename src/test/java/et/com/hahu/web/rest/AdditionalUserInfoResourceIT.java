package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.AdditionalUserInfo;
import et.com.hahu.domain.User;
import et.com.hahu.domain.UsersConnection;
import et.com.hahu.domain.UserGroup;
import et.com.hahu.repository.AdditionalUserInfoRepository;
import et.com.hahu.service.AdditionalUserInfoService;
import et.com.hahu.service.dto.AdditionalUserInfoDTO;
import et.com.hahu.service.mapper.AdditionalUserInfoMapper;
import et.com.hahu.service.dto.AdditionalUserInfoCriteria;
import et.com.hahu.service.AdditionalUserInfoQueryService;

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
 * Integration tests for the {@link AdditionalUserInfoResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdditionalUserInfoResourceIT {

    private static final String DEFAULT_PHONE = "+997970237155";
    private static final String UPDATED_PHONE = "+918016103523";

    private static final String DEFAULT_PROFILE_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PICTURE = "BBBBBBBBBB";

    @Autowired
    private AdditionalUserInfoRepository additionalUserInfoRepository;

    @Autowired
    private AdditionalUserInfoMapper additionalUserInfoMapper;

    @Autowired
    private AdditionalUserInfoService additionalUserInfoService;

    @Autowired
    private AdditionalUserInfoQueryService additionalUserInfoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdditionalUserInfoMockMvc;

    private AdditionalUserInfo additionalUserInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalUserInfo createEntity(EntityManager em) {
        AdditionalUserInfo additionalUserInfo = new AdditionalUserInfo()
            .phone(DEFAULT_PHONE)
            .profilePicture(DEFAULT_PROFILE_PICTURE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        additionalUserInfo.setUser(user);
        return additionalUserInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdditionalUserInfo createUpdatedEntity(EntityManager em) {
        AdditionalUserInfo additionalUserInfo = new AdditionalUserInfo()
            .phone(UPDATED_PHONE)
            .profilePicture(UPDATED_PROFILE_PICTURE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        additionalUserInfo.setUser(user);
        return additionalUserInfo;
    }

    @BeforeEach
    public void initTest() {
        additionalUserInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdditionalUserInfo() throws Exception {
        int databaseSizeBeforeCreate = additionalUserInfoRepository.findAll().size();

        // Create the AdditionalUserInfo
        AdditionalUserInfoDTO additionalUserInfoDTO = additionalUserInfoMapper.toDto(additionalUserInfo);
        restAdditionalUserInfoMockMvc.perform(post("/api/additional-user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalUserInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the AdditionalUserInfo in the database
        List<AdditionalUserInfo> additionalUserInfoList = additionalUserInfoRepository.findAll();
        assertThat(additionalUserInfoList).hasSize(databaseSizeBeforeCreate + 1);
        AdditionalUserInfo testAdditionalUserInfo = additionalUserInfoList.get(additionalUserInfoList.size() - 1);
        assertThat(testAdditionalUserInfo.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testAdditionalUserInfo.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testAdditionalUserInfo.getId()).isEqualTo(testAdditionalUserInfo.getUser().getId());
    }

    @Test
    @Transactional
    public void createAdditionalUserInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = additionalUserInfoRepository.findAll().size();

        // Create the AdditionalUserInfo with an existing ID
        additionalUserInfo.setId(1L);
        AdditionalUserInfoDTO additionalUserInfoDTO = additionalUserInfoMapper.toDto(additionalUserInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdditionalUserInfoMockMvc.perform(post("/api/additional-user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalUserInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalUserInfo in the database
        List<AdditionalUserInfo> additionalUserInfoList = additionalUserInfoRepository.findAll();
        assertThat(additionalUserInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateAdditionalUserInfoMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);
        int databaseSizeBeforeCreate = additionalUserInfoRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the additionalUserInfo
        AdditionalUserInfo updatedAdditionalUserInfo = additionalUserInfoRepository.findById(additionalUserInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAdditionalUserInfo are not directly saved in db
        em.detach(updatedAdditionalUserInfo);

        // Update the User with new association value
        updatedAdditionalUserInfo.setUser(user);
        AdditionalUserInfoDTO updatedAdditionalUserInfoDTO = additionalUserInfoMapper.toDto(updatedAdditionalUserInfo);

        // Update the entity
        restAdditionalUserInfoMockMvc.perform(put("/api/additional-user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdditionalUserInfoDTO)))
            .andExpect(status().isOk());

        // Validate the AdditionalUserInfo in the database
        List<AdditionalUserInfo> additionalUserInfoList = additionalUserInfoRepository.findAll();
        assertThat(additionalUserInfoList).hasSize(databaseSizeBeforeCreate);
        AdditionalUserInfo testAdditionalUserInfo = additionalUserInfoList.get(additionalUserInfoList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testAdditionalUserInfo.getId()).isEqualTo(testAdditionalUserInfo.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfos() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList
        restAdditionalUserInfoMockMvc.perform(get("/api/additional-user-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalUserInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE)));
    }
    
    @Test
    @Transactional
    public void getAdditionalUserInfo() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get the additionalUserInfo
        restAdditionalUserInfoMockMvc.perform(get("/api/additional-user-infos/{id}", additionalUserInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(additionalUserInfo.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.profilePicture").value(DEFAULT_PROFILE_PICTURE));
    }


    @Test
    @Transactional
    public void getAdditionalUserInfosByIdFiltering() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        Long id = additionalUserInfo.getId();

        defaultAdditionalUserInfoShouldBeFound("id.equals=" + id);
        defaultAdditionalUserInfoShouldNotBeFound("id.notEquals=" + id);

        defaultAdditionalUserInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdditionalUserInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultAdditionalUserInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdditionalUserInfoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdditionalUserInfosByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where phone equals to DEFAULT_PHONE
        defaultAdditionalUserInfoShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the additionalUserInfoList where phone equals to UPDATED_PHONE
        defaultAdditionalUserInfoShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfosByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where phone not equals to DEFAULT_PHONE
        defaultAdditionalUserInfoShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the additionalUserInfoList where phone not equals to UPDATED_PHONE
        defaultAdditionalUserInfoShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfosByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultAdditionalUserInfoShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the additionalUserInfoList where phone equals to UPDATED_PHONE
        defaultAdditionalUserInfoShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfosByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where phone is not null
        defaultAdditionalUserInfoShouldBeFound("phone.specified=true");

        // Get all the additionalUserInfoList where phone is null
        defaultAdditionalUserInfoShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdditionalUserInfosByPhoneContainsSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where phone contains DEFAULT_PHONE
        defaultAdditionalUserInfoShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the additionalUserInfoList where phone contains UPDATED_PHONE
        defaultAdditionalUserInfoShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfosByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where phone does not contain DEFAULT_PHONE
        defaultAdditionalUserInfoShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the additionalUserInfoList where phone does not contain UPDATED_PHONE
        defaultAdditionalUserInfoShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllAdditionalUserInfosByProfilePictureIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where profilePicture equals to DEFAULT_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldBeFound("profilePicture.equals=" + DEFAULT_PROFILE_PICTURE);

        // Get all the additionalUserInfoList where profilePicture equals to UPDATED_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldNotBeFound("profilePicture.equals=" + UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfosByProfilePictureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where profilePicture not equals to DEFAULT_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldNotBeFound("profilePicture.notEquals=" + DEFAULT_PROFILE_PICTURE);

        // Get all the additionalUserInfoList where profilePicture not equals to UPDATED_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldBeFound("profilePicture.notEquals=" + UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfosByProfilePictureIsInShouldWork() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where profilePicture in DEFAULT_PROFILE_PICTURE or UPDATED_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldBeFound("profilePicture.in=" + DEFAULT_PROFILE_PICTURE + "," + UPDATED_PROFILE_PICTURE);

        // Get all the additionalUserInfoList where profilePicture equals to UPDATED_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldNotBeFound("profilePicture.in=" + UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfosByProfilePictureIsNullOrNotNull() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where profilePicture is not null
        defaultAdditionalUserInfoShouldBeFound("profilePicture.specified=true");

        // Get all the additionalUserInfoList where profilePicture is null
        defaultAdditionalUserInfoShouldNotBeFound("profilePicture.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdditionalUserInfosByProfilePictureContainsSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where profilePicture contains DEFAULT_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldBeFound("profilePicture.contains=" + DEFAULT_PROFILE_PICTURE);

        // Get all the additionalUserInfoList where profilePicture contains UPDATED_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldNotBeFound("profilePicture.contains=" + UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void getAllAdditionalUserInfosByProfilePictureNotContainsSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        // Get all the additionalUserInfoList where profilePicture does not contain DEFAULT_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldNotBeFound("profilePicture.doesNotContain=" + DEFAULT_PROFILE_PICTURE);

        // Get all the additionalUserInfoList where profilePicture does not contain UPDATED_PROFILE_PICTURE
        defaultAdditionalUserInfoShouldBeFound("profilePicture.doesNotContain=" + UPDATED_PROFILE_PICTURE);
    }


    @Test
    @Transactional
    public void getAllAdditionalUserInfosByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = additionalUserInfo.getUser();
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);
        Long userId = user.getId();

        // Get all the additionalUserInfoList where user equals to userId
        defaultAdditionalUserInfoShouldBeFound("userId.equals=" + userId);

        // Get all the additionalUserInfoList where user equals to userId + 1
        defaultAdditionalUserInfoShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllAdditionalUserInfosByFollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);
        UsersConnection following = UsersConnectionResourceIT.createEntity(em);
        em.persist(following);
        em.flush();
        additionalUserInfo.addFollowing(following);
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);
        Long followingId = following.getId();

        // Get all the additionalUserInfoList where following equals to followingId
        defaultAdditionalUserInfoShouldBeFound("followingId.equals=" + followingId);

        // Get all the additionalUserInfoList where following equals to followingId + 1
        defaultAdditionalUserInfoShouldNotBeFound("followingId.equals=" + (followingId + 1));
    }


    @Test
    @Transactional
    public void getAllAdditionalUserInfosByFollowerIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);
        UsersConnection follower = UsersConnectionResourceIT.createEntity(em);
        em.persist(follower);
        em.flush();
        additionalUserInfo.addFollower(follower);
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);
        Long followerId = follower.getId();

        // Get all the additionalUserInfoList where follower equals to followerId
        defaultAdditionalUserInfoShouldBeFound("followerId.equals=" + followerId);

        // Get all the additionalUserInfoList where follower equals to followerId + 1
        defaultAdditionalUserInfoShouldNotBeFound("followerId.equals=" + (followerId + 1));
    }


    @Test
    @Transactional
    public void getAllAdditionalUserInfosByUserGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);
        UserGroup userGroup = UserGroupResourceIT.createEntity(em);
        em.persist(userGroup);
        em.flush();
        additionalUserInfo.setUserGroup(userGroup);
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);
        Long userGroupId = userGroup.getId();

        // Get all the additionalUserInfoList where userGroup equals to userGroupId
        defaultAdditionalUserInfoShouldBeFound("userGroupId.equals=" + userGroupId);

        // Get all the additionalUserInfoList where userGroup equals to userGroupId + 1
        defaultAdditionalUserInfoShouldNotBeFound("userGroupId.equals=" + (userGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdditionalUserInfoShouldBeFound(String filter) throws Exception {
        restAdditionalUserInfoMockMvc.perform(get("/api/additional-user-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(additionalUserInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE)));

        // Check, that the count call also returns 1
        restAdditionalUserInfoMockMvc.perform(get("/api/additional-user-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdditionalUserInfoShouldNotBeFound(String filter) throws Exception {
        restAdditionalUserInfoMockMvc.perform(get("/api/additional-user-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdditionalUserInfoMockMvc.perform(get("/api/additional-user-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdditionalUserInfo() throws Exception {
        // Get the additionalUserInfo
        restAdditionalUserInfoMockMvc.perform(get("/api/additional-user-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdditionalUserInfo() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        int databaseSizeBeforeUpdate = additionalUserInfoRepository.findAll().size();

        // Update the additionalUserInfo
        AdditionalUserInfo updatedAdditionalUserInfo = additionalUserInfoRepository.findById(additionalUserInfo.getId()).get();
        // Disconnect from session so that the updates on updatedAdditionalUserInfo are not directly saved in db
        em.detach(updatedAdditionalUserInfo);
        updatedAdditionalUserInfo
            .phone(UPDATED_PHONE)
            .profilePicture(UPDATED_PROFILE_PICTURE);
        AdditionalUserInfoDTO additionalUserInfoDTO = additionalUserInfoMapper.toDto(updatedAdditionalUserInfo);

        restAdditionalUserInfoMockMvc.perform(put("/api/additional-user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalUserInfoDTO)))
            .andExpect(status().isOk());

        // Validate the AdditionalUserInfo in the database
        List<AdditionalUserInfo> additionalUserInfoList = additionalUserInfoRepository.findAll();
        assertThat(additionalUserInfoList).hasSize(databaseSizeBeforeUpdate);
        AdditionalUserInfo testAdditionalUserInfo = additionalUserInfoList.get(additionalUserInfoList.size() - 1);
        assertThat(testAdditionalUserInfo.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testAdditionalUserInfo.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdditionalUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = additionalUserInfoRepository.findAll().size();

        // Create the AdditionalUserInfo
        AdditionalUserInfoDTO additionalUserInfoDTO = additionalUserInfoMapper.toDto(additionalUserInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdditionalUserInfoMockMvc.perform(put("/api/additional-user-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(additionalUserInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdditionalUserInfo in the database
        List<AdditionalUserInfo> additionalUserInfoList = additionalUserInfoRepository.findAll();
        assertThat(additionalUserInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdditionalUserInfo() throws Exception {
        // Initialize the database
        additionalUserInfoRepository.saveAndFlush(additionalUserInfo);

        int databaseSizeBeforeDelete = additionalUserInfoRepository.findAll().size();

        // Delete the additionalUserInfo
        restAdditionalUserInfoMockMvc.perform(delete("/api/additional-user-infos/{id}", additionalUserInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdditionalUserInfo> additionalUserInfoList = additionalUserInfoRepository.findAll();
        assertThat(additionalUserInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
