package uz.ciasev.ubdd_service.service.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.court.CourtInvoiceSending;
import uz.ciasev.ubdd_service.entity.court.CourtInvoiceType;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;
import uz.ciasev.ubdd_service.service.execution.BillingExecutionService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceActionService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Consumer;

import static uz.ciasev.ubdd_service.entity.court.CourtInvoiceType.COMPENSATION;
import static uz.ciasev.ubdd_service.entity.court.CourtInvoiceType.PENALTY;


/*
"ШТРАФ (mainPunishment = 1):
Квитанции НЕ БЫЛО:
1 - Получить номер инвойса от Ягоны (физ лицо)
2 - Сформировать pdf с квитанцией
3 - Вызвать метод № 4 (для отправки квитанции со штрафом в суд)

КВИТАНЦИЯ БЫЛА:
1 - если сумма нового штрафа не изменилась, или сумма нового штрафа меньше или равна сумме оплаты по предыдущей квитанции (т.е. сумма нового штрафа покрывается оплатами по предыдущей квиатнции), тогда:
1.1 - последнюю квитанцию ""перевесить"" на новое решение (со всеми оплатами)
1.2 - восстановить в Ягоне последнюю квитанцию, если она была заблокирована
1.3 - суд ничего отправлять не надо
1.4 - оплат по ней уже не будет

2 - если сумма нового штрафа изменилась и сумма нового штрафа больше суммы оплат по предыдущей квитанции (т.е. опталы по предыдущей квиатнции не покрывают сумму нового штрафа), тогда:
2.1 - последнюю квитанцию ""перевесить"" на новое решение (со всеми оплатами)
2.2 - восстановить в Ягоне последнюю квитанцию, если она была заблокирована
2.3 - изменить в Ягоне сумму последней квитанции по новому решению
2.4 - Сформировать pdf с квитанцией на новую сумму штрафа
2.5 - Вызвать метод № 4 (для отправки квитанции со штрафом в суд)


Примечание:
1. ""Последняя квитанция"" - квитанция, сформированная последней (по дате, по ID или другому признаку)
2. ""Перевесить"" - в квитанции изменить ссылку на решение (заменить на новое решение)
3. ""Восстановить"" - отправить сервисом в Ягону запрос на восстановление инвойса"
*/
@Service
@RequiredArgsConstructor
public class CourtInvoiceSendingHandleServiceImpl implements CourtInvoiceSendingHandleService {

    private final InvoiceActionService invoiceActionService;
    private final InvoiceService invoiceService;
    private final DecisionService decisionService;
    private final PunishmentService punishmentService;
    private final CompensationService compensationService;
    private final CourtPaymentService courtExecutionPaymentService;
    private final BillingExecutionService billingExecutionService;

    private final EnumMap<CourtInvoiceType, Consumer<CourtInvoiceSending>> map = new EnumMap<>(CourtInvoiceType.class);

    {
        map.put(PENALTY, this::handlePenalty);
        map.put(COMPENSATION, this::handleCompensation);
    }


    @Override
    @Transactional
    public void handle(CourtInvoiceSending invoiceSending) {

        Consumer<CourtInvoiceSending> handler = Optional.ofNullable(map.get(invoiceSending.getType()))
                .orElseThrow(() -> new NotImplementedException(String.format("Handel not implement for CourtInvoiceSending with type %s", invoiceSending.getType())));

        handler.accept(invoiceSending);
    }

    private void handlePenalty(CourtInvoiceSending invoiceSending) {
        Decision decision = decisionService.getById(invoiceSending.getTypeId());

        // Это не штраф, значит никакие операций с квитанцией не нужны
        if (decision.getPenalty().isEmpty()) {
            return;
        }

        if (invoiceSending.getResolutionId() == -1L) {
            generateNewPenaltyInvoice(decision);
        } else {
            boolean oldInvoiceNotFound = !updateOldPenaltyInvoice(decision, invoiceSending.getResolutionId());
            if (oldInvoiceNotFound) {
                if (invoiceSending.isOnlyMoveOld()) {
                    invoiceSending.addComment("У старого решения квитанции нет");
                } else {
                    generateNewPenaltyInvoice(decision);
                }
            }
        }

        billingExecutionService.calculateAndSetExecution(decision.getMainPunishment());
    }

