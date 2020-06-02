package et.com.hahu.repository;

import et.com.hahu.domain.Family;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Family entity.
 */
@Repository
public interface FamilyRepository extends JpaRepository<Family, Long>, JpaSpecificationExecutor<Family> {

    @Query(value = "select distinct family from Family family left join fetch family.parents",
        countQuery = "select count(distinct family) from Family family")
    Page<Family> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct family from Family family left join fetch family.parents")
    List<Family> findAllWithEagerRelationships();

    @Query("select family from Family family left join fetch family.parents where family.id =:id")
    Optional<Family> findOneWithEagerRelationships(@Param("id") Long id);
}
