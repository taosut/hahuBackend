package et.com.hahu.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import et.com.hahu.web.rest.TestUtil;

public class NotificationMetaDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationMetaData.class);
        NotificationMetaData notificationMetaData1 = new NotificationMetaData();
        notificationMetaData1.setId(1L);
        NotificationMetaData notificationMetaData2 = new NotificationMetaData();
        notificationMetaData2.setId(notificationMetaData1.getId());
        assertThat(notificationMetaData1).isEqualTo(notificationMetaData2);
        notificationMetaData2.setId(2L);
        assertThat(notificationMetaData1).isNotEqualTo(notificationMetaData2);
        notificationMetaData1.setId(null);
        assertThat(notificationMetaData1).isNotEqualTo(notificationMetaData2);
    }
}
