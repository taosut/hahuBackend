package et.com.hahu.repository;

import et.com.hahu.domain.UserGroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserGroup entity.
 */
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {

    @Query(value = "select distinct userGroup from UserGroup userGroup left join fetch userGroup.users",
        countQuery = "select count(distinct userGroup) from UserGroup userGroup")
    Page<UserGroup> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userGroup from UserGroup userGroup left join fetch userGroup.users")
    List<UserGroup> findAllWithEagerRelationships();

    @Query("select userGroup from UserGroup userGroup left join fetch userGroup.users where userGroup.id =:id")
    Optional<UserGroup> findOneWithEagerRelationships(@Param("id") Long id);
}
