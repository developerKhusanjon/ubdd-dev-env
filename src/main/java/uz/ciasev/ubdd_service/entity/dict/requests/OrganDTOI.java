package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

public interface OrganDTOI extends DictUpdateDTOI, DictCreateDTOI {
    MultiLanguage getShortName();
    String getLogoPath();
    Boolean getIsMvd();
    Boolean getIsArticleLevelMoneySeparation();
    Boolean getIsCourtPenaltyRecipient();
    Boolean getIsCourtCompensationRecipient();
    Long getInvestigatingOrganization();
    String getInvestigatingOrganizationName();
    Boolean getAllowSmsNotification();
    String getSmsContract();
    Boolean getAllowMailNotification();
    Boolean getIsAdministrationBlocked();
    Boolean getIsGlobalCriminalInvestigator();
    Organ getCriminalInvestigatingDepartmentOrgan();
    Long getMaxAccountsPerUser();
}
