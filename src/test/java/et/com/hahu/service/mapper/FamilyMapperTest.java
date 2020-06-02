package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FamilyMapperTest {

    private FamilyMapper familyMapper;

    @BeforeEach
    public void setUp() {
        familyMapper = new FamilyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(familyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(familyMapper.fromId(null)).isNull();
    }
}
