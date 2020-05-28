package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class ViewsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ViewsDTO.class);
        ViewsDTO viewsDTO1 = new ViewsDTO();
        viewsDTO1.setId(1L);
        ViewsDTO viewsDTO2 = new ViewsDTO();
        assertThat(viewsDTO1).isNotEqualTo(viewsDTO2);
        viewsDTO2.setId(viewsDTO1.getId());
        assertThat(viewsDTO1).isEqualTo(viewsDTO2);
        viewsDTO2.setId(2L);
        assertThat(viewsDTO1).isNotEqualTo(viewsDTO2);
        viewsDTO1.setId(null);
        assertThat(viewsDTO1).isNotEqualTo(viewsDTO2);
    }
}
