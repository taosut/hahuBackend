package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class ScheduleTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleType.class);
        ScheduleType scheduleType1 = new ScheduleType();
        scheduleType1.setId(1L);
        ScheduleType scheduleType2 = new ScheduleType();
        scheduleType2.setId(scheduleType1.getId());
        assertThat(scheduleType1).isEqualTo(scheduleType2);
        scheduleType2.setId(2L);
        assertThat(scheduleType1).isNotEqualTo(scheduleType2);
        scheduleType1.setId(null);
        assertThat(scheduleType1).isNotEqualTo(scheduleType2);
    }
}
