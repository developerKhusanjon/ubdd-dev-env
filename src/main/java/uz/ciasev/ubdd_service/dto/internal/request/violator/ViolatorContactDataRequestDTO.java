package uz.ciasev.ubdd_service.dto.internal.request.violator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.dto.internal.request.ActorDetailRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ValidEmploymentData;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ValidEmploymentData(message = ErrorCode.VIOLATOR_DETAIL_INVALID)
public class ViolatorContactDataRequestDTO implements ActorDetailRequestDTO {

    @NotNull(message = ErrorCode.MOBILE_REQUIRED)
    @NotBlank(message = ErrorCode.MOBILE_REQUIRED)
    @Pattern(regexp = "^\\d{12}$", message = ErrorCode.VIOLATOR_MOBILE_FORMAT_INVALID)
    private String mobile;

    @Pattern(regexp = "^\\d{9}$", message = ErrorCode.VIOLATOR_LANDLINE_FORMAT_INVALID)
    private String landline;

    @NotNull(message = ErrorCode.OCCUPATION_REQUIRED)
    @ActiveOnly(message = ErrorCode.OCCUPATION_DEACTIVATED)
    @JsonProperty(value = "occupationId")
    private Occupation occupation;

    @Size(max = 250, message = ErrorCode.MAX_EMPLOYMENT_PLACE_LENGTH)
    private String employmentPlace;

    @JsonUnwrapped
    @Valid
    private LastEmploymentInfoDTO lastEmploymentInfo;

    @Override
    public String getEmploymentPosition() {
        return "";
    }

    public ViolatorDetail applyTo(ViolatorDetail violatorDetail) {

        violatorDetail.setOccupation(this.occupation);
        violatorDetail.setEmploymentPlace(this.employmentPlace);

        return violatorDetail;
    }

    public Violator applyTo(Violator violator) {

        violator.setMobile(this.mobile);
        violator.setLandline(this.landline);

        return violator;
    }
}