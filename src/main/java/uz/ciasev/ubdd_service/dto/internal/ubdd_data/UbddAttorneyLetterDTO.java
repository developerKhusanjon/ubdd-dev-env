package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UbddAttorneyLetterDTO {

    @Size(max = 50, message = ErrorCode.UBDD_ATTORNEY_LETTER_ATTORNEY_TYPE_MIN_MAX_SIZE)
    private String attorneyType;

    @NotNull(message = ErrorCode.UBDD_ATTORNEY_LETTER_FROM_DATE_REQUIRED)
    private LocalDate fromDate;

    @NotNull(message = ErrorCode.UBDD_ATTORNEY_LETTER_TO_DATE_REQUIRED)
    private LocalDate toDate;

    @Size(max = 20, message = ErrorCode.UBDD_ATTORNEY_LETTER_NOTARY_CODE_MIN_MAX_SIZE)
    private String notaryCode;

    //@NotBlank(message = ErrorCode.UBDD_ATTORNEY_LETTER_NOTARY_NAME_REQUIRED)
    @Size(max = 300, message = ErrorCode.UBDD_ATTORNEY_LETTER_NOTARY_NAME_MIN_MAX_SIZE)
    private String notaryName;

    //@NotBlank(message = ErrorCode.UBDD_ATTORNEY_LETTER_NOTARY_ORGANIZATION_REQUIRED)
    @Size(max = 500, message = ErrorCode.UBDD_ATTORNEY_LETTER_NOTARY_ORGANIZATION_MIN_MAX_SIZE)
    private String notaryOrganization;

    @NotBlank(message = ErrorCode.UBDD_ATTORNEY_LETTER_NUMBER_REQUIRED)
    @Size(max = 20, message = ErrorCode.UBDD_ATTORNEY_LETTER_NUMBER_MIN_MAX_SIZE)
    private String number;

    private LocalDate registrationDate;
    private LocalDateTime registrationTime;

    @Valid
    @NotNull(message = ErrorCode.UBDD_ATTORNEY_LETTER_MEMBER_REQUIRED)
    private UbddAttorneyLetterMemberDTO member;

    @Valid
    @NotNull(message = ErrorCode.UBDD_ATTORNEY_LETTER_SUBJECT_REQUIRED)
    private UbddAttorneyLetterSubjectDTO subject;

    @Size(max = 200, message = ErrorCode.UBDD_ATTORNEY_LETTER_BLANK_NUMBER_MIN_MAX_SIZE)
    private String blankNumber;
}
