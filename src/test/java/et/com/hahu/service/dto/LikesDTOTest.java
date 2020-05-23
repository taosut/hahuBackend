package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class LikesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikesDTO.class);
        LikesDTO likesDTO1 = new LikesDTO();
        likesDTO1.setId(1L);
        LikesDTO likesDTO2 = new LikesDTO();
        assertThat(likesDTO1).isNotEqualTo(likesDTO2);
        likesDTO2.setId(likesDTO1.getId());
        assertThat(likesDTO1).isEqualTo(likesDTO2);
        likesDTO2.setId(2L);
        assertThat(likesDTO1).isNotEqualTo(likesDTO2);
        likesDTO1.setId(null);
        assertThat(likesDTO1).isNotEqualTo(likesDTO2);
    }
}
