package et.com.hahu.repository;

import et.com.hahu.domain.ImageMetaData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ImageMetaData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageMetaDataRepository extends JpaRepository<ImageMetaData, Long>, JpaSpecificationExecutor<ImageMetaData> {
}
