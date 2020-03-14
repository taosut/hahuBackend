package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class ImageMetaDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageMetaData.class);
        ImageMetaData imageMetaData1 = new ImageMetaData();
        imageMetaData1.setId(1L);
        ImageMetaData imageMetaData2 = new ImageMetaData();
        imageMetaData2.setId(imageMetaData1.getId());
        assertThat(imageMetaData1).isEqualTo(imageMetaData2);
        imageMetaData2.setId(2L);
        assertThat(imageMetaData1).isNotEqualTo(imageMetaData2);
        imageMetaData1.setId(null);
        assertThat(imageMetaData1).isNotEqualTo(imageMetaData2);
    }
}
