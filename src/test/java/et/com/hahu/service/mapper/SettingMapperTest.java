package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SettingMapperTest {

    private SettingMapper settingMapper;

    @BeforeEach
    public void setUp() {
        settingMapper = new SettingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(settingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(settingMapper.fromId(null)).isNull();
    }
}
