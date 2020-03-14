package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.UserGroup;
import et.com.hahu.domain.User;
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
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserGroupResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserGroupResourceIT {

    private static final String DEFAULT_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

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
            .groupName(DEFAULT_GROUP_NAME)
            .owner(DEFAULT_OWNER);
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
            .groupName(UPDATED_GROUP_NAME)
            .owner(UPDATED_OWNER);
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
        assertThat(testUserGroup.getGroupName()).isEqualTo(DEFAULT_GROUP_NAME);
        assertThat(testUserGroup.getOwner()).isEqualTo(DEFAULT_OWNER);
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
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)));
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
            .andExpect(jsonPath("$.groupName").value(DEFAULT_GROUP_NAME))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER));
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
    public void getAllUserGroupsByGroupNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupName equals to DEFAULT_GROUP_NAME
        defaultUserGroupShouldBeFound("groupName.equals=" + DEFAULT_GROUP_NAME);

        // Get all the userGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultUserGroupShouldNotBeFound("groupName.equals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByGroupNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupName not equals to DEFAULT_GROUP_NAME
        defaultUserGroupShouldNotBeFound("groupName.notEquals=" + DEFAULT_GROUP_NAME);

        // Get all the userGroupList where groupName not equals to UPDATED_GROUP_NAME
        defaultUserGroupShouldBeFound("groupName.notEquals=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByGroupNameIsInShouldWork() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupName in DEFAULT_GROUP_NAME or UPDATED_GROUP_NAME
        defaultUserGroupShouldBeFound("groupName.in=" + DEFAULT_GROUP_NAME + "," + UPDATED_GROUP_NAME);

        // Get all the userGroupList where groupName equals to UPDATED_GROUP_NAME
        defaultUserGroupShouldNotBeFound("groupName.in=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByGroupNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupName is not null
        defaultUserGroupShouldBeFound("groupName.specified=true");

        // Get all the userGroupList where groupName is null
        defaultUserGroupShouldNotBeFound("groupName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserGroupsByGroupNameContainsSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupName contains DEFAULT_GROUP_NAME
        defaultUserGroupShouldBeFound("groupName.contains=" + DEFAULT_GROUP_NAME);

        // Get all the userGroupList where groupName contains UPDATED_GROUP_NAME
        defaultUserGroupShouldNotBeFound("groupName.contains=" + UPDATED_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByGroupNameNotContainsSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where groupName does not contain DEFAULT_GROUP_NAME
        defaultUserGroupShouldNotBeFound("groupName.doesNotContain=" + DEFAULT_GROUP_NAME);

        // Get all the userGroupList where groupName does not contain UPDATED_GROUP_NAME
        defaultUserGroupShouldBeFound("groupName.doesNotContain=" + UPDATED_GROUP_NAME);
    }


    @Test
    @Transactional
    public void getAllUserGroupsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where owner equals to DEFAULT_OWNER
        defaultUserGroupShouldBeFound("owner.equals=" + DEFAULT_OWNER);

        // Get all the userGroupList where owner equals to UPDATED_OWNER
        defaultUserGroupShouldNotBeFound("owner.equals=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByOwnerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where owner not equals to DEFAULT_OWNER
        defaultUserGroupShouldNotBeFound("owner.notEquals=" + DEFAULT_OWNER);

        // Get all the userGroupList where owner not equals to UPDATED_OWNER
        defaultUserGroupShouldBeFound("owner.notEquals=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where owner in DEFAULT_OWNER or UPDATED_OWNER
        defaultUserGroupShouldBeFound("owner.in=" + DEFAULT_OWNER + "," + UPDATED_OWNER);

        // Get all the userGroupList where owner equals to UPDATED_OWNER
        defaultUserGroupShouldNotBeFound("owner.in=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where owner is not null
        defaultUserGroupShouldBeFound("owner.specified=true");

        // Get all the userGroupList where owner is null
        defaultUserGroupShouldNotBeFound("owner.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserGroupsByOwnerContainsSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where owner contains DEFAULT_OWNER
        defaultUserGroupShouldBeFound("owner.contains=" + DEFAULT_OWNER);

        // Get all the userGroupList where owner contains UPDATED_OWNER
        defaultUserGroupShouldNotBeFound("owner.contains=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    public void getAllUserGroupsByOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        userGroupRepository.saveAndFlush(userGroup);

        // Get all the userGroupList where owner does not contain DEFAULT_OWNER
        defaultUserGroupShouldNotBeFound("owner.doesNotContain=" + DEFAULT_OWNER);

        // Get all the userGroupList where owner does not contain UPDATED_OWNER
        defaultUserGroupShouldBeFound("owner.doesNotContain=" + UPDATED_OWNER);
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

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserGroupShouldBeFound(String filter) throws Exception {
        restUserGroupMockMvc.perform(get("/api/user-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupName").value(hasItem(DEFAULT_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)));

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
            .groupName(UPDATED_GROUP_NAME)
            .owner(UPDATED_OWNER);
        UserGroupDTO userGroupDTO = userGroupMapper.toDto(updatedUserGroup);

        restUserGroupMockMvc.perform(put("/api/user-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userGroupDTO)))
            .andExpect(status().isOk());

        // Validate the UserGroup in the database
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        assertThat(userGroupList).hasSize(databaseSizeBeforeUpdate);
        UserGroup testUserGroup = userGroupList.get(userGroupList.size() - 1);
        assertThat(testUserGroup.getGroupName()).isEqualTo(UPDATED_GROUP_NAME);
        assertThat(testUserGroup.getOwner()).isEqualTo(UPDATED_OWNER);
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
