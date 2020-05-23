package et.com.hahu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ScheduleType.
 */
@Entity
@Table(name = "schedule_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScheduleType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "scheduleType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Schedule> schedules = new HashSet<>();

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

    public ScheduleType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public ScheduleType schedules(Set<Schedule> schedules) {
        this.schedules = schedules;
        return this;
    }

    public ScheduleType addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
        schedule.setScheduleType(this);
        return this;
    }

    public ScheduleType removeSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
        schedule.setScheduleType(null);
        return this;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleType)) {
            return false;
        }
        return id != null && id.equals(((ScheduleType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
