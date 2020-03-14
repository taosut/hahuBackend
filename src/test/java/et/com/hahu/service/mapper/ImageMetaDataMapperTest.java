package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ImageMetaDataMapperTest {

    private ImageMetaDataMapper imageMetaDataMapper;

    @BeforeEach
    public void setUp() {
        imageMetaDataMapper = new ImageMetaDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(imageMetaDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(imageMetaDataMapper.fromId(null)).isNull();
    }
}
