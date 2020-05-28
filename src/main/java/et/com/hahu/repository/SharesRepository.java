package et.com.hahu.repository;

import et.com.hahu.domain.Shares;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Shares entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SharesRepository extends JpaRepository<Shares, Long>, JpaSpecificationExecutor<Shares> {

    @Query("select shares from Shares shares where shares.user.login = ?#{principal.username}")
    List<Shares> findByUserIsCurrentUser();
}
