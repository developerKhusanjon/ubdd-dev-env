package uz.ciasev.ubdd_service.mvd_core.api.billing.service;

import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.webhook.BillingWebhookRequestDTO;

public interface BillingPaymentApiService {

    void acceptPayment(BillingWebhookRequestDTO requestDTO);
}
