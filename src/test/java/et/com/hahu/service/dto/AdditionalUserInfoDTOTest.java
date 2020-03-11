package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class AdditionalUserInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalUserInfoDTO.class);
        AdditionalUserInfoDTO additionalUserInfoDTO1 = new AdditionalUserInfoDTO();
        additionalUserInfoDTO1.setId(1L);
        AdditionalUserInfoDTO additionalUserInfoDTO2 = new AdditionalUserInfoDTO();
        assertThat(additionalUserInfoDTO1).isNotEqualTo(additionalUserInfoDTO2);
        additionalUserInfoDTO2.setId(additionalUserInfoDTO1.getId());
        assertThat(additionalUserInfoDTO1).isEqualTo(additionalUserInfoDTO2);
        additionalUserInfoDTO2.setId(2L);
        assertThat(additionalUserInfoDTO1).isNotEqualTo(additionalUserInfoDTO2);
        additionalUserInfoDTO1.setId(null);
        assertThat(additionalUserInfoDTO1).isNotEqualTo(additionalUserInfoDTO2);
    }
}
