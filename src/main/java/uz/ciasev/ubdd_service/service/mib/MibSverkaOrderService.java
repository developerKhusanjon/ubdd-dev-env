package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiApplicationException;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.mib.MibSverkaSending;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.mib.MibSverkaSendingRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MibSverkaOrderService {
    private final MibSverkaSendingRepository repository;
    private final MibSverkaHandelService service;
    private final MibCardService cardService;

    public List<MibSverkaSending> getNext(Long n) {
        return repository.findUnpassed(PageUtils.top(n.intValue(), Sort.by("resolutionDate").descending())).getContent();
    }

    public Page<MibSverkaSending> findByDecisionId(Long decisionId, Pageable pageable) {
        return repository.findByDecisionId(decisionId, pageable);
    }

    @Transactional
    public void repass() {
        repository.repassApiError();
    }

    @Transactional
    public void addSendingForInvoice(String invoiceSerial) {
        List<String> invoices = List.of(invoiceSerial);
        repository.makeMibKVFinded(invoices);
        repository.setPenaltyStatus();
        repository.addDecisionInSverkaOrderByInvoiceSerial(invoices);

        repository.createMibExecutionCardWhereNotExist();
        repository.setCardIdWhereEmpty();

        repository.setEnvelopeIdFromMibFileWhereEmpty();
        repository.includeNewRowInOrder();
    }

    public List<String> getNewKVInvoice() {
         return repository.getNewGaiInvoiceSerialForSverka();
    }

    @Transactional
    public void addSendingForNewKVInvoice() {
        List<String> invoices = repository.getNewGaiInvoiceSerialForSverka();
        repository.makeMibKVFinded(invoices);
        repository.setPenaltyStatus();
        repository.addDecisionInSverkaOrderByInvoiceSerial(invoices);

        repository.createMibExecutionCardWhereNotExist();
        repository.setCardIdWhereEmpty();

        repository.setEnvelopeIdFromMibFileWhereEmpty();
        repository.includeNewRowInOrder();
    }

    @Transactional
    public void repassForDecisionInMib() {
        repository.repassSverkaForInMibStatus();
    }

    public String handel(Long id) {
        return handel(repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(MibSverkaSending.class, id)));
    }

    public String handel(MibSverkaSending sending) {
        String res = "Ok";

        try {

            service.handel(cardService.getById(sending.getCardId()), sending.getControlSerial(), sending.getControlNumber(), sending.getEnvelopedId());
            sending.setHasError(false);
            sending.setIsApiError(false);
            sending.setErrorText(null);
        } catch (MibApiApplicationException e) {
            sending.setHasError(true);
            sending.setIsApiError(true);
            sending.setErrorText(String.format("Exc: %s || Message: %s", e.getClass().getSimpleName(), e.getMessage()));
            res = e.getLocalizedMessage();
        } catch (Exception e) {
            log.error("MibSverka error for card {}", sending.getCardId(), e);
            sending.setHasError(true);
            sending.setIsApiError(false);
            sending.setErrorText(String.format("Exc: %s || Message: %s", e.getClass().getSimpleName(), e.getMessage()));
            res = e.getLocalizedMessage();
        }
        sending.setPassTime(LocalDateTime.now());
        sending.setPass(true);
        sending.incrementCount();

        repository.save(sending);

        return res;
    }

    @Transactional
    public MibSverkaSending createByManualRequest(MibExecutionCard card, String controlSerial, String controlNumber) {
        MibSverkaSending sending = new MibSverkaSending(card, controlSerial, controlNumber, null);
        return repository.save(sending);
    }





}
