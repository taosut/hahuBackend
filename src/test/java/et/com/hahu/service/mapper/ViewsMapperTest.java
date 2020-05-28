package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ViewsMapperTest {

    private ViewsMapper viewsMapper;

    @BeforeEach
    public void setUp() {
        viewsMapper = new ViewsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(viewsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(viewsMapper.fromId(null)).isNull();
    }
}
