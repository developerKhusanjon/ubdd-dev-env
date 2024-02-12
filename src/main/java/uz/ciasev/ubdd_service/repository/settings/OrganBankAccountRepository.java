package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganBankAccountRepository extends JpaRepository<OrganAccountSettings, UUID>, JpaSpecificationExecutor<OrganAccountSettings> {

    Optional<OrganAccountSettings> findByArticlePartAndOrganAndDepartmentAndRegionAndDistrictAndBankAccountTypeId(ArticlePart articlePart,
                                                                                              Organ organ, Department department,
                                                                                              Region region, District district, Long bankAccountType);

    @Query("SELECT oas FROM OrganAccountSettings oas WHERE oas.id IN :ids")
    List<OrganAccountSettings> findByIdIn(List<UUID> ids);

    @Modifying
    @Query("DELETE FROM OrganAccountSettings oas WHERE oas.id IN :ids")
    void deleteByIdIn(List<UUID> ids);
}
