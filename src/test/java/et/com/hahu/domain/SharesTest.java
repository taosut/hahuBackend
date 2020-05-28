package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class SharesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shares.class);
        Shares shares1 = new Shares();
        shares1.setId(1L);
        Shares shares2 = new Shares();
        shares2.setId(shares1.getId());
        assertThat(shares1).isEqualTo(shares2);
        shares2.setId(2L);
        assertThat(shares1).isNotEqualTo(shares2);
        shares1.setId(null);
        assertThat(shares1).isNotEqualTo(shares2);
    }
}
