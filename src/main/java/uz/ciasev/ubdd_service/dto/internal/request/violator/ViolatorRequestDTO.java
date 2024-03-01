package uz.ciasev.ubdd_service.dto.internal.request.violator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.QualificationArticleRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.court.EducationLevel;
import uz.ciasev.ubdd_service.entity.dict.court.HealthStatus;
import uz.ciasev.ubdd_service.entity.dict.court.MaritalStatus;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationRepeatabilityStatus;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.types.ArticlePairJson;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ViolatorRequestDTO {

    @NotBlank(message = ErrorCode.MOBILE_REQUIRED)
    @Pattern(regexp = "^\\d{12}$", message = ErrorCode.VIOLATOR_MOBILE_FORMAT_INVALID)
    private String mobile;

    @Pattern(regexp = "^\\d{9}$", message = ErrorCode.VIOLATOR_LANDLINE_FORMAT_INVALID)
    private String landline;

    private Long dependentAmount;

    @Pattern(regexp = "^\\d{9}$", message = ErrorCode.VIOLATOR_INN_FORMAT_INVALID)
    private String inn;

    @ActiveOnly(message = ErrorCode.HEALTH_STATUS_DEACTIVATED)
    @JsonProperty(value = "healthStatusId")
    private HealthStatus healthStatus;

    @ActiveOnly(message = ErrorCode.EDUCATION_LEVEL_DEACTIVATED)
    @JsonProperty(value = "educationLevelId")
    private EducationLevel educationLevel;

    @ActiveOnly(message = ErrorCode.MARITAL_STATUS_DEACTIVATED)
    @JsonProperty(value = "maritalStatusId")
    private MaritalStatus maritalStatus;

    private Long childrenAmount;

    @ActiveOnly(message = ErrorCode.VIOLATION_REPEATABILITY_STATUS_DEACTIVATED)
    @JsonProperty(value = "violationRepeatabilityStatusId")
    private ViolationRepeatabilityStatus violationRepeatabilityStatus;

    private List<QualificationArticleRequestDTO> earlierViolatedArticleParts;

    public Violator buildViolator() {
        Violator violator = new Violator();

        violator.setMobile(this.mobile);
        violator.setNotificationViaSms(false);
        violator.setNotificationViaMail(false);
        violator.setLandline(this.landline);
        violator.setDependentAmount(this.dependentAmount);
        violator.setInn(this.inn);
        violator.setHealthStatus(this.healthStatus);
        violator.setEducationLevel(this.educationLevel);
        violator.setMaritalStatus(this.maritalStatus);
        violator.setChildrenAmount(this.childrenAmount);
        violator.setViolationRepeatabilityStatus(violationRepeatabilityStatus);
        violator.setEarlierViolatedArticleParts(buildEarlierViolatedArticleParts());

        return violator;
    }

    public Violator applyTo(Violator violator) {

        violator.setMobile(this.mobile);
        violator.setNotificationViaSms(false);
        violator.setNotificationViaMail(false);
        violator.setLandline(this.landline);
        violator.setDependentAmount(this.dependentAmount);
        violator.setInn(this.inn);
        violator.setHealthStatus(this.healthStatus);
        violator.setEducationLevel(this.educationLevel);
        violator.setMaritalStatus(this.maritalStatus);
        violator.setChildrenAmount(this.childrenAmount);
        violator.setViolationRepeatabilityStatus(violationRepeatabilityStatus);

        return violator;
    }

    public List<ArticlePairJson> buildEarlierViolatedArticleParts() {
        return earlierViolatedArticleParts == null
                ? Collections.emptyList()
                : earlierViolatedArticleParts.stream()
                .map(QualificationArticleRequestDTO::buildArticlePairJson)
                .collect(Collectors.toList());
    }

    public List<QualificationArticleRequestDTO> getEarlierViolatedArticleParts() {
        return earlierViolatedArticleParts == null
                ? Collections.emptyList()
                : this.earlierViolatedArticleParts;
    }
}