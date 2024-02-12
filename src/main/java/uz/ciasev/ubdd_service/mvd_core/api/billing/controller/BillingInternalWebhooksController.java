package uz.ciasev.ubdd_service.mvd_core.api.billing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uz.ciasev.ubdd_service.mvd_core.api.billing.service.BillingPaymentApiService;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.webhook.BillingWebhookRequestDTO;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(path = "${mvd-ciasev.webhooks.base-url}/billing", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Profile("emi-api")
public class BillingInternalWebhooksController {

    private final BillingPaymentApiService paymentApiService;

    @PostMapping("/payments")
    public void acceptPayment(@RequestBody @Valid BillingWebhookRequestDTO requestDTO) {
        paymentApiService.acceptPayment(requestDTO);
    }
 }
