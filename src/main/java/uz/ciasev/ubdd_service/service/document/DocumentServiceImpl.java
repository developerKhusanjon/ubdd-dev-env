package uz.ciasev.ubdd_service.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.document.DocumentCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.document.DocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.document.DocumentUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.IDResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.PersonListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.DocumentDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.InspectorResponseDTO;
import uz.ciasev.ubdd_service.entity.document.Document;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.document.DocumentRepository;
import uz.ciasev.ubdd_service.service.AdmCaseChangeReasonService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.service.file.FileFormatService;
import uz.ciasev.ubdd_service.service.person.PersonService;
import uz.ciasev.ubdd_service.service.user.UserDTOService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final AdmCaseAccessService admCaseAccessService;
    private final AdmCaseChangeReasonService changeReasonService;
    private final AdmCaseService admCaseService;
    private final PersonService personService;
    private final FileFormatService fileFormatService;
    private final UserDTOService userDTOService;

    @Override
    public Document create(User user, AdmCase admCase, Person person, DocumentRequestDTO documentRequestDTO) {

        Document document = documentRequestDTO.buildDocument();

        document.setUser(user);
        document.setAdmCase(admCase);
        document.setPerson(person);

        return save(document);
    }

    @Override
    public IDResponseDTO create(User user, DocumentCreateRequestDTO requestDTO) {

        AdmCase admCase = admCaseService.getById(requestDTO.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.ADD_DOCUMENT_TO_ADM_CASE, admCase);

        Person person = (requestDTO.getPersonId() != null) ? personService.findById(requestDTO.getPersonId()) : null;

        return new IDResponseDTO(create(user, admCase, person, requestDTO).getId());
    }

    @Override
    public Document update(User user, Long id, DocumentUpdateRequestDTO requestDTO) {

        Document document = findById(id);
        AdmCase admCase = admCaseService.getById(document.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_DOCUMENT, admCase);

        document.setPerson((requestDTO.getPersonId() != null) ? personService.findById(requestDTO.getPersonId()) : null);

        changeReasonService.create(user, admCase, document, requestDTO.getChangeReason());
        return save(requestDTO.applyTo(document));
    }

    @Override
    @DigitalSignatureCheck(event = SignatureEvent.ADM_CASE_DOCUMENT_DELETE)
    public Document delete(User user, Long id, ChangeReasonRequestDTO reasonRequestDTO) {

        Document document = findById(id);
        AdmCase admCase = admCaseService.getById(document.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.DELETE_DOCUMENT, admCase);

        changeReasonService.create(user, admCase, document, reasonRequestDTO);
        documentRepository.delete(document);

        return document;
    }

    public Document findById(Long id) {
        return documentRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Document.class, id));
    }

    public List<Document> findAllByAdmCaseId(Long admCaseId) {
        return documentRepository.findAllByAdmCaseId(admCaseId);
    }

    @Override
    public List<DocumentDetailResponseDTO> findByAdmCaseId(Long admCaseId) {
        return findAllByAdmCaseId(admCaseId)
                .stream()
                .map(document -> {
                    PersonListResponseDTO person = Optional.ofNullable(document.getPersonId()).map(personService::findDetailById).orElse(null);
                    InspectorResponseDTO user = Optional.ofNullable(document.getUserId()).map(userDTOService::findInspectorById).orElse(null);
                    return new DocumentDetailResponseDTO(document, person, user);
                })
                .collect(Collectors.toList());
    }

    @Override
    public DocumentDetailResponseDTO findDetailById(Long id) {
        return buildDetail(findById(id));
    }

    public List<Document> findScenePhotosByAdmCase(Long admCaseId) {
        return documentRepository.findByAdmCaseIdAndDocumentType(
                admCaseId,
                DocumentTypeAlias.SCENE_PHOTO,
                FileFormatAlias.getImages().stream().map(FileFormatAlias::name).collect(Collectors.toList()));
    }

    private Document save(Document document) {
        document.setFileFormat(fileFormatService.calcForUrl(document.getUrl()));
        return documentRepository.save(document);
    }

    private DocumentDetailResponseDTO buildDetail(Document document) {
        PersonListResponseDTO person = Optional.ofNullable(document.getPersonId()).map(personService::findDetailById).orElse(null);
        InspectorResponseDTO user = Optional.ofNullable(document.getUserId()).map(userDTOService::findInspectorById).orElse(null);
        return new DocumentDetailResponseDTO(document, person, user);
    }
}
