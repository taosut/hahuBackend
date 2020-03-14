package et.com.hahu.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class NotificationMetaDataDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationMetaDataDTO.class);
        NotificationMetaDataDTO notificationMetaDataDTO1 = new NotificationMetaDataDTO();
        notificationMetaDataDTO1.setId(1L);
        NotificationMetaDataDTO notificationMetaDataDTO2 = new NotificationMetaDataDTO();
        assertThat(notificationMetaDataDTO1).isNotEqualTo(notificationMetaDataDTO2);
        notificationMetaDataDTO2.setId(notificationMetaDataDTO1.getId());
        assertThat(notificationMetaDataDTO1).isEqualTo(notificationMetaDataDTO2);
        notificationMetaDataDTO2.setId(2L);
        assertThat(notificationMetaDataDTO1).isNotEqualTo(notificationMetaDataDTO2);
        notificationMetaDataDTO1.setId(null);
        assertThat(notificationMetaDataDTO1).isNotEqualTo(notificationMetaDataDTO2);
    }
}
