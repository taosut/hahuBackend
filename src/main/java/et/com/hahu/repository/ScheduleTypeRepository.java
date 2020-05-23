package et.com.hahu.repository;

import et.com.hahu.domain.ScheduleType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ScheduleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduleTypeRepository extends JpaRepository<ScheduleType, Long>, JpaSpecificationExecutor<ScheduleType> {
}
