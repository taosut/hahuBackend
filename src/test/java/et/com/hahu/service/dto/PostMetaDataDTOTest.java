package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class PostMetaDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostMetaDataDTO.class);
        PostMetaDataDTO postMetaDataDTO1 = new PostMetaDataDTO();
        postMetaDataDTO1.setId(1L);
        PostMetaDataDTO postMetaDataDTO2 = new PostMetaDataDTO();
        assertThat(postMetaDataDTO1).isNotEqualTo(postMetaDataDTO2);
        postMetaDataDTO2.setId(postMetaDataDTO1.getId());
        assertThat(postMetaDataDTO1).isEqualTo(postMetaDataDTO2);
        postMetaDataDTO2.setId(2L);
        assertThat(postMetaDataDTO1).isNotEqualTo(postMetaDataDTO2);
        postMetaDataDTO1.setId(null);
        assertThat(postMetaDataDTO1).isNotEqualTo(postMetaDataDTO2);
    }
}
