package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class FamilyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FamilyDTO.class);
        FamilyDTO familyDTO1 = new FamilyDTO();
        familyDTO1.setId(1L);
        FamilyDTO familyDTO2 = new FamilyDTO();
        assertThat(familyDTO1).isNotEqualTo(familyDTO2);
        familyDTO2.setId(familyDTO1.getId());
        assertThat(familyDTO1).isEqualTo(familyDTO2);
        familyDTO2.setId(2L);
        assertThat(familyDTO1).isNotEqualTo(familyDTO2);
        familyDTO1.setId(null);
        assertThat(familyDTO1).isNotEqualTo(familyDTO2);
    }
}
