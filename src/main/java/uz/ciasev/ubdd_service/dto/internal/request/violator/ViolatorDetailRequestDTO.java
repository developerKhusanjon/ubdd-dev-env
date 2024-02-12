package uz.ciasev.ubdd_service.dto.internal.request.violator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.dto.internal.request.ActorDetailRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.person.IntoxicationType;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidEmploymentData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ValidEmploymentData(message = ErrorCode.VIOLATOR_DETAIL_INVALID)
public class ViolatorDetailRequestDTO implements ActorDetailRequestDTO {

    @NotNull(message = ErrorCode.OCCUPATION_REQUIRED)
    @ActiveOnly(message = ErrorCode.OCCUPATION_DEACTIVATED)
    @JsonProperty(value = "occupationId")
    private Occupation occupation;

    @Size(max = 250, message = ErrorCode.MAX_EMPLOYMENT_PLACE_LENGTH)
    private String employmentPlace;

    @Size(max = 250, message = ErrorCode.MAX_EMPLOYMENT_POSITION_LENGTH)
    private String employmentPosition;

    @Valid
    @JsonUnwrapped
    private LastEmploymentInfoDTO lastEmploymentInfo;

    @ActiveOnly(message = ErrorCode.INTOXICATION_TYPE_DEACTIVATED)
    @JsonProperty(value = "intoxicationTypeId")
    private IntoxicationType intoxicationType;

    @Size(max = 500, message = ErrorCode.MAX_EMPLOYMENT_POSITION_LENGTH)
    private String additionally;

    private String signature;

    public ViolatorDetail buildDetail() {
        ViolatorDetail violatorDetail = new ViolatorDetail();

        violatorDetail.setOccupation(this.occupation);
        violatorDetail.setEmploymentPlace(this.employmentPlace);
        violatorDetail.setEmploymentPosition(this.employmentPosition);
        violatorDetail.setIntoxicationType(this.intoxicationType);
        violatorDetail.setAdditionally(this.additionally);
        violatorDetail.setSignature(this.signature);

        return violatorDetail;
    }

    public ViolatorDetail applyTo(ViolatorDetail violatorDetail) {
        violatorDetail.setOccupation(this.occupation);
        violatorDetail.setEmploymentPlace(this.employmentPlace);
        violatorDetail.setEmploymentPosition(this.employmentPosition);
        violatorDetail.setIntoxicationType(this.intoxicationType);
        violatorDetail.setAdditionally(this.additionally);

        return violatorDetail;
    }
}