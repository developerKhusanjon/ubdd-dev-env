package uz.ciasev.ubdd_service.controller_ubdd;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ciasev.ubdd_service.dto.ubdd.UbddInvoiceRequest;
import uz.ciasev.ubdd_service.mvd_core.api.billing.dto.BillingPaymentDTO;
import uz.ciasev.ubdd_service.service.execution.BillingExecutionService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${mvd-ciasev.url-v0}/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class UbddPaymentController {

    private final BillingExecutionService billingExecutionService;

    @PostMapping
    public void save(@RequestBody @NotNull @Valid BillingPaymentDTO request) {
        billingExecutionService.handlePayment(request);
    }


}