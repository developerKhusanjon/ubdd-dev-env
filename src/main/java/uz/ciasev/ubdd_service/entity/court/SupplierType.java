package uz.ciasev.ubdd_service.entity.court;

import uz.ciasev.ubdd_service.entity.invoice.InvoiceOwnerTypeAlias;

public enum SupplierType {

    PENALTY(1L),
    DAMAGE(2L);

    private final Long value;

    SupplierType(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static Long getValueFor(InvoiceOwnerTypeAlias invoiceOwnerType) {
        if (invoiceOwnerType.equals(InvoiceOwnerTypeAlias.PENALTY)) {
            return PENALTY.getValue();
        } else if (invoiceOwnerType.equals(InvoiceOwnerTypeAlias.COMPENSATION)) {
            return DAMAGE.getValue();
        } else {
            return null;
        }
    }
}
