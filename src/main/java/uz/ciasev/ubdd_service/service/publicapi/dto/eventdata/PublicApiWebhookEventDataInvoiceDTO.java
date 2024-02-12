package uz.ciasev.ubdd_service.service.publicapi.dto.eventdata;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicApiWebhookEventDataInvoiceDTO {

    private Long invoiceId;
    private Boolean active;
    private String deactivateReasonAlias;
    private String deactivateReasonText;
    private LocalDateTime deactivateTime;
}
