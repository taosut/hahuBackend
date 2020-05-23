package et.com.hahu.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificationMetaDataMapperTest {

    private NotificationMetaDataMapper notificationMetaDataMapper;

    @BeforeEach
    public void setUp() {
        notificationMetaDataMapper = new NotificationMetaDataMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(notificationMetaDataMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(notificationMetaDataMapper.fromId(null)).isNull();
    }
}
