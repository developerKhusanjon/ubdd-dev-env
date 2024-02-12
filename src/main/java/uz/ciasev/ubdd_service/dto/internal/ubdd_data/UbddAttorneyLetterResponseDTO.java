package uz.ciasev.ubdd_service.dto.internal.ubdd_data;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddAttorneyLetterData;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UbddAttorneyLetterResponseDTO {

    private Long id;
    private LocalDateTime createdTime;
    private LocalDateTime editedTime;
    private Long userId;

    private String attorneyType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String notaryCode;
    private String notaryName;
    private String notaryOrganization;
    private String number;
    private LocalDate registrationDate;
    private LocalDateTime registrationTime;
    private JsonNode member;
    private JsonNode subject;

    private String blankNumber;

    public UbddAttorneyLetterResponseDTO(UbddAttorneyLetterData data) {

        this.id = data.getId();
        this.createdTime = data.getCreatedTime();
        this.editedTime = data.getEditedTime();
        this.userId = data.getUserId();
        this.attorneyType = data.getAttorneyType();
        this.fromDate = data.getFromDate();
        this.toDate = data.getToDate();
        this.notaryCode = data.getNotaryCode();
        this.notaryName = data.getNotaryName();
        this.notaryOrganization = data.getNotaryOrganization();
        this.number = data.getNumber();
        this.registrationDate = data.getRegistrationDate();
        this.registrationTime = data.getRegistrationTime();
        this.member = data.getMember();
        this.subject = data.getSubject();
        this.blankNumber = data.getBlankNumber();
    }
}
