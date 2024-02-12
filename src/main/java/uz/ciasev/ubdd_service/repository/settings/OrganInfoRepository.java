package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uz.ciasev.ubdd_service.entity.settings.OrganInfo;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;

import java.util.Optional;

public interface OrganInfoRepository extends JpaRepository<OrganInfo, Long>, JpaSpecificationExecutor<OrganInfo> {

    Optional<OrganInfo> findByOrganAndDepartmentAndRegionAndDistrict(Organ organ,
                                                                     Department department,
                                                                     Region region,
                                                                     District district);
}
