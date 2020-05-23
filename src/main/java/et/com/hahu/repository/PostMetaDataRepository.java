package et.com.hahu.repository;

import et.com.hahu.domain.PostMetaData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PostMetaData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostMetaDataRepository extends JpaRepository<PostMetaData, Long>, JpaSpecificationExecutor<PostMetaData> {
}
