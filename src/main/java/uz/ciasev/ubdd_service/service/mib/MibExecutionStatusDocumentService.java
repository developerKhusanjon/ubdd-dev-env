package uz.ciasev.ubdd_service.service.mib;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionStatusDocument;

public interface MibExecutionStatusDocumentService {

    MibExecutionStatusDocument create(MibCardMovement cardMovement, MibCaseStatus executionStatus, String fileUri);
    Page<MibExecutionStatusDocument> findPageByCardId(Long cardId, Pageable pageable);
}
