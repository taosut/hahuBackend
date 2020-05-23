package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SchoolMapperTest {

    private SchoolMapper schoolMapper;

    @BeforeEach
    public void setUp() {
        schoolMapper = new SchoolMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(schoolMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(schoolMapper.fromId(null)).isNull();
    }
}
