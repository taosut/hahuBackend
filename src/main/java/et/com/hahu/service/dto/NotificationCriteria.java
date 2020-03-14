package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import et.com.hahu.domain.enumeration.ContentType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link et.com.hahu.domain.Notification} entity. This class is used
 * in {@link et.com.hahu.web.rest.NotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NotificationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ContentType
     */
    public static class ContentTypeFilter extends Filter<ContentType> {

        public ContentTypeFilter() {
        }

        public ContentTypeFilter(ContentTypeFilter filter) {
            super(filter);
        }

        @Override
        public ContentTypeFilter copy() {
            return new ContentTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private ContentTypeFilter contentType;

    private StringFilter link;

    private InstantFilter date;

    private BooleanFilter markAsRead;

    private LongFilter notificationMetaDataId;

    private LongFilter userId;

    private LongFilter userGroupId;

    public NotificationCriteria() {
    }

    public NotificationCriteria(NotificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.contentType = other.contentType == null ? null : other.contentType.copy();
        this.link = other.link == null ? null : other.link.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.markAsRead = other.markAsRead == null ? null : other.markAsRead.copy();
        this.notificationMetaDataId = other.notificationMetaDataId == null ? null : other.notificationMetaDataId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userGroupId = other.userGroupId == null ? null : other.userGroupId.copy();
    }

    @Override
    public NotificationCriteria copy() {
        return new NotificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public ContentTypeFilter getContentType() {
        return contentType;
    }

    public void setContentType(ContentTypeFilter contentType) {
        this.contentType = contentType;
    }

    public StringFilter getLink() {
        return link;
    }

    public void setLink(StringFilter link) {
        this.link = link;
    }

    public InstantFilter getDate() {
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public BooleanFilter getMarkAsRead() {
        return markAsRead;
    }

    public void setMarkAsRead(BooleanFilter markAsRead) {
        this.markAsRead = markAsRead;
    }

    public LongFilter getNotificationMetaDataId() {
        return notificationMetaDataId;
    }

    public void setNotificationMetaDataId(LongFilter notificationMetaDataId) {
        this.notificationMetaDataId = notificationMetaDataId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(LongFilter userGroupId) {
        this.userGroupId = userGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NotificationCriteria that = (NotificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(contentType, that.contentType) &&
            Objects.equals(link, that.link) &&
            Objects.equals(date, that.date) &&
            Objects.equals(markAsRead, that.markAsRead) &&
            Objects.equals(notificationMetaDataId, that.notificationMetaDataId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userGroupId, that.userGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        contentType,
        link,
        date,
        markAsRead,
        notificationMetaDataId,
        userId,
        userGroupId
        );
    }

    @Override
    public String toString() {
        return "NotificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (contentType != null ? "contentType=" + contentType + ", " : "") +
                (link != null ? "link=" + link + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (markAsRead != null ? "markAsRead=" + markAsRead + ", " : "") +
                (notificationMetaDataId != null ? "notificationMetaDataId=" + notificationMetaDataId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (userGroupId != null ? "userGroupId=" + userGroupId + ", " : "") +
            "}";
    }

}
