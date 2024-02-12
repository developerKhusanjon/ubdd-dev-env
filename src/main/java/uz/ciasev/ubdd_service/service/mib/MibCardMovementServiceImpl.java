package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement_;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.mib.MibCardMovementRepository;
import uz.ciasev.ubdd_service.specifications.MibMovementSpecifications;
import uz.ciasev.ubdd_service.utils.PageUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MibCardMovementServiceImpl implements MibCardMovementService {

    private final MibCardMovementRepository repository;
    private final MibMovementSpecifications specifications;

    @Override
    @Transactional
    public MibCardMovement create(@Nullable User user, MibExecutionCard card, MibCardMovement movement) {

        movement.setCard(card);
        if (user != null && user.getId() != null) {
            movement.setUser(user);
        }

        movement.setIsFirst(!repository.existsByCardId(card.getId()));

        movement.setActive(true);
        repository.setActiveToFalseForAllByCardId(card);
        return repository.saveAndFlush(movement);
    }

    @Override
    public MibCardMovement update(MibCardMovement movement) {
        return repository.save(movement);
    }

    @Override
    @Transactional
    public void validateActiveForHandelExecutionStatus(MibCardMovement movement) {

        if (movement.isActive()) {
            return;
        }

        Optional<MibCardMovement> activeMovementOpt = repository.getActiveByCard(movement.getCard());

        if (activeMovementOpt.isEmpty()) {
            movement.setActive(true);
            repository.saveAndFlush(movement);
            return;
        }

        MibCardMovement activeMovement = activeMovementOpt.get();

        if (MibSendStatus.isSuccessfully(activeMovement.getSendStatusId())) {
            throw new ValidationException(ErrorCode.MIB_MOVEMENT_IS_NOT_ACTIVE);
        }

        repository.setActiveToFalseForAllByCardId(movement.getCard());
        movement.setActive(true);
        repository.saveAndFlush(movement);
    }

    @Override
    public boolean existsByMibRequestId(Long mibRequestId) {
        return repository.existsByMibRequestId(mibRequestId);
    }

    @Override
    public MibCardMovement getByMibRequestId(Long requestId) {
        return repository.findByMibRequestId(requestId)
                .orElseThrow(() -> new EntityByParamsNotFound(MibCardMovement.class, "envelopeId", requestId));
    }

    public MibCardMovement getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(MibCardMovement.class, id));
    }

    @Override
    public Optional<MibCardMovement> findCurrentByCard(MibExecutionCard mibExecutionCard) {
        return repository.getActiveByCard(mibExecutionCard);
    }

    @Override
    public Optional<MibCardMovement> findLastSuccessfullySentByCard(MibExecutionCard mibExecutionCard) {
        return repository.findAll(
                specifications.withCardId(mibExecutionCard.getId()).and(specifications.withMibSendStatusId(MibSendStatus.SUCCESSFULLY_ID)),
                PageUtils.oneWithMaxId()
        ).stream().findFirst();
    }

    @Override
    public List<MibCardMovement> findAllByCardId(Long cardId) {
        return repository.findAllByCardId(cardId, Sort.by(MibCardMovement_.id.getName()).descending());
    }

    @Override
    public MibCardMovement getCurrentByCard(MibExecutionCard mibExecutionCard) {
        return findCurrentByCard(mibExecutionCard)
                .orElseThrow(() -> new EntityByParamsNotFound(MibCardMovement.class, "mibExecutionCard", mibExecutionCard.getId()));
    }

    @Override
    @Transactional
    public void setSynced(Long mibRequestId, Long envelopeId) {
        repository.setSyncByMibRequestId(mibRequestId, envelopeId);
    }
}
