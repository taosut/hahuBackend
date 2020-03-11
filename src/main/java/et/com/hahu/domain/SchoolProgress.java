package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SchoolProgress.
 */
@Entity
@Table(name = "school_progress")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchoolProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "year")
    private Integer year;

    @Column(name = "semester")
    private String semester;

    @Column(name = "result")
    private Double result;

    @ManyToOne
    @JsonIgnoreProperties("schoolProgresses")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public SchoolProgress subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getYear() {
        return year;
    }

    public SchoolProgress year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public SchoolProgress semester(String semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Double getResult() {
        return result;
    }

    public SchoolProgress result(Double result) {
        this.result = result;
        return this;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public User getUser() {
        return user;
    }

    public SchoolProgress user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolProgress)) {
            return false;
        }
        return id != null && id.equals(((SchoolProgress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SchoolProgress{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", year=" + getYear() +
            ", semester='" + getSemester() + "'" +
            ", result=" + getResult() +
            "}";
    }
}
