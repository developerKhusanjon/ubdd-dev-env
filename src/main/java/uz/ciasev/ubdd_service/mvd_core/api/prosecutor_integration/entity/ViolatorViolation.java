package uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.entity;


import lombok.Data;
import org.hibernate.annotations.Immutable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Immutable
@Entity(name = "django_prosecutor_protocol_view")
public class ViolatorViolation implements Serializable {

    @Id
    Long admCaseId;
    String admCaseNumber;
    String documentSeries;
    String documentNumber;
    String violatorPinpp;
    Date admCaseDate;
    String admCaseProtocolSeries;
    String admCaseProtocolNumber;
    Date admCaseProtocolDateTime;
    String admCaseDecisionSeries;
    String admCaseDecisionNumber;
    Date admCaseDecisionDateTime;
    Long decisionId;
    String admCaseDecisionArticle;
    Long bodyId;
    Long branchId;
    Long regionId;
    Long districtId;
    String officerTitle;
    String officerRank;
    String officerFio;
    String officerPinpp;
    String admCaseDescription;
    Long admCaseClassArticle;
    String mainPunishmentType;
    String punishment;
    Long amount;
    Long paidAmount;
    Date lastPayTime;
    String invoiceSerial;
    String mibSendStatus;
    String courtSendStatus;
    String admCaseStatus;
    Long personId;
    Long protocolId;
    String extraFilesUrl;

    String violatorInfo;
    String victimInfo;

    public ViolatorViolationResponse toResponse() {

        ViolatorViolationResponse response = new ViolatorViolationResponse();

        response.setAdmCaseId(admCaseId);
        response.setAdmCaseNumber(admCaseNumber);
        response.setDocumentSeries(documentSeries);
        response.setDocumentNumber(documentNumber);
        response.setViolatorPinpp(violatorPinpp);
        response.setAdmCaseDate(admCaseDate);
        response.setAdmCaseProtocolSeries(admCaseProtocolSeries);
        response.setAdmCaseProtocolNumber(admCaseProtocolNumber);
        response.setAdmCaseProtocolDateTime(admCaseProtocolDateTime);
        response.setAdmCaseDecisionSeries(admCaseDecisionSeries);
        response.setAdmCaseDecisionNumber(admCaseDecisionNumber);
        response.setAdmCaseDecisionDateTime(admCaseDecisionDateTime);
        response.setDecisionId(decisionId);
        response.setAdmCaseDecisionArticle(admCaseDecisionArticle);
        response.setBodyId(bodyId);
        response.setBranchId(branchId);
        response.setRegionId(regionId);
        response.setDistrictId(districtId);
        response.setOfficerTitle(officerTitle);
        response.setOfficerRank(officerRank);
        response.setOfficerFio(officerFio);
        response.setOfficerPinpp(officerPinpp);
        response.setAdmCaseDescription(admCaseDescription);
        response.setAdmCaseClassArticle(admCaseClassArticle);
        response.setMainPunishmentType(mainPunishmentType);
        response.setPunishment(punishment);
        response.setAmount(amount);
        response.setPaidAmount(paidAmount);
        response.setLastPayTime(lastPayTime);
        response.setInvoiceSerial(invoiceSerial);
        response.setMibSendStatus(mibSendStatus);
        response.setCourtSendStatus(courtSendStatus);
        response.setAdmCaseStatus(admCaseStatus);
        response.setPersonId(personId);
        response.setProtocolId(protocolId);
        response.setExtraFilesUrl(extraFilesUrl);
        response.setViolatorInfo(null);
        response.setVictimInfo(null);

        return response;
    }


}
