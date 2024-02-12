package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiApplicationException;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovementReturnRequest_;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.mib.MibCardMovementReturnRequestRepository;

import javax.annotation.Nullable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MibCardMovementReturnRequestServiceImpl implements MibCardMovementReturnRequestService {

    private final MibCardMovementReturnRequestRepository repository;

    @Override
    public List<MibCardMovementReturnRequest> findByCardId(Long cardId) {
        return repository.findAllByCardId(cardId, Sort.by(MibCardMovementReturnRequest_.ID).descending());
    }

    @Override
    public List<MibCardMovementReturnRequest> findByMovementId(Long movementId) {
        return repository.findAllByMovementId(movementId, Sort.by(MibCardMovementReturnRequest_.ID));
    }

    @Override
    public MibCardMovementReturnRequest create(@Nullable User user, MibCardMovement movement, MibCardMovementReturnRequest.CreateRequest request) {
        MibCardMovementReturnRequest returnRequest = new MibCardMovementReturnRequest(user, movement, request);
        return repository.save(returnRequest);
    }

    @Override
    public MibCardMovementReturnRequest saveSendResult(MibCardMovementReturnRequest request, MibSendStatus sendStatus, String sendMessage) {
        request.setSendResult(false, sendStatus.getId(), sendMessage);
        return repository.save(request);
    }

    @Override
    public MibCardMovementReturnRequest saveSendResult(MibCardMovementReturnRequest request, MibApiApplicationException sendError) {
        request.setSendResult(true, null, Strings.left(sendError.getMessage(), 255));
        return repository.save(request);
    }

    @Override
    public MibCardMovementReturnRequest getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(MibCardMovementReturnRequest.class, id));
    }
}
