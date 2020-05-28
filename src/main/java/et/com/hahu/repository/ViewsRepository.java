package et.com.hahu.repository;

import et.com.hahu.domain.Views;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Views entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewsRepository extends JpaRepository<Views, Long>, JpaSpecificationExecutor<Views> {

    @Query("select views from Views views where views.user.login = ?#{principal.username}")
    List<Views> findByUserIsCurrentUser();
}
