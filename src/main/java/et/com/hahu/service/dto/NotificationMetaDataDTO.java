package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link et.com.hahu.domain.NotificationMetaData} entity.
 */
public class NotificationMetaDataDTO implements Serializable {
    
    private Long id;

    private String name;

    private String value;

    @Lob
    private byte[] blobValue;

    private String blobValueContentType;

    private Long notificationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getBlobValue() {
        return blobValue;
    }

    public void setBlobValue(byte[] blobValue) {
        this.blobValue = blobValue;
    }

    public String getBlobValueContentType() {
        return blobValueContentType;
    }

    public void setBlobValueContentType(String blobValueContentType) {
        this.blobValueContentType = blobValueContentType;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationMetaDataDTO notificationMetaDataDTO = (NotificationMetaDataDTO) o;
        if (notificationMetaDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificationMetaDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificationMetaDataDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", blobValue='" + getBlobValue() + "'" +
            ", notificationId=" + getNotificationId() +
            "}";
    }
}
