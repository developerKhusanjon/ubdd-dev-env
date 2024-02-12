package uz.ciasev.ubdd_service.entity.dict.autocon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@RequiredArgsConstructor
public enum AutoconSendingStatusAlias implements BackendAlias {

    AWAIT_OPEN(1L),
    OPENED(2L),
    AWAIT_CLOSE(3L),
    CLOSED(4L),
    OPEN_CANCELED(5L);

    private final long id;

    public static AutoconSendingStatusAlias getInstanceById(Long id) {
        if (id == 1) {
            return AWAIT_OPEN;
        }
        if (id == 2) {
            return OPENED;
        }
        if (id == 3) {
            return AWAIT_CLOSE;
        }
        if (id == 4) {
            return CLOSED;
        }
        if (id == 5) {
            return OPEN_CANCELED;
        }
        throw new NotImplementedException(String.format("Enum value in AutoconSendingStatusAlias for id %s not present", id));
    }
}
