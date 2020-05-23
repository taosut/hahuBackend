package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class ScheduleTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleTypeDTO.class);
        ScheduleTypeDTO scheduleTypeDTO1 = new ScheduleTypeDTO();
        scheduleTypeDTO1.setId(1L);
        ScheduleTypeDTO scheduleTypeDTO2 = new ScheduleTypeDTO();
        assertThat(scheduleTypeDTO1).isNotEqualTo(scheduleTypeDTO2);
        scheduleTypeDTO2.setId(scheduleTypeDTO1.getId());
        assertThat(scheduleTypeDTO1).isEqualTo(scheduleTypeDTO2);
        scheduleTypeDTO2.setId(2L);
        assertThat(scheduleTypeDTO1).isNotEqualTo(scheduleTypeDTO2);
        scheduleTypeDTO1.setId(null);
        assertThat(scheduleTypeDTO1).isNotEqualTo(scheduleTypeDTO2);
    }
}
