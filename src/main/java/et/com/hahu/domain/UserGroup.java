package et.com.hahu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * The UserGroup entity will store user groups.\n@author A true hailemaryam
 */
@Entity
@Table(name = "user_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "userGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AdditionalUserInfo> additionalUserInfos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public UserGroup groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<AdditionalUserInfo> getAdditionalUserInfos() {
        return additionalUserInfos;
    }

    public UserGroup additionalUserInfos(Set<AdditionalUserInfo> additionalUserInfos) {
        this.additionalUserInfos = additionalUserInfos;
        return this;
    }

    public UserGroup addAdditionalUserInfo(AdditionalUserInfo additionalUserInfo) {
        this.additionalUserInfos.add(additionalUserInfo);
        additionalUserInfo.setUserGroup(this);
        return this;
    }

    public UserGroup removeAdditionalUserInfo(AdditionalUserInfo additionalUserInfo) {
        this.additionalUserInfos.remove(additionalUserInfo);
        additionalUserInfo.setUserGroup(null);
        return this;
    }

    public void setAdditionalUserInfos(Set<AdditionalUserInfo> additionalUserInfos) {
        this.additionalUserInfos = additionalUserInfos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroup)) {
            return false;
        }
        return id != null && id.equals(((UserGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            "}";
    }
}
