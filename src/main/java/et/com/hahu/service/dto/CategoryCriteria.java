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
 * Criteria class for the {@link et.com.hahu.domain.Category} entity. This class is used
 * in {@link et.com.hahu.web.rest.CategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private BooleanFilter recomendationCategory;

    private LongFilter postId;

    private LongFilter preferenceId;

    public CategoryCriteria() {
    }

    public CategoryCriteria(CategoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.recomendationCategory = other.recomendationCategory == null ? null : other.recomendationCategory.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
        this.preferenceId = other.preferenceId == null ? null : other.preferenceId.copy();
    }

    @Override
    public CategoryCriteria copy() {
        return new CategoryCriteria(this);
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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public BooleanFilter getRecomendationCategory() {
        return recomendationCategory;
    }

    public void setRecomendationCategory(BooleanFilter recomendationCategory) {
        this.recomendationCategory = recomendationCategory;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }

    public LongFilter getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(LongFilter preferenceId) {
        this.preferenceId = preferenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategoryCriteria that = (CategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(recomendationCategory, that.recomendationCategory) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(preferenceId, that.preferenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        recomendationCategory,
        postId,
        preferenceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (recomendationCategory != null ? "recomendationCategory=" + recomendationCategory + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
                (preferenceId != null ? "preferenceId=" + preferenceId + ", " : "") +
            "}";
    }

}
