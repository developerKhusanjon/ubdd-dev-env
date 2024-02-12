package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionStatusDocument;
import uz.ciasev.ubdd_service.repository.mib.MibExecutionStatusDocumentRepository;

@Service
@RequiredArgsConstructor
public class MibExecutionStatusDocumentServiceImpl implements MibExecutionStatusDocumentService {

    private final MibExecutionStatusDocumentRepository repository;

    @Override
    public MibExecutionStatusDocument create(MibCardMovement cardMovement, MibCaseStatus executionStatus, String fileUri) {

        MibExecutionStatusDocument rsl = new MibExecutionStatusDocument();

        rsl.setCardMovement(cardMovement);
        rsl.setExecutionStatus(executionStatus);
        rsl.setUri(fileUri);

        return repository.save(rsl);
    }

    @Override
    public Page<MibExecutionStatusDocument> findPageByCardId(Long cardId, Pageable pageable) {
        return repository.findAllByCardId(cardId, pageable);
    }
}
