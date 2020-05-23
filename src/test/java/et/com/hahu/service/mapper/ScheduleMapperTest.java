package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleMapperTest {

    private ScheduleMapper scheduleMapper;

    @BeforeEach
    public void setUp() {
        scheduleMapper = new ScheduleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scheduleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scheduleMapper.fromId(null)).isNull();
    }
}
