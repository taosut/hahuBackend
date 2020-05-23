package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A NotificationMetaData.
 */
@Entity
@Table(name = "notification_meta_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotificationMetaData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Lob
    @Column(name = "blob_value")
    private byte[] blobValue;

    @Column(name = "blob_value_content_type")
    private String blobValueContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "notificationMetaData", allowSetters = true)
    private Notification notification;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public NotificationMetaData name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public NotificationMetaData value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getBlobValue() {
        return blobValue;
    }

    public NotificationMetaData blobValue(byte[] blobValue) {
        this.blobValue = blobValue;
        return this;
    }

    public void setBlobValue(byte[] blobValue) {
        this.blobValue = blobValue;
    }

    public String getBlobValueContentType() {
        return blobValueContentType;
    }

    public NotificationMetaData blobValueContentType(String blobValueContentType) {
        this.blobValueContentType = blobValueContentType;
        return this;
    }

    public void setBlobValueContentType(String blobValueContentType) {
        this.blobValueContentType = blobValueContentType;
    }

    public Notification getNotification() {
        return notification;
    }

    public NotificationMetaData notification(Notification notification) {
        this.notification = notification;
        return this;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationMetaData)) {
            return false;
        }
        return id != null && id.equals(((NotificationMetaData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationMetaData{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", blobValue='" + getBlobValue() + "'" +
            ", blobValueContentType='" + getBlobValueContentType() + "'" +
            "}";
    }
}
