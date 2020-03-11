package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserGroupMapperTest {

    private UserGroupMapper userGroupMapper;

    @BeforeEach
    public void setUp() {
        userGroupMapper = new UserGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userGroupMapper.fromId(null)).isNull();
    }
}
