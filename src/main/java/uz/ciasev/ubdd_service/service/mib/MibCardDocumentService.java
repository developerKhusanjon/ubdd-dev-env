package uz.ciasev.ubdd_service.service.mib;

import uz.ciasev.ubdd_service.dto.internal.request.mib.MibCardDocumentRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.MibCardDocument;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface MibCardDocumentService {

    List<MibCardDocument> findAllDocumentsByCardId(Long cardId);

    List<MibCardDocument> findAllAttachableDocumentsByCard(MibExecutionCard card);

    MibCardDocument createDocument(User user, Long cardId, MibCardDocumentRequestDTO requestDTO);

    MibCardDocument updateDocument(User user, Long documentId, MibCardDocumentRequestDTO requestDTO);

    MibCardDocument deleteDocument(User user, Long documentId);

}
