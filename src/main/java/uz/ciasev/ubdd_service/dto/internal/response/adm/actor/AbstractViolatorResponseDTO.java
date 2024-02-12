package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.InnerPersonResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ArticleResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.utils.ConvertUtils;

import java.util.List;

@Getter
public abstract class AbstractViolatorResponseDTO {

    @JsonUnwrapped
    private final InnerPersonResponseDTO person;

    private final Boolean isArchived;
    private final Long admCaseId;
    private final Long mergedToViolatorId;
    private final Long actualAddressId;
    private final Long postAddressId;
    private final String inn;
    private final String mobile;
    private final String landline;
    private final Long childrenAmount;
    private final Long dependentAmount;
    private final Long healthStatusId;
    private final Long educationLevelId;
    private final Long maritalStatusId;
    private final Long violationRepeatabilityStatusId;
    private final List<ArticleResponseDTO> earlierViolatedArticleParts;
    private final boolean notificationViaSms;
    private final boolean notificationViaMail;


    public AbstractViolatorResponseDTO(Violator violator, Person person) {

        this.person = new InnerPersonResponseDTO(person);

        this.isArchived = violator.getIsArchived();
        this.admCaseId = violator.getAdmCaseId();
        this.mergedToViolatorId = violator.getMergedToViolatorId();
        this.actualAddressId = violator.getActualAddressId();
        this.postAddressId = violator.getPostAddressId();
        this.inn = violator.getInn();
        this.mobile = violator.getMobile();
        this.landline = violator.getLandline();
        this.childrenAmount = violator.getChildrenAmount();
        this.dependentAmount = violator.getDependentAmount();
        this.healthStatusId = violator.getHealthStatusId();
        this.educationLevelId = violator.getEducationLevelId();
        this.maritalStatusId = violator.getMaritalStatusId();
        this.violationRepeatabilityStatusId = violator.getViolationRepeatabilityStatusId();
//        this.additionCaseArticleParts = ConvertUtils.jsonArticleToDTO(violator.getAdditionCaseArticleParts());
        this.earlierViolatedArticleParts = ConvertUtils.jsonArticleToDTO(violator.getEarlierViolatedArticleParts());
        this.notificationViaSms = violator.isNotificationViaSms();
        this.notificationViaMail = violator.isNotificationViaMail();
    }
}