package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link et.com.hahu.domain.SchoolProgress} entity.
 */
public class SchoolProgressDTO implements Serializable {
    
    private Long id;

    private String subject;

    private Integer year;

    private String semester;

    private Double result;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchoolProgressDTO schoolProgressDTO = (SchoolProgressDTO) o;
        if (schoolProgressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schoolProgressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchoolProgressDTO{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", year=" + getYear() +
            ", semester='" + getSemester() + "'" +
            ", result=" + getResult() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
