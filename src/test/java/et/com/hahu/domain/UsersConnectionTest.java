package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class UsersConnectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsersConnection.class);
        UsersConnection usersConnection1 = new UsersConnection();
        usersConnection1.setId(1L);
        UsersConnection usersConnection2 = new UsersConnection();
        usersConnection2.setId(usersConnection1.getId());
        assertThat(usersConnection1).isEqualTo(usersConnection2);
        usersConnection2.setId(2L);
        assertThat(usersConnection1).isNotEqualTo(usersConnection2);
        usersConnection1.setId(null);
        assertThat(usersConnection1).isNotEqualTo(usersConnection2);
    }
}
