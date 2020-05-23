package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleTypeMapperTest {

    private ScheduleTypeMapper scheduleTypeMapper;

    @BeforeEach
    public void setUp() {
        scheduleTypeMapper = new ScheduleTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scheduleTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scheduleTypeMapper.fromId(null)).isNull();
    }
}
