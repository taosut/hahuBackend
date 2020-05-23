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
 * Criteria class for the {@link et.com.hahu.domain.SchoolProgress} entity. This class is used
 * in {@link et.com.hahu.web.rest.SchoolProgressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /school-progresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SchoolProgressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter subject;

    private IntegerFilter year;

    private StringFilter semester;

    private DoubleFilter result;

    private LongFilter userId;

    public SchoolProgressCriteria() {
    }

    public SchoolProgressCriteria(SchoolProgressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.semester = other.semester == null ? null : other.semester.copy();
        this.result = other.result == null ? null : other.result.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public SchoolProgressCriteria copy() {
        return new SchoolProgressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public StringFilter getSemester() {
        return semester;
    }

    public void setSemester(StringFilter semester) {
        this.semester = semester;
    }

    public DoubleFilter getResult() {
        return result;
    }

    public void setResult(DoubleFilter result) {
        this.result = result;
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
        final SchoolProgressCriteria that = (SchoolProgressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(year, that.year) &&
            Objects.equals(semester, that.semester) &&
            Objects.equals(result, that.result) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        subject,
        year,
        semester,
        result,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchoolProgressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (semester != null ? "semester=" + semester + ", " : "") +
                (result != null ? "result=" + result + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
