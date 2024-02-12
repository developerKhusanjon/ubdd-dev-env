package uz.ciasev.ubdd_service.service.mib;

import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;

public interface MibExecutionService {

    Pair<Long, Long> getPaidDataForDecision(MibCardMovement movement);

    Pair<Long, Long> getPaidDataCompensation(MibCardMovement movement);


    Pair<Long, Long> registerDecisionInMib(MibCardMovement movement);

    Pair<Long, Long> registerCompensationInMib(MibCardMovement movement);


    void returnEvidenceFromMib(MibCardMovement movement);

    void returnDecisionFromMib(MibCardMovement movement);

    void returnCompensationFromMib(MibCardMovement movement);


    void executeEvidenceByMib(MibCardMovement movement);

    void executeDecisionByMib(MibCardMovement movement);

    void executeCompensationByMib(MibCardMovement movement);


    void cancelExecuteEvidenceByMib(MibCardMovement movement);

    void cancelExecuteDecisionByMib(MibCardMovement movement);

    void cancelExecuteCompensationByMib(MibCardMovement movement);
}
