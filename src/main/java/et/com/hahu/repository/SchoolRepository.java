package et.com.hahu.repository;

import et.com.hahu.domain.School;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the School entity.
 */
@Repository
public interface SchoolRepository extends JpaRepository<School, Long>, JpaSpecificationExecutor<School> {

    @Query(value = "select distinct school from School school left join fetch school.users",
        countQuery = "select count(distinct school) from School school")
    Page<School> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct school from School school left join fetch school.users")
    List<School> findAllWithEagerRelationships();

    @Query("select school from School school left join fetch school.users where school.id =:id")
    Optional<School> findOneWithEagerRelationships(@Param("id") Long id);
}
