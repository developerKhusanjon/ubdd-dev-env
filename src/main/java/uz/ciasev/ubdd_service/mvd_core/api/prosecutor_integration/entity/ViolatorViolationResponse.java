package uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class ViolatorViolationResponse implements Serializable {

    @JsonProperty("pRequestId")
    Long pRequestId;
    Long answerId;

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




}
