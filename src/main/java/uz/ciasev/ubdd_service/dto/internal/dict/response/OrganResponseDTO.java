package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.UploadFileDTO;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Getter
public class OrganResponseDTO extends DictResponseDTO {

    private final MultiLanguage shortName;
    private final LocalFileUrl logoUrl;
    private final String logoPath;
    private final Long sendCardToMIB;
    private final UploadFileDTO logo;
    private final Boolean isMvd;
    private final Boolean isArticleLevelMoneySeparation;
    private final Boolean isCourtPenaltyRecipient;
    private final Boolean isCourtCompensationRecipient;
    private final Long investigatingOrganization;
    private final String investigatingOrganizationName;
    private final Boolean allowSmsNotification;
    private final String smsContract;
    private final Boolean allowMailNotification;
    private final Boolean isAdministrationBlocked;
    private final Boolean isGlobalCriminalInvestigator;
    private final Long criminalInvestigatingDepartmentOrganId;
    private final Long maxAccountsPerUser;


    public OrganResponseDTO(Organ entity) {
        super(entity);
        this.shortName = entity.getShortName();
        this.logoUrl = LocalFileUrl.ofNullable(entity.getLogoPath());
        this.logoPath = entity.getLogoPath();
        this.sendCardToMIB = entity.getSendCardToMIB();
        this.logo = UploadFileDTO.ofNullable(entity.getLogoPath());
        this.isMvd = entity.getIsMvd();
        this.isArticleLevelMoneySeparation = entity.getIsArticleLevelMoneySeparation();
        this.isCourtPenaltyRecipient = entity.getIsCourtPenaltyRecipient();
        this.isCourtCompensationRecipient = entity.getIsCourtCompensationRecipient();
        this.investigatingOrganization = entity.getInvestigatingOrganization();
        this.investigatingOrganizationName = entity.getInvestigatingOrganizationName();
        this.allowSmsNotification = entity.getSmsNotification();
        this.smsContract = entity.getSmsContract();
        this.allowMailNotification = entity.getMailNotification();
        this.isAdministrationBlocked = entity.getIsAdministrationBlocked();
        this.isGlobalCriminalInvestigator = entity.getIsGlobalCriminalInvestigator();
        this.criminalInvestigatingDepartmentOrganId = entity.getCriminalInvestigatingDepartmentOrganId();
        this.maxAccountsPerUser = entity.getMaxAccountsPerUser();
    }
}
