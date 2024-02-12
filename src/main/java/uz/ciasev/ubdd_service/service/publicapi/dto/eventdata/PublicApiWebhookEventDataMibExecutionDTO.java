package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.mib.PaymentData;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PublicApiWebhookEventDataMibExecutionDTO {

    private Long mibExecutionCardId;
    private Long regionId;
    private Long districtId;
    private LocalDateTime sendTime;
    private Long decisionId;
    private Long compensationId;
    private String mibCaseNumber;
    private Long mibCaseStatusId;
    private List<PaymentData> payments;
    private Long mibRequestId;
    private Long sendStatusId;
    private String sendMessage;
}
