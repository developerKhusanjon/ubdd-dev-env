package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FirstCourtParticipantRequestDTO implements FirstCourtPersonRequestDTO {

    private Long participantType;
    private Long participantId;
    private List<Long> protocolList;
    private Long pinpp;
    private String participantLastName;
    private String participantFirstName;
    private String participantMiddleName;

    private String participantLastNameKir;
    private String participantFirstNameKir;
    private String participantMiddleNameKir;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate participantBirthdate;
    private Long participantDocType;
    private String participantPassportSeries;
    private String participantPassportNumber;
    private String participantMobile;
    private Long participantLivingDistrict;
    private String participantAddress;

    @Override
    public void setId(Long value) {
        this.participantId = value;
    }

    @Override
    public void setLastName(String value) {
        this.participantLastName = value;
    }

    @Override
    public void setFirstName(String value) {
        this.participantFirstName = value;
    }

    @Override
    public void setMiddleName(String value) {
        this.participantMiddleName = value;
    }

    @Override
    public void setLastNameKir(String value) {
        this.participantLastNameKir = value;
    }

    @Override
    public void setFirstNameKir(String value) {
        this.participantFirstNameKir = value;
    }

    @Override
    public void setMiddleNameKir(String value) {
        this.participantMiddleNameKir = value;
    }
    @Override
    public void setGender(Long value) {
    }

    @Override
    public void setCitizenship(Long value) {
    }

    @Override
    public void setBirthdate(LocalDate value) {
        this.participantBirthdate = value;
    }

    @Override
    public void setDistrict(Long value) {
        this.participantLivingDistrict = value;
    }

    @Override
    public void setCurrentAddress(String value) {
        this.participantAddress = value;
    }

    @Override
    public void setAge(Long value) {
    }
}