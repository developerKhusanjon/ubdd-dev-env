package uz.ciasev.ubdd_service.exception.mib;

import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

import java.util.Optional;

public class MibCardMovementNotAwaitExecution extends ValidationException {

    public MibCardMovementNotAwaitExecution(MibCardMovement movement) {
        super(
                ErrorCode.MIB_EXECUTION_CARD_NOT_AWAIT_EXECUTION,
                String.format(
                        "Current card statuses: sendStatus = %s, mibCaseStatus = %s(%s)",
                        movement.getSendStatusId(),
                        movement.getMibCaseStatusId(),
                        Optional.ofNullable(movement.getMibCaseStatus()).map(MibCaseStatus::getAlias).orElse(null)
                )
        );
    }
}
