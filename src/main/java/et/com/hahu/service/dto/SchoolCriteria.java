package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import et.com.hahu.domain.enumeration.ContentType;
import et.com.hahu.domain.enumeration.ContentType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link et.com.hahu.domain.School} entity. This class is used
 * in {@link et.com.hahu.web.rest.SchoolResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /schools?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SchoolCriteria implements Serializable, Criteria {
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

    private StringFilter name;

    private StringFilter phone;

    private StringFilter email;

    private StringFilter website;

    private ContentTypeFilter aboutType;

    private ContentTypeFilter locationType;

    private LongFilter userGroupId;

    private LongFilter userId;

    public SchoolCriteria() {
    }

    public SchoolCriteria(SchoolCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.aboutType = other.aboutType == null ? null : other.aboutType.copy();
        this.locationType = other.locationType == null ? null : other.locationType.copy();
        this.userGroupId = other.userGroupId == null ? null : other.userGroupId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public SchoolCriteria copy() {
        return new SchoolCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getWebsite() {
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
    }

    public ContentTypeFilter getAboutType() {
        return aboutType;
    }

    public void setAboutType(ContentTypeFilter aboutType) {
        this.aboutType = aboutType;
    }

    public ContentTypeFilter getLocationType() {
        return locationType;
    }

    public void setLocationType(ContentTypeFilter locationType) {
        this.locationType = locationType;
    }

    public LongFilter getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(LongFilter userGroupId) {
        this.userGroupId = userGroupId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SchoolCriteria that = (SchoolCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(website, that.website) &&
            Objects.equals(aboutType, that.aboutType) &&
            Objects.equals(locationType, that.locationType) &&
            Objects.equals(userGroupId, that.userGroupId) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        phone,
        email,
        website,
        aboutType,
        locationType,
        userGroupId,
        userId
        );
    }

    @Override
    public String toString() {
        return "SchoolCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (website != null ? "website=" + website + ", " : "") +
                (aboutType != null ? "aboutType=" + aboutType + ", " : "") +
                (locationType != null ? "locationType=" + locationType + ", " : "") +
                (userGroupId != null ? "userGroupId=" + userGroupId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
