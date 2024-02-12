package uz.ciasev.ubdd_service.dto.internal.request.victim;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.dto.internal.request.ActorDetailRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.person.IntoxicationType;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidEmploymentData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ValidEmploymentData(message = ErrorCode.VICTIM_DETAIL_INVALID)
public class VictimDetailRequestDTO implements ActorDetailRequestDTO {

    @NotNull(message = ErrorCode.OCCUPATION_REQUIRED)
    @ActiveOnly(message = ErrorCode.OCCUPATION_DEACTIVATED)
    @JsonProperty(value = "occupationId")
    private Occupation occupation;

    @Size(max = 250, message = ErrorCode.MAX_EMPLOYMENT_POSITION_LENGTH)
    private String employmentPlace;

    @Size(max = 250, message = ErrorCode.MAX_EMPLOYMENT_POSITION_LENGTH)
    private String employmentPosition;

    @Valid
    @JsonUnwrapped
    private LastEmploymentInfoDTO lastEmploymentInfoDTO;

    @ActiveOnly(message = ErrorCode.INTOXICATION_TYPE_DEACTIVATED)
    @JsonProperty(value = "intoxicationTypeId")
    private IntoxicationType intoxicationType;

    private String signature;

    public VictimDetail buildDetail() {
        VictimDetail victimDetail = new VictimDetail();

        victimDetail.setOccupation(this.occupation);
        victimDetail.setEmploymentPlace(this.employmentPlace);
        victimDetail.setEmploymentPosition(this.employmentPosition);
        victimDetail.setIntoxicationType(this.intoxicationType);
        victimDetail.setSignature(this.signature);

        return victimDetail;
    }

    public VictimDetail applyTo(VictimDetail victimDetail) {
        victimDetail.setOccupation(this.occupation);
        victimDetail.setEmploymentPlace(this.employmentPlace);
        victimDetail.setEmploymentPosition(this.employmentPosition);
        victimDetail.setIntoxicationType(this.intoxicationType);
        victimDetail.setSignature(this.signature);

        return victimDetail;
    }
}
