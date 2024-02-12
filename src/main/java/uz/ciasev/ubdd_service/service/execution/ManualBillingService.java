package uz.ciasev.ubdd_service.service.execution;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.ManualPaymentRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.PaymentData;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface ManualBillingService {

    void replaceMibPayments(Punishment punishment, List<PaymentData> payments);

    void deleteMibPayments(Punishment punishment);

    void createAdminPayment(User user, BillingEntity billingEntity, ManualPaymentRequestDTO dto);

    boolean hasAdminPayment(BillingEntity punishment);

    void deleteAdminPayment(User user, Decision decision, BillingEntity billingEntity);

//    void deleteAdminPayment(User user, Decision decision, Punishment punishment);
//
//    void deleteAdminPayment(User user, Decision decision, Compensation compensation);
//
//    void createAdminPayment(User user, Punishment punishment, ManualPaymentRequestDTO dto);
//
//    void createAdminPayment(User user, Compensation compensation, ManualPaymentRequestDTO dto);
}
