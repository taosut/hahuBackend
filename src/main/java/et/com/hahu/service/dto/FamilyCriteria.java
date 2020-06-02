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
 * Criteria class for the {@link et.com.hahu.domain.Family} entity. This class is used
 * in {@link et.com.hahu.web.rest.FamilyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /families?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FamilyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter hasFamilyControl;

    private LongFilter userId;

    private LongFilter parentId;

    public FamilyCriteria() {
    }

    public FamilyCriteria(FamilyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hasFamilyControl = other.hasFamilyControl == null ? null : other.hasFamilyControl.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public FamilyCriteria copy() {
        return new FamilyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getHasFamilyControl() {
        return hasFamilyControl;
    }

    public void setHasFamilyControl(BooleanFilter hasFamilyControl) {
        this.hasFamilyControl = hasFamilyControl;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FamilyCriteria that = (FamilyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(hasFamilyControl, that.hasFamilyControl) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        hasFamilyControl,
        userId,
        parentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (hasFamilyControl != null ? "hasFamilyControl=" + hasFamilyControl + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }

}
