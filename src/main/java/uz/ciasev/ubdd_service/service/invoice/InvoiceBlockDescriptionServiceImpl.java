package uz.ciasev.ubdd_service.service.invoice;

import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.invoice.InvoiceDeactivateReasonAlias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceBlockDescriptionServiceImpl implements InvoiceBlockDescriptionService {

    private final Map<InvoiceDeactivateReasonAlias, String> descriptions = new HashMap<>();

    public InvoiceBlockDescriptionServiceImpl() {
        descriptions.put(InvoiceDeactivateReasonAlias.FULLY_PAID, fullyPaid());
        descriptions.put(InvoiceDeactivateReasonAlias.RESOLUTION_CANCELED, resolutionCanceled());
        descriptions.put(InvoiceDeactivateReasonAlias.DECISION_SEND_TO_MIB, decisionSendToMib());
        descriptions.put(InvoiceDeactivateReasonAlias.DECISION_IN_REVIEW, decisionInReview());
        descriptions.put(InvoiceDeactivateReasonAlias.MANUAL_PAYMENT_EXECUTION, manualPaymentExecution());
        descriptions.put(InvoiceDeactivateReasonAlias.ADMIN_ACTION, adminAction());
    }

    @Override
    public String getDescriptionByReason(InvoiceDeactivateReasonAlias reason, List<Object> params) {

        String rsl = descriptions.getOrDefault(reason, "");
        for (Object p : params) {
            rsl = rsl.replaceFirst("##", String.valueOf(p));
        }
        return rsl;
    }

    private String fullyPaid() {
        return "Invoys to'liq to'langan";
    }

    private String resolutionCanceled() {
        return "Invoys bekor qilingan";
    }

    private String decisionSendToMib() {
        return "Invoys MIBga yuborilgan. Tulash uchun ##";
    }

    private String decisionInReview() {
        return "Invoys bekor qilingan - sudda ko'rib chiqilmoqda";
    }

    private String manualPaymentExecution() {
        return "Invoys to'liq to'langan";
    }

    private String adminAction() {return "##";}
}
