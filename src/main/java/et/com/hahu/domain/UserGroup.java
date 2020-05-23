package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import et.com.hahu.domain.enumeration.GroupType;

/**
 * The UserGroup entity will store user groups.\n@author A true hailemaryam
 */
@Entity
@Table(name = "user_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @Lob
    @Column(name = "profile_pic")
    private byte[] profilePic;

    @Column(name = "profile_pic_content_type")
    private String profilePicContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_type")
    private GroupType groupType;

    @OneToMany(mappedBy = "userGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "userGroup")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Schedule> schedules = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "user_group_user",
               joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "user_group_owner",
               joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id"))
    private Set<User> owners = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "userGroups", allowSetters = true)
    private School school;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public UserGroup detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public UserGroup profilePic(byte[] profilePic) {
        this.profilePic = profilePic;
        return this;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePicContentType() {
        return profilePicContentType;
    }

    public UserGroup profilePicContentType(String profilePicContentType) {
        this.profilePicContentType = profilePicContentType;
        return this;
    }

    public void setProfilePicContentType(String profilePicContentType) {
        this.profilePicContentType = profilePicContentType;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public UserGroup groupType(GroupType groupType) {
        this.groupType = groupType;
        return this;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public UserGroup notifications(Set<Notification> notifications) {
        this.notifications = notifications;
        return this;
    }

    public UserGroup addNotification(Notification notification) {
        this.notifications.add(notification);
        notification.setUserGroup(this);
        return this;
    }

    public UserGroup removeNotification(Notification notification) {
        this.notifications.remove(notification);
        notification.setUserGroup(null);
        return this;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public UserGroup schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public UserGroup addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setUserGroup(this);
        return this;
    }

    public UserGroup removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setUserGroup(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<User> getUsers() {
        return users;
    }

    public UserGroup users(Set<User> users) {
        this.users = users;
        return this;
    }

    public UserGroup addUser(User user) {
        this.users.add(user);
        return this;
    }

    public UserGroup removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getOwners() {
        return owners;
    }

    public UserGroup owners(Set<User> users) {
        this.owners = users;
        return this;
    }

    public UserGroup addOwner(User user) {
        this.owners.add(user);
        return this;
    }

    public UserGroup removeOwner(User user) {
        this.owners.remove(user);
        return this;
    }

    public void setOwners(Set<User> users) {
        this.owners = users;
    }

    public School getSchool() {
        return school;
    }

    public UserGroup school(School school) {
        this.school = school;
        return this;
    }

    public void setSchool(School school) {
        this.school = school;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", profilePic='" + getProfilePic() + "'" +
            ", profilePicContentType='" + getProfilePicContentType() + "'" +
            ", groupType='" + getGroupType() + "'" +
            "}";
    }
}
