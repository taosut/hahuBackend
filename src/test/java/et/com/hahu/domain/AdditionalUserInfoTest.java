package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class AdditionalUserInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalUserInfo.class);
        AdditionalUserInfo additionalUserInfo1 = new AdditionalUserInfo();
        additionalUserInfo1.setId(1L);
        AdditionalUserInfo additionalUserInfo2 = new AdditionalUserInfo();
        additionalUserInfo2.setId(additionalUserInfo1.getId());
        assertThat(additionalUserInfo1).isEqualTo(additionalUserInfo2);
        additionalUserInfo2.setId(2L);
        assertThat(additionalUserInfo1).isNotEqualTo(additionalUserInfo2);
        additionalUserInfo1.setId(null);
        assertThat(additionalUserInfo1).isNotEqualTo(additionalUserInfo2);
    }
}
