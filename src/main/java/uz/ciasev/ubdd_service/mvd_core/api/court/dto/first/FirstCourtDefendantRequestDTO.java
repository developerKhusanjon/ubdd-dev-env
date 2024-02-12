package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FirstCourtDefendantRequestDTO implements FirstCourtActorRequestDTO {

    private Long violatorId;

    private Long pinpp;
    private String defendantLastName;
    private String defendantFirstName;
    private String defendantMiddleName;
    private String defendantLastNameKir;
    private String defendantFirstNameKir;
    private String defendantMiddleNameKir;

    private Long defendantBirthRegion;
    private Long defendantBirthDistrict;
    private Long defendantBirthCountry;

    private String defendantMobile;
    private Long defendantHealth;
    private Long defendantEducation;
    private Long defendantNationality;
    private Long defendantGender;
    private Long defendantCitizenship;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate defendantBirthdate;

    private Long defendantMaritalStatus;
    private Long childrenQty;
    private Long defendantConvictedBefore;

    private Long defendantRegion;
    private String defendantAddress;
    private String defendantCurrentAddress;

    private Long defendantDocType;
    private String defendantPassportSeries;
    private String defendantPassportNumber;
    private String defendantWorkplace;
    private String defendantPosition;
    private Long defendantOccupation;
    private Long defendantAge;

    private Boolean isOfficial;
    private String inn;
    private String organizationName;
    private Long protocolMtpId;
    private Long protocolDistrictId;

    private Long amountFixedPenalty;
    private Long amountPaidPenalty;
    private Long amountPaidDamage;

    private List<FirstCourtEarlyArticleRequestDTO> convictedBeforeArticles;
    private List<FirstCourtArticleRequestDTO> articles;
    private List<FirstCourtCauseDamageRequestDTO> claimCausedDamage;

    @Override
    public void setId(Long value) {
        this.violatorId = value;
    }

    @Override
    public void setLastName(String value) {
        this.defendantLastName = value;
    }

    @Override
    public void setFirstName(String value) {
        this.defendantFirstName = value;
    }

    @Override
    public void setMiddleName(String value) {
        this.defendantMiddleName = value;
    }

    @Override
    public void setLastNameKir(String value) {
        this.defendantLastNameKir = value;
    }

    @Override
    public void setFirstNameKir(String value) {
        this.defendantFirstNameKir = value;
    }

    @Override
    public void setMiddleNameKir(String value) {
        this.defendantMiddleNameKir = value;
    }

    @Override
    public void setBirthRegion(Long value) {
        this.defendantBirthRegion = value;
    }

    @Override
    public void setBirthDistrict(Long value) {
        this.defendantBirthDistrict = value;
    }

    @Override
    public void setBirthCountry(Long value) {
        this.defendantBirthCountry = value;
    }

    @Override
    public void setGender(Long value) {
        this.defendantGender = value;
    }

    @Override
    public void setCitizenship(Long value) {
        this.defendantCitizenship = value;
    }

    @Override
    public void setBirthdate(LocalDate value) {
        this.defendantBirthdate = value;
    }

    @Override
    public void setDistrict(Long value) {
        this.defendantRegion = value;
    }

    @Override
    public void setCurrentAddress(String value) {
        this.defendantCurrentAddress = value;
    }

    @Override
    public void setAge(Long value) {
        this.defendantAge = value;
    }
}