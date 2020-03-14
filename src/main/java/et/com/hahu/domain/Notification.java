package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import et.com.hahu.domain.enumeration.ContentType;

/**
 * The Notification entity contains notificationfor user.\n@author A true hailemaryam
 */
@Entity
@Table(name = "notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "featured_image")
    private byte[] featuredImage;

    @Column(name = "featured_image_content_type")
    private String featuredImageContentType;

    @Column(name = "title")
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;

    @Column(name = "link")
    private String link;

    @Column(name = "date")
    private Instant date;

    @Column(name = "mark_as_read")
    private Boolean markAsRead;

    @OneToMany(mappedBy = "notification")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NotificationMetaData> notificationMetaData = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("notifications")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("notifications")
    private UserGroup userGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFeaturedImage() {
        return featuredImage;
    }

    public Notification featuredImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
        return this;
    }

    public void setFeaturedImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageContentType() {
        return featuredImageContentType;
    }

    public Notification featuredImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
        return this;
    }

    public void setFeaturedImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
    }

    public String getTitle() {
        return title;
    }

    public Notification title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Notification content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public Notification contentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getLink() {
        return link;
    }

    public Notification link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Instant getDate() {
        return date;
    }

    public Notification date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean isMarkAsRead() {
        return markAsRead;
    }

    public Notification markAsRead(Boolean markAsRead) {
        this.markAsRead = markAsRead;
        return this;
    }

    public void setMarkAsRead(Boolean markAsRead) {
        this.markAsRead = markAsRead;
    }

    public Set<NotificationMetaData> getNotificationMetaData() {
        return notificationMetaData;
    }

    public Notification notificationMetaData(Set<NotificationMetaData> notificationMetaData) {
        this.notificationMetaData = notificationMetaData;
        return this;
    }

    public Notification addNotificationMetaData(NotificationMetaData notificationMetaData) {
        this.notificationMetaData.add(notificationMetaData);
        notificationMetaData.setNotification(this);
        return this;
    }

    public Notification removeNotificationMetaData(NotificationMetaData notificationMetaData) {
        this.notificationMetaData.remove(notificationMetaData);
        notificationMetaData.setNotification(null);
        return this;
    }

    public void setNotificationMetaData(Set<NotificationMetaData> notificationMetaData) {
        this.notificationMetaData = notificationMetaData;
    }

    public User getUser() {
        return user;
    }

    public Notification user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public Notification userGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
        return this;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Notification{" +
            "id=" + getId() +
            ", featuredImage='" + getFeaturedImage() + "'" +
            ", featuredImageContentType='" + getFeaturedImageContentType() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", link='" + getLink() + "'" +
            ", date='" + getDate() + "'" +
            ", markAsRead='" + isMarkAsRead() + "'" +
            "}";
    }
}
