package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.mib.PaymentData;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.deserializer.dict.MibCaseStatusCacheDeserializer;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MibRequestDTO {

    private Long envelopeId;

    private Long offenseId;

    @JsonProperty("work_number")
    private String executionCaseNumber;

    @JsonProperty("docStatus")
    @JsonDeserialize(using = MibCaseStatusCacheDeserializer.class)
    @NotNull(message = ErrorCode.MIB_DOCUMENT_STATUS_REQUIRED)
    private MibCaseStatus executionCaseStatus;

    @JsonProperty("payments")
    private List<MibPaymentDTO> payments;

    @JsonProperty("doc_article_file")
    private MibDocumentDTO document;

    public List<MibPaymentDTO> getPayments() {
        return payments == null
                ? List.of()
                : payments;
    }

    public List<PaymentData> buildPaymentsData() {
        return this.getPayments()
                .stream()
                .map(MibPaymentDTO::buildPaymentData)
                .collect(Collectors.toList());
    }
}
