package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class ViewsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Views.class);
        Views views1 = new Views();
        views1.setId(1L);
        Views views2 = new Views();
        views2.setId(views1.getId());
        assertThat(views1).isEqualTo(views2);
        views2.setId(2L);
        assertThat(views1).isNotEqualTo(views2);
        views1.setId(null);
        assertThat(views1).isNotEqualTo(views2);
    }
}
