package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class SharesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SharesDTO.class);
        SharesDTO sharesDTO1 = new SharesDTO();
        sharesDTO1.setId(1L);
        SharesDTO sharesDTO2 = new SharesDTO();
        assertThat(sharesDTO1).isNotEqualTo(sharesDTO2);
        sharesDTO2.setId(sharesDTO1.getId());
        assertThat(sharesDTO1).isEqualTo(sharesDTO2);
        sharesDTO2.setId(2L);
        assertThat(sharesDTO1).isNotEqualTo(sharesDTO2);
        sharesDTO1.setId(null);
        assertThat(sharesDTO1).isNotEqualTo(sharesDTO2);
    }
}
