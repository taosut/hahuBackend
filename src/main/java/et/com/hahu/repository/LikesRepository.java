package et.com.hahu.repository;

import et.com.hahu.domain.Likes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Likes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>, JpaSpecificationExecutor<Likes> {

    @Query("select likes from Likes likes where likes.user.login = ?#{principal.username}")
    List<Likes> findByUserIsCurrentUser();
}
