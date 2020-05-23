package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.UserGroup;
import et.com.hahu.domain.Notification;
import et.com.hahu.domain.Schedule;
import et.com.hahu.domain.User;
import et.com.hahu.domain.School;
import et.com.hahu.repository.UserGroupRepository;
import et.com.hahu.service.UserGroupService;
import et.com.hahu.service.dto.UserGroupDTO;
import et.com.hahu.service.mapper.UserGroupMapper;
import et.com.hahu.service.dto.UserGroupCriteria;
import et.com.hahu.service.UserGroupQueryService;

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

import et.com.hahu.domain.enumeration.GroupType;
/**
 * Integration tests for the {@link UserGroupResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PROFILE_PIC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE_PIC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PROFILE_PIC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_PIC_CONTENT_TYPE = "image/png";

    private static final GroupType DEFAULT_GROUP_TYPE = GroupType.PUBLIC;
    private static final GroupType UPDATED_GROUP_TYPE = GroupType.PRIVATE;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Mock
    private UserGroupRepository userGroupRepositoryMock;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Mock
    private UserGroupService userGroupServiceMock;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserGroupQueryService userGroupQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGroupMockMvc;

    private UserGroup userGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroup createEntity(EntityManager em) {
        UserGroup userGroup = new UserGroup()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .profilePic(DEFAULT_PROFILE_PIC)
            .profilePicContentType(DEFAULT_PROFILE_PIC_CONTENT_TYPE)
            .groupType(DEFAULT_GROUP_TYPE);
        return userGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroup createUpdatedEntity(EntityManager em) {
        UserGroup userGroup = new UserGroup()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .profilePic(UPDATED_PROFILE_PIC)
            .profilePicContentType(UPDATED_PROFILE_PIC_CONTENT_TYPE)
            .groupType(UPDATED_GROUP_TYPE);
        return userGroup;
    }

    @BeforeEach
    public void initTest() {
        userGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserGroup() throws Exception {
        int databaseSizeBeforeCreate = userGroupRepository.findAll().size();

        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);
        restUserGroupMockMvc.perform(post("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeCreate + 1);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserGroup.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testUserGroup.getProfilePic()).isEqualTo(DEFAULT_PROFILE_PIC);
        assertThat(testUserGroup.getProfilePicContentType()).isEqualTo(DEFAULT_PROFILE_PIC_CONTENT_TYPE);
        assertThat(testUserGroup.getGroupType()).isEqualTo(DEFAULT_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void createUserGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGroupRepository.findAll().size();

        // Create the UserGroup with an existing ID
        userGroup.setId(1L);
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupMockMvc.perform(post("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserGroups() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList
        restUserGroupMockMvc.perform(get("/api/user-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].profilePicContentType").value(hasItem(DEFAULT_PROFILE_PIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profilePic").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PIC))))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserGroupsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserGroupMockMvc.perform(get("/api/user-groups?eagerload=true"))
            .andExpect(status().isOk());

        verify(userGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserGroupsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userGroupServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserGroupMockMvc.perform(get("/api/user-groups?eagerload=true"))
            .andExpect(status().isOk());

        verify(userGroupServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserGroup() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get the userGroup
        restUserGroupMockMvc.perform(get("/api/user-groups/{id}", userGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.profilePicContentType").value(DEFAULT_PROFILE_PIC_CONTENT_TYPE))
            .andExpect(jsonPath("$.profilePic").value(Base64Utils.encodeToString(DEFAULT_PROFILE_PIC)))
            .andExpect(jsonPath("$.groupType").value(DEFAULT_GROUP_TYPE.toString()));
    }


    @Test
    @Transactional
    public void getUserGroupsByIdFiltering() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        Long id = userGroup.getId();

        defaultUserGroupShouldBeFound("id.equals=" + id);
        defaultUserGroupShouldNotBeFound("id.notEquals=" + id);

        defaultUserGroupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserGroupShouldNotBeFound("id.greaterThan=" + id);

        defaultUserGroupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserGroupShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserGroupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where name equals to DEFAULT_NAME
        defaultUserGroupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the userGroupList where name equals to UPDATED_NAME
        defaultUserGroupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where name not equals to DEFAULT_NAME
        defaultUserGroupShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the userGroupList where name not equals to UPDATED_NAME
        defaultUserGroupShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUserGroupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the userGroupList where name equals to UPDATED_NAME
        defaultUserGroupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where name is not null
        defaultUserGroupShouldBeFound("name.specified=true");

        // Get all the userGroupList where name is null
        defaultUserGroupShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserGroupsByNameContainsSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where name contains DEFAULT_NAME
        defaultUserGroupShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the userGroupList where name contains UPDATED_NAME
        defaultUserGroupShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where name does not contain DEFAULT_NAME
        defaultUserGroupShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the userGroupList where name does not contain UPDATED_NAME
        defaultUserGroupShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUserGroupsByGroupTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupType equals to DEFAULT_GROUP_TYPE
        defaultUserGroupShouldBeFound("groupType.equals=" + DEFAULT_GROUP_TYPE);

        // Get all the userGroupList where groupType equals to UPDATED_GROUP_TYPE
        defaultUserGroupShouldNotBeFound("groupType.equals=" + UPDATED_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByGroupTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupType not equals to DEFAULT_GROUP_TYPE
        defaultUserGroupShouldNotBeFound("groupType.notEquals=" + DEFAULT_GROUP_TYPE);

        // Get all the userGroupList where groupType not equals to UPDATED_GROUP_TYPE
        defaultUserGroupShouldBeFound("groupType.notEquals=" + UPDATED_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByGroupTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupType in DEFAULT_GROUP_TYPE or UPDATED_GROUP_TYPE
        defaultUserGroupShouldBeFound("groupType.in=" + DEFAULT_GROUP_TYPE + "," + UPDATED_GROUP_TYPE);

        // Get all the userGroupList where groupType equals to UPDATED_GROUP_TYPE
        defaultUserGroupShouldNotBeFound("groupType.in=" + UPDATED_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByGroupTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupType is not null
        defaultUserGroupShouldBeFound("groupType.specified=true");

        // Get all the userGroupList where groupType is null
        defaultUserGroupShouldNotBeFound("groupType.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserGroupsByNotificationIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);
        Notification notification = NotificationResourceIT.createEntity(em);
        em.persist(notification);
        em.flush();
        userGroup.addNotification(notification);
        userGroupRepository.saveAndFlush(userGroup);
        Long notificationId = notification.getId();

        // Get all the userGroupList where notification equals to notificationId
        defaultUserGroupShouldBeFound("notificationId.equals=" + notificationId);

        // Get all the userGroupList where notification equals to notificationId + 1
        defaultUserGroupShouldNotBeFound("notificationId.equals=" + (notificationId + 1));
    }


    @Test
    @Transactional
    public void getAllUserGroupsByScheduleIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);
        Schedule schedule = ScheduleResourceIT.createEntity(em);
        em.persist(schedule);
        em.flush();
        userGroup.addSchedule(schedule);
        userGroupRepository.saveAndFlush(userGroup);
        Long scheduleId = schedule.getId();

        // Get all the userGroupList where schedule equals to scheduleId
        defaultUserGroupShouldBeFound("scheduleId.equals=" + scheduleId);

        // Get all the userGroupList where schedule equals to scheduleId + 1
        defaultUserGroupShouldNotBeFound("scheduleId.equals=" + (scheduleId + 1));
    }


    @Test
    @Transactional
    public void getAllUserGroupsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userGroup.addUser(user);
        userGroupRepository.saveAndFlush(userGroup);
        Long userId = user.getId();

        // Get all the userGroupList where user equals to userId
        defaultUserGroupShouldBeFound("userId.equals=" + userId);

        // Get all the userGroupList where user equals to userId + 1
        defaultUserGroupShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllUserGroupsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);
        User owner = UserResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        userGroup.addOwner(owner);
        userGroupRepository.saveAndFlush(userGroup);
        Long ownerId = owner.getId();

        // Get all the userGroupList where owner equals to ownerId
        defaultUserGroupShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the userGroupList where owner equals to ownerId + 1
        defaultUserGroupShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllUserGroupsBySchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);
        School school = SchoolResourceIT.createEntity(em);
        em.persist(school);
        em.flush();
        userGroup.setSchool(school);
        userGroupRepository.saveAndFlush(userGroup);
        Long schoolId = school.getId();

        // Get all the userGroupList where school equals to schoolId
        defaultUserGroupShouldBeFound("schoolId.equals=" + schoolId);

        // Get all the userGroupList where school equals to schoolId + 1
        defaultUserGroupShouldNotBeFound("schoolId.equals=" + (schoolId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserGroupShouldBeFound(String filter) throws Exception {
        restUserGroupMockMvc.perform(get("/api/user-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].profilePicContentType").value(hasItem(DEFAULT_PROFILE_PIC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profilePic").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PIC))))
            .andExpect(jsonPath("$.[*].groupType").value(hasItem(DEFAULT_GROUP_TYPE.toString())));

        // Check, that the count call also returns 1
        restUserGroupMockMvc.perform(get("/api/user-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserGroupShouldNotBeFound(String filter) throws Exception {
        restUserGroupMockMvc.perform(get("/api/user-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserGroupMockMvc.perform(get("/api/user-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserGroup() throws Exception {
        // Get the userGroup
        restUserGroupMockMvc.perform(get("/api/user-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroup() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();

        // Update the userGroup
        UserGroup updatedUserGroup = userGroupRepository.findById(userGroup.getId()).get();
        // Disconnect from session so that the updates on updatedUserGroup are not directly saved in db
        em.detach(updatedUserGroup);
        updatedUserGroup
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .profilePic(UPDATED_PROFILE_PIC)
            .profilePicContentType(UPDATED_PROFILE_PIC_CONTENT_TYPE)
            .groupType(UPDATED_GROUP_TYPE);
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(updatedUserGroup);

        restUserGroupMockMvc.perform(put("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isOk());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserGroup.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testUserGroup.getProfilePic()).isEqualTo(UPDATED_PROFILE_PIC);
        assertThat(testUserGroup.getProfilePicContentType()).isEqualTo(UPDATED_PROFILE_PIC_CONTENT_TYPE);
        assertThat(testUserGroup.getGroupType()).isEqualTo(UPDATED_GROUP_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserGroup() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRepository.findAll().size();

        // Create the UserGroup
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(userGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupMockMvc.perform(put("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserGroup() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        int databaseSizeBeforeDelete = userGroupRepository.findAll().size();

        // Delete the userGroup
        restUserGroupMockMvc.perform(delete("/api/user-groups/{id}", userGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
