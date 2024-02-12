package uz.ciasev.ubdd_service.service.invoice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.billing.BillingClientApplicationException;
import uz.ciasev.ubdd_service.entity.court.BillingSending;
import uz.ciasev.ubdd_service.entity.court.BillingSendingErrorLog;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.repository.court.BillingSendingErrorLogRepository;
import uz.ciasev.ubdd_service.repository.court.BillingSendingRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingSendingServiceImpl implements BillingSendingService {

    private final BillingSendingErrorLogRepository logRepository;
    private final BillingSendingRepository billingSendingRepository;
    private final BillingSendingHandleService handleService;

    @Override
    public void save(BillingSending billingSending) {
        billingSendingRepository.save(billingSending);
    }

    @Override
    @Transactional
    public void saveAll(List<BillingSending> list) {
        billingSendingRepository.saveAll(list);
    }

//    @Override
//    public List<BillingSending> findUnsentInvoices(BillingAction action) {
//        return billingSendingRepository.findUnsentInvoice(action, PageUtils.limit(20));
//    }

    @Override
    public List<BillingSending> findNextInOrder() {
        return billingSendingRepository.findUnsentInvoice(PageUtils.limit(20));
    }

    @Override
    public void handle(BillingSending invoice) {
        while (!invoice.getIsSent()) {
            try {
                handleService.handle(invoice);
            } catch (Exception e) {
                log.error("BillingSending error for id {}", invoice.getId(), e);
                logRepository.save(new BillingSendingErrorLog(invoice, e));
                invoice.setHasError(true);
                invoice.setError(e.getMessage());
                billingSendingRepository.save(invoice);

                if (!(e instanceof ApplicationException)) {
                    throw e;
                }

                // почему шла повторная отрпавка тольок при ApplicationException, но не при BillingClientApplicationException
                if (!(e instanceof BillingClientApplicationException) || e.getMessage().contains("502")) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                    continue;
                }
            }
            invoice.setIsSent(true);
            billingSendingRepository.save(invoice);
        }
    }

    void handleOld(BillingSending invoice) {
        while (!invoice.getIsSent()) {
            try {
                handleService.handle(invoice);
            } catch (BillingClientApplicationException e) {
                invoice.setHasError(true);
                invoice.setError(e.getCode());
                billingSendingRepository.save(invoice);
            } catch (ApplicationException e) {
                log.error("BILLING ERROR SENDING INVOICE, ID:{}", invoice.getId(), e);
                invoice.setError(e.getCode());
                billingSendingRepository.save(invoice);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                continue;
            }
            invoice.setIsSent(true);
            billingSendingRepository.save(invoice);
        }
    }
}
