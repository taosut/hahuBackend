package et.com.hahu.repository;

import et.com.hahu.domain.Profile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Profile entity.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {

    @Query(value = "select distinct profile from Profile profile left join fetch profile.families",
        countQuery = "select count(distinct profile) from Profile profile")
    Page<Profile> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct profile from Profile profile left join fetch profile.families")
    List<Profile> findAllWithEagerRelationships();

    @Query("select profile from Profile profile left join fetch profile.families where profile.id =:id")
    Optional<Profile> findOneWithEagerRelationships(@Param("id") Long id);
}
