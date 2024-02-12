package uz.ciasev.ubdd_service.dto.internal.response.settings;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;

import java.util.Optional;
import java.util.UUID;

@Getter
public class OrganAccountSettingsResponseDTO {

    private final UUID id;
    private final Long penaltyDepId;
    private final Long compensationDepId;
    private final Long organId;
    private final Long departmentId;
    private final Long regionId;
    private final Long districtId;
    private final Long articlePartId;
    private final Long bankAccountTypeId;

    public OrganAccountSettingsResponseDTO(OrganAccountSettings entity) {
        this.id = entity.getId();
        this.penaltyDepId = entity.getPenaltyAccount().getBillingId();
        this.compensationDepId = Optional.ofNullable(entity.getCompensationAccount()).map(BankAccount::getBillingId).orElse(null);
        this.organId = entity.getOrganId();
        this.departmentId = entity.getDepartmentId();
        this.regionId = entity.getRegionId();
        this.districtId = entity.getDistrictId();
        this.articlePartId = entity.getArticlePartId();
        this.bankAccountTypeId = entity.getBankAccountTypeId();
    }
}
