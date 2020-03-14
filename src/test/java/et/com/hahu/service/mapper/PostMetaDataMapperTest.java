package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PostMetaDataMapperTest {

    private PostMetaDataMapper postMetaDataMapper;

    @BeforeEach
    public void setUp() {
        postMetaDataMapper = new PostMetaDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(postMetaDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(postMetaDataMapper.fromId(null)).isNull();
    }
}
