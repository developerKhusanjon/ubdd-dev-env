package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface MibCardMovementService {

    boolean existsByMibRequestId(Long mibRequestId);

    MibCardMovement getByMibRequestId(Long requestId);

    MibCardMovement getById(Long id);

    List<MibCardMovement> findAllByCardId(Long cardId);

    MibCardMovement getCurrentByCard(MibExecutionCard mibExecutionCard);

    Optional<MibCardMovement> findCurrentByCard(MibExecutionCard mibExecutionCard);

    Optional<MibCardMovement> findLastSuccessfullySentByCard(MibExecutionCard mibExecutionCard);

    MibCardMovement create(@Nullable User user, MibExecutionCard card, MibCardMovement movement);

    MibCardMovement update(MibCardMovement movement);

    void setSynced(Long mibRequestId, Long envelopeId);

    void validateActiveForHandelExecutionStatus(MibCardMovement movement);
}
