package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotBlank;

@Data
public class UbddAttorneyLetterSubjectDTO {

    @NotBlank(message = ErrorCode.UBDD_ATTORNEY_LETTER_SUBJECT_TEX_PASS_NUMBER_REQUIRED)
    private String texPassNumber;

    @NotBlank(message = ErrorCode.UBDD_ATTORNEY_LETTER_SUBJECT_TEX_PASS_SERIES_REQUIRED)
    private String texPassSeries;

    @NotBlank(message = ErrorCode.UBDD_ATTORNEY_LETTER_SUBJECT_VEHICLE_NUMBER_REQUIRED)
    private String vehicleNumber;

    private String vehicleColor;
    private String vehicleModel;
    private String vehicleRegistrationDate;
    private Integer vehicleYear;
}
