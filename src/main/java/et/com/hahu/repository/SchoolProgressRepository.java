package et.com.hahu.repository;

import et.com.hahu.domain.SchoolProgress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SchoolProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolProgressRepository extends JpaRepository<SchoolProgress, Long>, JpaSpecificationExecutor<SchoolProgress> {

    @Query("select schoolProgress from SchoolProgress schoolProgress where schoolProgress.user.login = ?#{principal.username}")
    List<SchoolProgress> findByUserIsCurrentUser();
}
