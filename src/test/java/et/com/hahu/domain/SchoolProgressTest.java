package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class SchoolProgressTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolProgress.class);
        SchoolProgress schoolProgress1 = new SchoolProgress();
        schoolProgress1.setId(1L);
        SchoolProgress schoolProgress2 = new SchoolProgress();
        schoolProgress2.setId(schoolProgress1.getId());
        assertThat(schoolProgress1).isEqualTo(schoolProgress2);
        schoolProgress2.setId(2L);
        assertThat(schoolProgress1).isNotEqualTo(schoolProgress2);
        schoolProgress1.setId(null);
        assertThat(schoolProgress1).isNotEqualTo(schoolProgress2);
    }
}
