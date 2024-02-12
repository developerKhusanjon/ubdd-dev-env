package uz.ciasev.ubdd_service.mvd_core.api.billing.dto.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BillingWebhookRequestDTO {

    @NotNull(message = "type field required")
    private String type;

    @NotNull(message = "data field required")
    private JsonNode data;
}
