package uz.ciasev.ubdd_service.entity.dict.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.BackendAlias;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

@Getter
@RequiredArgsConstructor
public enum CitizenshipCalculatingModeAlias implements BackendAlias {
    FOREVER_UZBEK(1L),
    FOREVER_FOREIGN(2L),
    FOREVER_STATELESS(3L),
    DEPEND_ON_GIVEN_PLACE(4L),
    CALCULATION_UNACCEPTABLE(5L),
    FOREVER_UNKNOWN(6L);

    private final long id;

    public static CitizenshipCalculatingModeAlias getInstanceById(Long id) {
        if (id == 1L) {
            return FOREVER_UZBEK;
        } else if (id == 2L) {
            return FOREVER_FOREIGN;
        } else if (id == 3L) {
            return FOREVER_STATELESS;
        } else if (id == 4L) {
            return DEPEND_ON_GIVEN_PLACE;
        } else if (id == 5L) {
            return CALCULATION_UNACCEPTABLE;
        } else if (id == 6L) {
            return FOREVER_UNKNOWN;
        }
        throw new NotImplementedException(String.format("Enum value in CitizenshipCalculatingModeAlias for id %s not present", id));
    }
}
