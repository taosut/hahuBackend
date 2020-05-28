package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SharesMapperTest {

    private SharesMapper sharesMapper;

    @BeforeEach
    public void setUp() {
        sharesMapper = new SharesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sharesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sharesMapper.fromId(null)).isNull();
    }
}
