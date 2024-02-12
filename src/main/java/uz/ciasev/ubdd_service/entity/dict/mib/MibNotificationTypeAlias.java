package uz.ciasev.ubdd_service.entity.dict.mib;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@RequiredArgsConstructor
public enum MibNotificationTypeAlias implements BackendAlias {
    SMS(1L),
    MAIL(2L),
    MANUAL(3L);

    private final long id;

    public static MibNotificationTypeAlias getInstanceById(Long id) {
        if (id == 1L) {
            return SMS;
        } else if (id == 2L) {
            return MAIL;
        } else if (id == 3L) {
            return MANUAL;
        }
        throw new NotImplementedException(String.format("Enum value in MibNotificationTypeAlias for id %s not present", id));
    }
}
