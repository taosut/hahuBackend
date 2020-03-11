package et.com.hahu.repository;

import et.com.hahu.domain.AdditionalUserInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AdditionalUserInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdditionalUserInfoRepository extends JpaRepository<AdditionalUserInfo, Long>, JpaSpecificationExecutor<AdditionalUserInfo> {
}
