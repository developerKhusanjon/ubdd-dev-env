package uz.ciasev.ubdd_service.dto.internal.request.publicapi;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookType;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PublicApiWebhookRequestDTO {

    @NotNull(message = ErrorCode.WEBHOOK_URL_REQUIRED)
    private String url;

    @NotNull(message = ErrorCode.WEBHOOK_TOKEN_REQUIRED)
    private String token;

    private List<PublicApiWebhookType> subscribeToTypes;

    public List<PublicApiWebhookType> getSubscribeToTypes() {
        if (subscribeToTypes == null) return List.of();
        return subscribeToTypes;
    }
}
