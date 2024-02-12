package uz.ciasev.ubdd_service.service.invoice;

import uz.ciasev.ubdd_service.entity.resolution.execution.ManualPayment;
import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceTypeAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.service.execution.BillingEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ManualPaymentService {

    ManualPayment create(ManualPayment manualPayment);

    Collection<Long> findCompensationManualPaymentIdByViolator(Violator violator);

    Collection<Long> findPunishmentManualPaymentIdByViolator(Violator violator);

    Collection<Long> findManualPaymentIdByDamageSettlementDetailId(Long damageSettlementDetailId);

    List<ManualPayment> findAllById(Collection<Long> ids);

    Optional<ManualPayment> getLastPaymentForm(Collection<Long> ids);

    Optional<Long> getTotalAmountForm(Collection<Long> ids);

    List<User> getCreateUsers(Collection<Long> ids);

    void deleteByEntity(BillingEntity billingEntity, ManualPaymentSourceTypeAlias sourceType);

    boolean existsByEntity(BillingEntity billingEntity, ManualPaymentSourceTypeAlias... sourceTypes);
}
