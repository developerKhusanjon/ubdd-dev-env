package uz.ciasev.ubdd_service.exception;

import org.springframework.http.HttpStatus;

import java.util.Set;

public class ViolatorAndDecisionNotConsistException extends ApplicationException {

    public ViolatorAndDecisionNotConsistException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.VIOLATORS_AND_DECISIONS_NOT_CONSIST);
    }

    public ViolatorAndDecisionNotConsistException(Set<Long> violatorsIdSet, Set<Long> decisionsIdSet) {
        super(
                HttpStatus.BAD_REQUEST,
                ErrorCode.VIOLATORS_AND_DECISIONS_NOT_CONSIST,
                String.format("Adm case contains violators %s. Decision presents for %s", violatorsIdSet, decisionsIdSet)
        );
    }
}
