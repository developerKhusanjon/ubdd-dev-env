package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.entity.court.CourtInvoiceSending;

public interface CourtInvoiceSendingHandleService {
    void handle(CourtInvoiceSending invoiceSending);
}
