package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LikesMapperTest {

    private LikesMapper likesMapper;

    @BeforeEach
    public void setUp() {
        likesMapper = new LikesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(likesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(likesMapper.fromId(null)).isNull();
    }
}
