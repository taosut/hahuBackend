package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class PostMetaDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostMetaData.class);
        PostMetaData postMetaData1 = new PostMetaData();
        postMetaData1.setId(1L);
        PostMetaData postMetaData2 = new PostMetaData();
        postMetaData2.setId(postMetaData1.getId());
        assertThat(postMetaData1).isEqualTo(postMetaData2);
        postMetaData2.setId(2L);
        assertThat(postMetaData1).isNotEqualTo(postMetaData2);
        postMetaData1.setId(null);
        assertThat(postMetaData1).isNotEqualTo(postMetaData2);
    }
}
