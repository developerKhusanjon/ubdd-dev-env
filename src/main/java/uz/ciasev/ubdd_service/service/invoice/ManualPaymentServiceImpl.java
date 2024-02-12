package uz.ciasev.ubdd_service.service.invoice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.resolution.execution.ManualPayment;
import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceTypeAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.repository.resolution.execution.ManualPaymentRepository;
import uz.ciasev.ubdd_service.service.execution.BillingEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class ManualPaymentServiceImpl implements ManualPaymentService {

    private final ManualPaymentRepository manualPaymentRepository;

    @Override
    public ManualPayment create(ManualPayment manualPayment) {
        return manualPaymentRepository.saveAndFlush(manualPayment);
    }

    @Override
    public Collection<Long> findCompensationManualPaymentIdByViolator(Violator violator) {
        return manualPaymentRepository.findCompensationInvoiceIdByViolator(violator);
    }

    @Override
    public Collection<Long> findPunishmentManualPaymentIdByViolator(Violator violator) {
        return manualPaymentRepository.findPunishmentInvoiceIdByViolator(violator);
    }

    @Override
    public Collection<Long> findManualPaymentIdByDamageSettlementDetailId(Long damageSettlementDetailId) {
        return manualPaymentRepository.findByDamageSettlementDetailId(damageSettlementDetailId);
    }

    @Override
    public List<ManualPayment> findAllById(Collection<Long> ids) {
        return manualPaymentRepository.findAllById(ids);
    }

    @Override
    public Optional<ManualPayment> getLastPaymentForm(Collection<Long> ids) {
        return manualPaymentRepository.findTopByIdInOrderByPaymentDateDesc(ids);
    }

    @Override
    public Optional<Long> getTotalAmountForm(Collection<Long> ids) {
        return manualPaymentRepository.sumAmountByIds(ids);
    }

    @Override
    public List<User> getCreateUsers(Collection<Long> ids) {
        return manualPaymentRepository.findUniqueCreateUsersByIds(ids);
    }

    @Override
    @Transactional
    public void deleteByEntity(BillingEntity billingEntity, ManualPaymentSourceTypeAlias sourceType) {
        BiConsumer<Long, ManualPaymentSourceTypeAlias> consumer;

        switch (billingEntity.getInvoiceOwnerTypeAlias()) {
            case PENALTY: {
                consumer = manualPaymentRepository::deleteAllByPunishmentIdAndSourceTypeAlias;
                break;
            }
            case COMPENSATION: {
                consumer = manualPaymentRepository::deleteAllByCompensationIdAndSourceTypeAlias;
                break;
            }
            default: {
                throw new ImplementationException("Not manual payment not implemented for invoice owner type "+billingEntity.getInvoiceOwnerTypeAlias().name());
            }
        }

        consumer.accept(billingEntity.getId(), sourceType);
    }

    @Override
    public boolean existsByEntity(BillingEntity billingEntity, ManualPaymentSourceTypeAlias... sourceTypes) {
        BiFunction<Long, List<ManualPaymentSourceTypeAlias>, Long> suppler;

        switch (billingEntity.getInvoiceOwnerTypeAlias()) {
            case PENALTY: {
                suppler = manualPaymentRepository::countByPunishmentIdAndSourceTypeAliases;
                break;
            }
            case COMPENSATION: {
                suppler = manualPaymentRepository::countByCompensationIdAndSourceTypeAliases;
                break;
            }
            default: {
                throw new ImplementationException("Not manual payment not implemented for invoice owner type "+billingEntity.getInvoiceOwnerTypeAlias().name());
            }
        }

        return suppler.apply(billingEntity.getId(), List.of(sourceTypes)) != 0;
    }

//    @Override
//    @Transactional
//    public void deleteByPenaltyId(Long penaltyPunishmentId, ManualPaymentSourceTypeAlias sourceType) {
//
//        manualPaymentRepository.deleteAllByPenaltyPunishmentIdAndSourceTypeAlias(penaltyPunishmentId, sourceType);
//    }
//
//    @Override
//    public boolean existsByPenaltyId(Long penaltyPunishmentId, ManualPaymentSourceTypeAlias sourceType) {
//        return manualPaymentRepository.countByPenaltyPunishmentIdAndSourceTypeAlias(penaltyPunishmentId, sourceType) != 0L;
//    }
//
//    @Override
//    @Transactional
//    public void deleteByCompensationId(Long compensationId, ManualPaymentSourceTypeAlias sourceType) {
//
//        manualPaymentRepository.deleteAllByCompensationIdAndSourceTypeAlias(compensationId, sourceType);
//    }
//
//    @Override
//    public boolean existsByCompensationId(Long compensationId, ManualPaymentSourceTypeAlias sourceType) {
//        return manualPaymentRepository.countByCompensationIdAndSourceTypeAlias(compensationId, sourceType) != 0L;
//    }
}
