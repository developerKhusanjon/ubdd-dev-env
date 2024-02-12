package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.mib.MibCardDocumentRequestDTO;
import uz.ciasev.ubdd_service.entity.mib.MibCardDocument;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.mib.MibCardDocumentRepository;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class MibCardDocumentServiceImpl implements MibCardDocumentService {

    private final MibCardService cardService;
    private final MibCardDocumentRepository documentRepository;
    private final MibValidationService validationService;
    private final HistoryService historyService;

    @Override
    public MibCardDocument createDocument(User user, Long cardId, MibCardDocumentRequestDTO requestDTO) {

        MibExecutionCard card = cardService.getCardForEdit(user, cardId);

        return createDocument(card, requestDTO, user);
    }

    private MibCardDocument createDocument(MibExecutionCard card, MibCardDocumentRequestDTO requestDTO, @Nullable User user) {

        MibCardDocument document = requestDTO.buildMibCardDocument();
        document.setCard(card);
        document.setUser(user);

        return documentRepository.save(document);
    }

    @Override
    public MibCardDocument updateDocument(User user, Long documentId, MibCardDocumentRequestDTO requestDTO) {
        MibCardDocument document = getDocumentForEdit(user, documentId);
        return documentRepository.save(requestDTO.applyTo(document));
    }

    @Override
    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.ADM_CASE_DOCUMENT_DELETE)
    public MibCardDocument deleteDocument(User user, Long documentId) {
        MibCardDocument document = getDocumentForEdit(user, documentId);

        historyService.registerMibCardDocumentRemove(document);
        documentRepository.delete(document);

        return document;
    }

    @Override
    public List<MibCardDocument> findAllAttachableDocumentsByCard(MibExecutionCard card) {
        return documentRepository.findAttachableByCard(card);
    }

    @Override
    public List<MibCardDocument> findAllDocumentsByCardId(Long cardId) {
        return documentRepository.findByCardId(cardId);
    }

    public MibCardDocument getById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(MibCardDocument.class, id));
    }

    private MibCardDocument getDocumentForEdit(User user, Long documentId) {
        MibCardDocument document = getById(documentId);
        cardService.getCardForEdit(user, document.getCardId());
        return document;
    }
}