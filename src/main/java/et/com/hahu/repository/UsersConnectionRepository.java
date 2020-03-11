package et.com.hahu.repository;

import et.com.hahu.domain.UsersConnection;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UsersConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersConnectionRepository extends JpaRepository<UsersConnection, Long>, JpaSpecificationExecutor<UsersConnection> {
}
