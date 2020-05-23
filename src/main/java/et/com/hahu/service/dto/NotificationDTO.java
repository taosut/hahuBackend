package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;
import javax.persistence.Lob;
import et.com.hahu.domain.enumeration.ContentType;

/**
 * A DTO for the {@link et.com.hahu.domain.Notification} entity.
 */
@ApiModel(description = "The Notification entity contains notificationfor user.\n@author A true hailemaryam")
public class NotificationDTO implements Serializable {
    
    private Long id;

    @Lob
    private byte[] featuredImage;

    private String featuredImageContentType;
    private String title;

    @Lob
    private String content;

    private ContentType contentType;

    private String link;

    private Instant date;

    private Boolean markAsRead;


    private Long userId;

    private String userLogin;

    private Long userGroupId;

    private String userGroupName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageContentType() {
        return featuredImageContentType;
    }

    public void setFeaturedImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean isMarkAsRead() {
        return markAsRead;
    }

    public void setMarkAsRead(Boolean markAsRead) {
        this.markAsRead = markAsRead;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationDTO)) {
            return false;
        }

        return id != null && id.equals(((NotificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", featuredImage='" + getFeaturedImage() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", link='" + getLink() + "'" +
            ", date='" + getDate() + "'" +
            ", markAsRead='" + isMarkAsRead() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", userGroupId=" + getUserGroupId() +
            ", userGroupName='" + getUserGroupName() + "'" +
            "}";
    }
}
