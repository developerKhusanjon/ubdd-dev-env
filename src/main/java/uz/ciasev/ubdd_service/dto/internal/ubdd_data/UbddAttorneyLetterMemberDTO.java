package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotBlank;

@Data
public class UbddAttorneyLetterMemberDTO {

    @NotBlank(message = ErrorCode.UBDD_ATTORNEY_LETTER_MEMBER_LAST_NAME_REQUIRED)
    private String lastName;

    @NotBlank(message = ErrorCode.UBDD_ATTORNEY_LETTER_MEMBER_FIRST_NAME_REQUIRED)
    private String firstName;

    private String secondName;

    private String address;
    private String birthDate;
    private String blankNumber;
    private Integer citizenshipTypeId;
    private String documentNumber;
    private String documentSeries;
    private String documentType;
    private Short genderId;
    private String inn;
    private Boolean isJuridic;
    private String memberType;
    private Integer memberTypeId;
    private String organizationName;
    private String pinpp;
    private String rights;
}
