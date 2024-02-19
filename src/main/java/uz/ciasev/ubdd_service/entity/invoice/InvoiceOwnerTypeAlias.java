package uz.ciasev.ubdd_service.entity.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@AllArgsConstructor
public enum InvoiceOwnerTypeAlias {
    PENALTY(1L, true), COMPENSATION(2L, true), DAMAGE(3L, false);

    private final long id;
    private final boolean isAggregatable;

    public static InvoiceOwnerTypeAlias getInstanceById(Integer id) {
        if (id == 1L) {
            return PENALTY;
        } else if (id == 2L) {
            return COMPENSATION;
        } else if (id == 3L) {
            return DAMAGE;
        }
        return PENALTY;
    }

}
