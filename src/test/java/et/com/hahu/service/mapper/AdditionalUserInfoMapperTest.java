package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdditionalUserInfoMapperTest {

    private AdditionalUserInfoMapper additionalUserInfoMapper;

    @BeforeEach
    public void setUp() {
        additionalUserInfoMapper = new AdditionalUserInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(additionalUserInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(additionalUserInfoMapper.fromId(null)).isNull();
    }
}
