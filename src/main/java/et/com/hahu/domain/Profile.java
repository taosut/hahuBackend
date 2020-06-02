package et.com.hahu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Pattern(regexp = "^\\+[0-9]{12}$")
    @Column(name = "phone")
    private String phone;

    @Lob
    @Column(name = "curent_profile_pic")
    private byte[] curentProfilePic;

    @Column(name = "curent_profile_pic_content_type")
    private String curentProfilePicContentType;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public Profile age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public Profile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getCurentProfilePic() {
        return curentProfilePic;
    }

    public Profile curentProfilePic(byte[] curentProfilePic) {
        this.curentProfilePic = curentProfilePic;
        return this;
    }

    public void setCurentProfilePic(byte[] curentProfilePic) {
        this.curentProfilePic = curentProfilePic;
    }

    public String getCurentProfilePicContentType() {
        return curentProfilePicContentType;
    }

    public Profile curentProfilePicContentType(String curentProfilePicContentType) {
        this.curentProfilePicContentType = curentProfilePicContentType;
        return this;
    }

    public void setCurentProfilePicContentType(String curentProfilePicContentType) {
        this.curentProfilePicContentType = curentProfilePicContentType;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", age=" + getAge() +
            ", phone='" + getPhone() + "'" +
            ", curentProfilePic='" + getCurentProfilePic() + "'" +
            ", curentProfilePicContentType='" + getCurentProfilePicContentType() + "'" +
            "}";
    }
}
