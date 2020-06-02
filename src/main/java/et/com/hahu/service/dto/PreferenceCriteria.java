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
 * Criteria class for the {@link et.com.hahu.domain.Preference} entity. This class is used
 * in {@link et.com.hahu.web.rest.PreferenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /preferences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PreferenceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter hasPrefereceSelected;

    private LongFilter userId;

    private LongFilter categoryId;

    public PreferenceCriteria() {
    }

    public PreferenceCriteria(PreferenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hasPrefereceSelected = other.hasPrefereceSelected == null ? null : other.hasPrefereceSelected.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
    }

    @Override
    public PreferenceCriteria copy() {
        return new PreferenceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getHasPrefereceSelected() {
        return hasPrefereceSelected;
    }

    public void setHasPrefereceSelected(BooleanFilter hasPrefereceSelected) {
        this.hasPrefereceSelected = hasPrefereceSelected;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PreferenceCriteria that = (PreferenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(hasPrefereceSelected, that.hasPrefereceSelected) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        hasPrefereceSelected,
        userId,
        categoryId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreferenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (hasPrefereceSelected != null ? "hasPrefereceSelected=" + hasPrefereceSelected + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
            "}";
    }

}
