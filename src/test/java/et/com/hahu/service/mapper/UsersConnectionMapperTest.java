package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UsersConnectionMapperTest {

    private UsersConnectionMapper usersConnectionMapper;

    @BeforeEach
    public void setUp() {
        usersConnectionMapper = new UsersConnectionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(usersConnectionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(usersConnectionMapper.fromId(null)).isNull();
    }
}
