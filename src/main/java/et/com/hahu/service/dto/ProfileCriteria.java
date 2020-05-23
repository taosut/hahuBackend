package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link et.com.hahu.domain.Profile} entity. This class is used
 * in {@link et.com.hahu.web.rest.ProfileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /profiles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProfileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phone;

    private LongFilter userId;

    private LongFilter familyId;

    public ProfileCriteria() {
    }

    public ProfileCriteria(ProfileCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.familyId = other.familyId == null ? null : other.familyId.copy();
    }

    @Override
    public ProfileCriteria copy() {
        return new ProfileCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getFamilyId() {
        return familyId;
    }

    public void setFamilyId(LongFilter familyId) {
        this.familyId = familyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProfileCriteria that = (ProfileCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(familyId, that.familyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phone,
        userId,
        familyId
        );
    }

    @Override
    public String toString() {
        return "ProfileCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (familyId != null ? "familyId=" + familyId + ", " : "") +
            "}";
    }

}
