package uz.ciasev.ubdd_service.service.execution;

import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.AdmEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BillingEntity extends AdmEntity {

    Long getId();

    InvoiceOwnerTypeAlias getInvoiceOwnerTypeAlias();

    boolean isExecuted();

    Long getAmount();

    Long getCurrentAmount();

    Long getPaidAmount();

    LocalDateTime getLastPayTime();

    default LocalDate getLastPayDate() {
        LocalDateTime lastPayTime = getLastPayTime();
        if (lastPayTime == null) return null;
        return lastPayTime.toLocalDate();
    }

    void setPaidAmount(Long value);

    void setLastPayTime(LocalDateTime value);

    default Long getAmountForPayLeft() {
        if (isExecuted()) {
            return 0L;
        }

        long diff = getCurrentAmount() - getPaidAmount();
        if (diff < 0) {
            return 0L;
        }
        return diff;
    }

//    Long getActualAmountForDate(LocalDate value);
//
//    default Long getAmountForPayLeft() {
//        Long diff = getActualAmountForDate(LocalDate.now()) - getPaidAmount();
//        if (diff < 0) {
//            return 0L;
//        }
//        return diff;
//    }

}
