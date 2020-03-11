package et.com.hahu.web.rest;

import et.com.hahu.HahuApp;
import et.com.hahu.domain.Setting;
import et.com.hahu.domain.User;
import et.com.hahu.repository.SettingRepository;
import et.com.hahu.service.SettingService;
import et.com.hahu.service.dto.SettingDTO;
import et.com.hahu.service.mapper.SettingMapper;
import et.com.hahu.service.dto.SettingCriteria;
import et.com.hahu.service.SettingQueryService;

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
 * Integration tests for the {@link SettingResource} REST controller.
 */
@SpringBootTest(classes = HahuApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SettingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private SettingService settingService;

    @Autowired
    private SettingQueryService settingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSettingMockMvc;

    private Setting setting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createEntity(EntityManager em) {
        Setting setting = new Setting()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        setting.setUser(user);
        return setting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setting createUpdatedEntity(EntityManager em) {
        Setting setting = new Setting()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        setting.setUser(user);
        return setting;
    }

    @BeforeEach
    public void initTest() {
        setting = createEntity(em);
    }

    @Test
    @Transactional
    public void createSetting() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);
        restSettingMockMvc.perform(post("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isCreated());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate + 1);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSetting.getValue()).isEqualTo(DEFAULT_VALUE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testSetting.getId()).isEqualTo(testSetting.getUser().getId());
    }

    @Test
    @Transactional
    public void createSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Create the Setting with an existing ID
        setting.setId(1L);
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingMockMvc.perform(post("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateSettingMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);
        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // Add a new parent entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();

        // Load the setting
        Setting updatedSetting = settingRepository.findById(setting.getId()).get();
        // Disconnect from session so that the updates on updatedSetting are not directly saved in db
        em.detach(updatedSetting);

        // Update the User with new association value
        updatedSetting.setUser(user);
        SettingDTO updatedSettingDTO = settingMapper.toDto(updatedSetting);

        // Update the entity
        restSettingMockMvc.perform(put("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSettingDTO)))
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate);
        Setting testSetting = settingList.get(settingList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testSetting.getId()).isEqualTo(testSetting.getUser().getId());
    }

    @Test
    @Transactional
    public void getAllSettings() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList
        restSettingMockMvc.perform(get("/api/settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setting.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", setting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(setting.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getSettingsByIdFiltering() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        Long id = setting.getId();

        defaultSettingShouldBeFound("id.equals=" + id);
        defaultSettingShouldNotBeFound("id.notEquals=" + id);

        defaultSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSettingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSettingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where name equals to DEFAULT_NAME
        defaultSettingShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the settingList where name equals to UPDATED_NAME
        defaultSettingShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSettingsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where name not equals to DEFAULT_NAME
        defaultSettingShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the settingList where name not equals to UPDATED_NAME
        defaultSettingShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSettingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSettingShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the settingList where name equals to UPDATED_NAME
        defaultSettingShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSettingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where name is not null
        defaultSettingShouldBeFound("name.specified=true");

        // Get all the settingList where name is null
        defaultSettingShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllSettingsByNameContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where name contains DEFAULT_NAME
        defaultSettingShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the settingList where name contains UPDATED_NAME
        defaultSettingShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSettingsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where name does not contain DEFAULT_NAME
        defaultSettingShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the settingList where name does not contain UPDATED_NAME
        defaultSettingShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllSettingsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where value equals to DEFAULT_VALUE
        defaultSettingShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the settingList where value equals to UPDATED_VALUE
        defaultSettingShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllSettingsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where value not equals to DEFAULT_VALUE
        defaultSettingShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the settingList where value not equals to UPDATED_VALUE
        defaultSettingShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllSettingsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultSettingShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the settingList where value equals to UPDATED_VALUE
        defaultSettingShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllSettingsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where value is not null
        defaultSettingShouldBeFound("value.specified=true");

        // Get all the settingList where value is null
        defaultSettingShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllSettingsByValueContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where value contains DEFAULT_VALUE
        defaultSettingShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the settingList where value contains UPDATED_VALUE
        defaultSettingShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllSettingsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        // Get all the settingList where value does not contain DEFAULT_VALUE
        defaultSettingShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the settingList where value does not contain UPDATED_VALUE
        defaultSettingShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllSettingsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = setting.getUser();
        settingRepository.saveAndFlush(setting);
        Long userId = user.getId();

        // Get all the settingList where user equals to userId
        defaultSettingShouldBeFound("userId.equals=" + userId);

        // Get all the settingList where user equals to userId + 1
        defaultSettingShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSettingShouldBeFound(String filter) throws Exception {
        restSettingMockMvc.perform(get("/api/settings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(setting.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restSettingMockMvc.perform(get("/api/settings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSettingShouldNotBeFound(String filter) throws Exception {
        restSettingMockMvc.perform(get("/api/settings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSettingMockMvc.perform(get("/api/settings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSetting() throws Exception {
        // Get the setting
        restSettingMockMvc.perform(get("/api/settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting
        Setting updatedSetting = settingRepository.findById(setting.getId()).get();
        // Disconnect from session so that the updates on updatedSetting are not directly saved in db
        em.detach(updatedSetting);
        updatedSetting
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);
        SettingDTO settingDTO = settingMapper.toDto(updatedSetting);

        restSettingMockMvc.perform(put("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        Setting testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSetting.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(setting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc.perform(put("/api/settings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(setting);

        int databaseSizeBeforeDelete = settingRepository.findAll().size();

        // Delete the setting
        restSettingMockMvc.perform(delete("/api/settings/{id}", setting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Setting> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
