package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.dict.Organ;

public class UserLimitByPinppExceeded extends ValidationException {

    public UserLimitByPinppExceeded(Organ organ, long limitValue, long countOfUsers, String pinpp) {
        super(
                ErrorCode.USER_LIMIT_BY_PINPP_EXCEEDED,
                String.format("Organ(%s) has limit = %s for active user accounts per pinpp. Pinpp %s already has %s active accounts",
                        organ.getId(),
                        limitValue,
                        pinpp,
                        countOfUsers)
        );
    }
}