    private void handleCompensation(CourtInvoiceSending invoiceSending) {
        Compensation compensation = compensationService.findById(invoiceSending.getTypeId());

        if (compensation.getVictimType().not(VictimTypeAlias.GOVERNMENT)) {
            return;
        }


        Decision decision = decisionService.getById(compensation.getDecisionId());

        if (invoiceSending.getResolutionId() != -1L) {
            updateOldCompensationInvoice(decision, compensation, invoiceSending.getResolutionId());
        } else {
            generateNewCompensationInvoice(decision, compensation);
        }

        billingExecutionService.calculateAndSetExecution(compensation);
    }

    private boolean updateOldPenaltyInvoice(Decision decision, Long inactiveResolutionId) {
        Optional<Decision> oldDecision = decisionService.findByResolutionAndViolatorIds(inactiveResolutionId, decision.getViolator().getId());
        Optional<Invoice> oldInvoiceOpt =  oldDecision.flatMap(invoiceService::findPenaltyInvoiceByDecision);

        boolean oldDecisionHasNoInvoice = oldInvoiceOpt.isEmpty();

        if (oldDecisionHasNoInvoice) {
//            generateNewPenaltyInvoice(decision);
            return false;
        }

        PenaltyPunishment newPenalty = decision.getMainPunishment().getPenalty();
        PenaltyPunishment oldPenalty = oldDecision.get().getMainPunishment().getPenalty();
        Invoice oldInvoice = oldInvoiceOpt.get();

        invoiceActionService.editOwner(oldInvoice, newPenalty);
        invoiceActionService.open(oldInvoice);

        if (isNotEquals(newPenalty.getAmount(), oldPenalty.getAmount())) {
            invoiceActionService.updateInvoiceAmount(oldInvoice, newPenalty);
            courtExecutionPaymentService.accept(decision, oldInvoice, null);
        }

        return true;
    }

    private void generateNewPenaltyInvoice(Decision decision) {
        Punishment mainPunishment = decision.getMainPunishment();

        Invoice invoice = invoiceService.findPenaltyInvoiceByDecision(decision)
                .orElseGet(() -> invoiceActionService.createForPenalty(decision));

//        punishmentService.setDiscount(mainPunishment, invoice);
        courtExecutionPaymentService.accept(decision, invoice, null);

    }

    private void updateOldCompensationInvoice(Decision decision, Compensation compensation, Long inactiveResolutionId) {
        Optional<Compensation> oldCompensationOpt = compensationService
                .findGovByViolatorId(inactiveResolutionId, decision.getViolatorId());

        boolean oldCompensationHasNoInvoice = oldCompensationOpt.map(Compensation::getInvoice).isEmpty();

        if (oldCompensationHasNoInvoice) {
            generateNewCompensationInvoice(decision, compensation);
            return;
        }

        Compensation oldCompensation = oldCompensationOpt.get();
        var oldInvoice = oldCompensation.getInvoice();

        invoiceActionService.editOwner(oldInvoice, compensation);
        invoiceActionService.open(oldInvoice);

        if (isNotEquals(compensation.getAmount(), oldCompensation.getAmount())) {
            invoiceActionService.updateInvoiceAmount(oldInvoice, compensation);
            courtExecutionPaymentService.accept(decision, oldInvoice, null);
        }

    }

    private void generateNewCompensationInvoice(Decision decision, Compensation compensation) {
        Invoice invoice = compensation.getInvoiceOptional()
                .orElseGet(() -> invoiceActionService.createForCompensation(compensation));

        courtExecutionPaymentService.accept(decision, invoice, null);
    }

    private boolean isNotEquals(Long newAmount, Long oldAmount) {
        return newAmount.compareTo(oldAmount) != 0;
    }
}
