package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data public class ReturnRequestApiDTO {

    @JsonProperty("envelopeId")
    private Long mibRequestId;

    @JsonProperty("protocolSeries")
    private String decisionSeries;

    @JsonProperty("protocolNumber")
    private String decisionNumber;

    @JsonProperty("return_reason_id")
    private String reasonId;

    @JsonProperty("return_reason")
    private String reasonName;

    @JsonProperty("return_comment")
    private String comment;

    @JsonProperty("user")
    private String inspectorInfo;

    @JsonProperty("document_File")
    private ReturnRequestFileApiDTO file;

}
