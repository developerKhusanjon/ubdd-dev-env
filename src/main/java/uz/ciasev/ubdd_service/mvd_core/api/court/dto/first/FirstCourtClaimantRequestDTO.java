package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FirstCourtClaimantRequestDTO implements FirstCourtActorRequestDTO {

    private Long victimId;
    private List<Long> protocolList;
    private Long pinpp;
    private String claimantLastName;
    private String claimantFirstName;
    private String claimantMiddleName;
    private String claimantLastNameKir;
    private String claimantFirstNameKir;
    private String claimantMiddleNameKir;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate claimantBirthdate;
    private Long claimantAge;
    private Long claimantCitizenship;
    private Long claimantGender;
    private Long claimantDocType;
    private String claimantPassportSeries;
    private String claimantPassportNumber;
    private String claimantMobile;
    private Long claimantBirthDistrict;
    private Long claimantBirthRegion;
    private Long claimantBirthCountry;
    private Long claimantLivingDistrict;
    private String claimantAddress;

    @Override
    public void setId(Long value) {
        this.victimId = value;
    }

    @Override
    public void setLastName(String value) {
        this.claimantLastName = value;
    }

    @Override
    public void setFirstName(String value) {
        this.claimantFirstName = value;
    }

    @Override
    public void setMiddleName(String value) {
        this.claimantMiddleName = value;
    }

    @Override
    public void setLastNameKir(String value) {
        this.claimantLastNameKir = value;
    }

    @Override
    public void setFirstNameKir(String value) {
        this.claimantFirstNameKir = value;
    }

    @Override
    public void setMiddleNameKir(String value) {
        this.claimantMiddleNameKir = value;
    }

    @Override
    public void setBirthRegion(Long value) {
        this.claimantBirthRegion = value;
    }

    @Override
    public void setBirthDistrict(Long value) {
        this.claimantBirthDistrict = value;
    }

    @Override
    public void setBirthCountry(Long value) {
        this.claimantBirthCountry = value;
    }

    @Override
    public void setGender(Long value) {
        this.claimantGender = value;
    }

    @Override
    public void setCitizenship(Long value) {
        this.claimantCitizenship = value;
    }

    @Override
    public void setBirthdate(LocalDate value) {
        this.claimantBirthdate = value;
    }

    @Override
    public void setDistrict(Long value) {
        this.claimantLivingDistrict = value;
    }

    @Override
    public void setCurrentAddress(String value) {
        this.claimantAddress  = value;
    }

    @Override
    public void setAge(Long value) {
        this.claimantAge = value;
    }
}
