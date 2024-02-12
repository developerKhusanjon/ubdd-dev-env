package uz.ciasev.ubdd_service.entity.dict.admcase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@AllArgsConstructor
public enum AdmCaseMovementStatusAlias implements BackendAlias {
    SENT(1L),
    DECLINED(2L),
    CANCELLED(3L);

    private final long id;

    public static AdmCaseMovementStatusAlias getInstanceById(Long id) {
        if (id == 1) {
            return SENT;
        } else if (id == 2) {
            return DECLINED;
        } else if (id == 3) {
            return CANCELLED;
        }
        throw new NotImplementedException(String.format("Enum value in AdmCaseMovementStatusAlias for id %s not present", id));
    }
}
