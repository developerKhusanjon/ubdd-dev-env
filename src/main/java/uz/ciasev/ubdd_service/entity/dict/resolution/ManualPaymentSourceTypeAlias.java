package uz.ciasev.ubdd_service.entity.dict.resolution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@RequiredArgsConstructor
public enum ManualPaymentSourceTypeAlias implements BackendAlias {
    MIGRATION(2),
    EXTERNAL_SYSTEM(4),
    ADMIN_ENTRY(1),
    MIB_EXECUTION(3);

    private final long id;

    public static ManualPaymentSourceTypeAlias getInstanceById(Long id) {
        if (id == 1) {
            return ADMIN_ENTRY;
        } else if (id == 2) {
            return MIGRATION;
        } else if (id == 4) {
            return EXTERNAL_SYSTEM;
        } else if (id == 3) {
            return MIB_EXECUTION;
        }
        throw new NotImplementedException(String.format("Enum value in ManualPaymentSourceTypeAlias for id %s not present", id));
    }
}
