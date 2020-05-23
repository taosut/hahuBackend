package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class ImageMetaDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageMetaDataDTO.class);
        ImageMetaDataDTO imageMetaDataDTO1 = new ImageMetaDataDTO();
        imageMetaDataDTO1.setId(1L);
        ImageMetaDataDTO imageMetaDataDTO2 = new ImageMetaDataDTO();
        assertThat(imageMetaDataDTO1).isNotEqualTo(imageMetaDataDTO2);
        imageMetaDataDTO2.setId(imageMetaDataDTO1.getId());
        assertThat(imageMetaDataDTO1).isEqualTo(imageMetaDataDTO2);
        imageMetaDataDTO2.setId(2L);
        assertThat(imageMetaDataDTO1).isNotEqualTo(imageMetaDataDTO2);
        imageMetaDataDTO1.setId(null);
        assertThat(imageMetaDataDTO1).isNotEqualTo(imageMetaDataDTO2);
    }
}
