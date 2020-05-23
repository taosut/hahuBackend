package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class SchoolProgressDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolProgressDTO.class);
        SchoolProgressDTO schoolProgressDTO1 = new SchoolProgressDTO();
        schoolProgressDTO1.setId(1L);
        SchoolProgressDTO schoolProgressDTO2 = new SchoolProgressDTO();
        assertThat(schoolProgressDTO1).isNotEqualTo(schoolProgressDTO2);
        schoolProgressDTO2.setId(schoolProgressDTO1.getId());
        assertThat(schoolProgressDTO1).isEqualTo(schoolProgressDTO2);
        schoolProgressDTO2.setId(2L);
        assertThat(schoolProgressDTO1).isNotEqualTo(schoolProgressDTO2);
        schoolProgressDTO1.setId(null);
        assertThat(schoolProgressDTO1).isNotEqualTo(schoolProgressDTO2);
    }
}
