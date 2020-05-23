package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SchoolProgressMapperTest {

    private SchoolProgressMapper schoolProgressMapper;

    @BeforeEach
    public void setUp() {
        schoolProgressMapper = new SchoolProgressMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(schoolProgressMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(schoolProgressMapper.fromId(null)).isNull();
    }
}
