package et.com.hahu.repository;

import et.com.hahu.domain.Preference;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Preference entity.
 */
@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Long>, JpaSpecificationExecutor<Preference> {

    @Query(value = "select distinct preference from Preference preference left join fetch preference.categories",
        countQuery = "select count(distinct preference) from Preference preference")
    Page<Preference> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct preference from Preference preference left join fetch preference.categories")
    List<Preference> findAllWithEagerRelationships();

    @Query("select preference from Preference preference left join fetch preference.categories where preference.id =:id")
    Optional<Preference> findOneWithEagerRelationships(@Param("id") Long id);
}
