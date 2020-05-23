package et.com.hahu.repository;

import et.com.hahu.domain.UsersConnection;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UsersConnection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersConnectionRepository extends JpaRepository<UsersConnection, Long>, JpaSpecificationExecutor<UsersConnection> {

    @Query("select usersConnection from UsersConnection usersConnection where usersConnection.follower.login = ?#{principal.username}")
    List<UsersConnection> findByFollowerIsCurrentUser();

    @Query("select usersConnection from UsersConnection usersConnection where usersConnection.following.login = ?#{principal.username}")
    List<UsersConnection> findByFollowingIsCurrentUser();
}
