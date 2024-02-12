package uz.ciasev.ubdd_service.dto.internal.dict.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.requests.OrganDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;
import uz.ciasev.ubdd_service.utils.validator.ValidFileUri;
import uz.ciasev.ubdd_service.utils.validator.ValidOrgan;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ValidOrgan
@EqualsAndHashCode(callSuper = true)
@Data
public class OrganRequestDTO extends BaseDictRequestDTO implements OrganDTOI, DictCreateRequest<Organ>, DictUpdateRequest<Organ> {

    @NotNull(message = ErrorCode.SHORT_NAME_REQUIRED)
    private MultiLanguage shortName;

    @NotNull(message = ErrorCode.LOGO_PATH_REQUIRED)
    @ValidFileUri(allow = {FileFormatAlias.PNG, FileFormatAlias.JPEG, FileFormatAlias.JPG})
    private String logoPath;

//    @NotNull(message = ErrorCode.IS_MVD_REQUIRED)
    private Long  sendCarToMIB;

    @NotNull(message = ErrorCode.IS_MVD_REQUIRED)
    private Boolean isMvd;

    @NotNull(message = ErrorCode.IS_ARTICLE_LEVEL_MONEY_SEPARATION_REQUIRED)
    private Boolean isArticleLevelMoneySeparation;

    @NotNull(message = ErrorCode.IS_COURT_PENALTY_RECIPIENT_REQUIRED)
    private Boolean isCourtPenaltyRecipient;

    @NotNull(message = ErrorCode.IS_COURT_COMPENSATION_RECIPIENT_REQUIRED)
    private Boolean isCourtCompensationRecipient;

    @NotNull(message = ErrorCode.INVESTIGATING_ORGANIZATION_REQUIRED)
    private Long investigatingOrganization;

    @NotNull(message = ErrorCode.INVESTIGATING_ORGANIZATION_NAME_REQUIRED)
    @Size(min= 3, max = 255, message = ErrorCode.INVESTIGATING_ORGANIZATION_NAME_MIN_MAX_SIZE)
    private String investigatingOrganizationName;

    @NotNull(message = ErrorCode.ALLOW_SMS_NOTIFICATION_REQUIRED)
    private Boolean allowSmsNotification;

    @Size(max = 16, message = ErrorCode.SMS_CONTRACT_MAX_SIZE)
    private String smsContract;

    @NotNull(message = ErrorCode.ALLOW_MAIL_NOTIFICATION_REQUIRED)
    private Boolean allowMailNotification;

    @NotNull(message = ErrorCode.IS_ADMINISTRATION_BLOCKED_REQUIRED)
    private Boolean isAdministrationBlocked;

    @NotNull(message = ErrorCode.IS_GLOBAL_CRIMINAL_INVESTIGATOR_REQUIRED)
    private Boolean isGlobalCriminalInvestigator;

    @JsonProperty(value = "criminalInvestigatingDepartmentOrganId")
    private Organ criminalInvestigatingDepartmentOrgan;

    @NotNull(message = ErrorCode.MAX_ACCOUNTS_PER_USER_REQUIRED)
    @Min(value = 1, message = ErrorCode.MAX_ACCOUNTS_PER_USER_MIN_SIZE)
    private Long maxAccountsPerUser;

    @Override
    public void applyToNew(Organ entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(Organ entity) {
        entity.update(this);
    }
}
