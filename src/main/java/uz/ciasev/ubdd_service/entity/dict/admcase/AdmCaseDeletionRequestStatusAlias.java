package uz.ciasev.ubdd_service.entity.dict.admcase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@AllArgsConstructor
public enum AdmCaseDeletionRequestStatusAlias implements BackendAlias {
    CREATED(1L),
    APPROVED(2L),
    DECLINED(3L),
    APPROVE_CANCELED(4L);

    private final long id;

    public static AdmCaseDeletionRequestStatusAlias getInstanceById(Long id) {
        if (id == 1) {
            return CREATED;
        } else if (id == 2) {
            return APPROVED;
        } else if (id == 3) {
            return DECLINED;
        } else if (id == 4) {
            return APPROVE_CANCELED;
        }
        throw new NotImplementedException(String.format("Enum value in AdmCaseDeletionRequestStatusAlias for id %s not present", id));
    }
}
