package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class UsersConnectionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsersConnectionDTO.class);
        UsersConnectionDTO usersConnectionDTO1 = new UsersConnectionDTO();
        usersConnectionDTO1.setId(1L);
        UsersConnectionDTO usersConnectionDTO2 = new UsersConnectionDTO();
        assertThat(usersConnectionDTO1).isNotEqualTo(usersConnectionDTO2);
        usersConnectionDTO2.setId(usersConnectionDTO1.getId());
        assertThat(usersConnectionDTO1).isEqualTo(usersConnectionDTO2);
        usersConnectionDTO2.setId(2L);
        assertThat(usersConnectionDTO1).isNotEqualTo(usersConnectionDTO2);
        usersConnectionDTO1.setId(null);
        assertThat(usersConnectionDTO1).isNotEqualTo(usersConnectionDTO2);
    }
}
