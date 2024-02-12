package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.ProtocolType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MibCardDecisionDTO {

    @JsonProperty("docContentId")
    private final int REQUEST_TYPE = 10;

    @JsonProperty("postStatus")
    private final int NOTIFICATION_STATUS = 0;

    @JsonProperty("documentId")
    private String mibRequestId;

    @JsonProperty("regionId")
    private Long mibRegionId;

    @JsonProperty("docType")
    private Integer protocolType;

    @JsonProperty("docDate")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime protocolTime;

    @JsonProperty("dateConsider")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime resolutionTime;

    @JsonProperty("courtRegion")
    private Long resolutionRegionId;

    @JsonProperty("org_id")
    private Long resolutionOrganId;

    @JsonProperty("fioConsider")
    private String considerName;

    @JsonProperty("positionConsider")
    private String considerPosition;

    @JsonProperty("protocolSeries")
    private String decisionSeries;

    @JsonProperty("protocolNumber")
    private String decisionNumber;

    @JsonProperty("protocolCreatName")
    private String protocolInspectorName;

    @JsonProperty("nameProtocolMain")
    private Long decisionArticlePartId;

    @JsonProperty("offenceDate")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate protocolViolationDate;

    @JsonProperty("plot")
    private String protocolFabula;

    @JsonProperty("amount")
    private Long penaltyAmount;

    @JsonProperty("dateSent")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate notificationSentDate;

    @JsonProperty("dataReceving")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate notificationReceiveDate;

    @JsonProperty("numSent")
    private String notificationNumber;

    @JsonProperty("content_mail")
    private String notificationText;

    @JsonProperty("files")
    private List<CardDocumentDTO> documents;

    @JsonProperty("debitor")
    private ViolatorRequestDTO violator;

    @JsonProperty("num_amt")
    private String autoGovNumber;

    @JsonProperty("auto_texpass_sn")
    private String autoTechnicalPassportSerial;

    @JsonProperty("auto_texpass_num")
    private String autoTechnicalPassportNumber;

    public void setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType.getValue();
    }
}
