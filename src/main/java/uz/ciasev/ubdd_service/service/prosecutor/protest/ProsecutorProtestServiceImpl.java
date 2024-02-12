package uz.ciasev.ubdd_service.service.prosecutor.protest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest.AbstractProsecutorProtestRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest.ProsecutorProtestCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest.ProsecutorProtestDocumentCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest.ProsecutorProtestUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.CancellationResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.resolution.OrganCancellation;
import uz.ciasev.ubdd_service.entity.dict.resolution.OrganCancellationAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocumentProjection;
import uz.ciasev.ubdd_service.entity.resolution.cancellation.CancellationResolution;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.prosecutor.protest.ProsecutorProtestDocumentRepository;
import uz.ciasev.ubdd_service.repository.prosecutor.protest.ProsecutorProtestRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;
import uz.ciasev.ubdd_service.service.dict.resolution.ReasonCancellationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionActionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.*;

@Service
@RequiredArgsConstructor
public class ProsecutorProtestServiceImpl implements ProsecutorProtestService {
    private final ProsecutorProtestRepository protestRepository;
    private final ProsecutorProtestDocumentRepository documentRepository;
    private final ResolutionService resolutionService;
    private final DecisionAccessService decisionAccessService;
    private final ResolutionActionService resolutionActionService;
    private final DecisionService decisionService;
    private final ValidationService validationService;
    private final ReasonCancellationService reasonCancellationService;
    private final AliasedDictionaryService<OrganCancellation, OrganCancellationAlias> organCancellationDictionaryService;
    private final AdmCaseAccessService admCaseAccessService;

    @Override
    @Transactional
    public ProsecutorProtest createProtest(User user, ProsecutorProtestCreateRequestDTO requestDTO) {

        Resolution resolution = resolutionService.getById(requestDTO.getResolutionId());

        validateProsecutorProtestCreation(user, resolution, requestDTO);

        ProsecutorProtest protest = requestDTO.buildProtest();
        protest.setUser(user);
        protest.setResolution(resolution);

        List<ProsecutorProtestDocument> documents = buildDocuments(user, protest, requestDTO.getDocuments());

        protestRepository.save(protest);
        documentRepository.saveAll(documents);

        return protest;
    }

    @Override
    public Page<ProsecutorProtest> findAllByResolutionId(Long resolutionId, Pageable pageable) {
        return protestRepository.findAllByResolutionId(resolutionId, pageable);
    }

    @Override
    public Page<Pair<ProsecutorProtestDocument, String>> findAllDocumentsByProtestId(Long protestId, Pageable pageable) {
        String protestNumber = getById(protestId).getNumber();

        return documentRepository.findAllByProtestId(protestId, pageable).map(document -> Pair.of(document, protestNumber));
    }

    @Override
    @Transactional
    public void acceptProtestById(User user, Long protestId) {

        ProsecutorProtest protest = getById(protestId);
        checkAccepted(protest);

        ReasonCancellation reasonCancellation = reasonCancellationService.getById(protest.getReasonId());
        ProsecutorProtestDocument protestDocument = documentRepository.findFirstByProtestIdOrderByCreatedTimeAsc(protestId);

        CancellationResolutionRequestDTO dto = new CancellationResolutionRequestDTO();
        dto.setOrganCancellation(organCancellationDictionaryService.getByAlias(OrganCancellationAlias.PROSECUTOR));
        dto.setReasonCancellation(reasonCancellation);
        dto.setSignature(null);
        dto.setCancellationTime(LocalDateTime.now());
        dto.setFileUri(protestDocument.getUri());

        CancellationResolution cancellation = resolutionActionService.cancelResolutionByProtest(user, protest.getResolutionId(), dto);

        protest.setAccepted(true);
        protest.setCancellationResolution(cancellation);
        protestRepository.save(protest);
    }

    @Override
    @Transactional
    public void updateProtestById(User user, Long id, ProsecutorProtestUpdateRequestDTO requestDTO) {
        ProsecutorProtest protest = getById(id);
        validateProsecutorProtestUpdate(user, protest, requestDTO);

        protest = requestDTO.applyTo(protest);
        protestRepository.save(protest);
    }

    @Override
    @Transactional
    public void addDocumentByProtestId(User user, Long protestId, ProsecutorProtestDocumentCreateRequestDTO requestDTO) {
        ProsecutorProtest protest = getById(protestId);
        Resolution resolution = resolutionService.getById(protest.getResolutionId());
        AdmCase admCase = resolution.getAdmCase();

        validateProtestChange(user, protest, admCase);
        ProsecutorProtestDocument document = new ProsecutorProtestDocument(user, protest, requestDTO.getUri());

        documentRepository.save(document);
    }

    @Override
    public Page<ProsecutorProtestDocumentProjection> findAllDocumentsByResolutionId(Long resolutionId, Pageable pageable) {
        return documentRepository.findAllByResolutionId(resolutionId, pageable);
    }

    @Override
    public ProsecutorProtest getById(Long id) {
        Optional<ProsecutorProtest> protestOptional = protestRepository.findById(id);

        return protestOptional.orElseThrow(() -> new EntityByIdNotFound(ProsecutorProtest.class, id));
    }

    private List<ProsecutorProtestDocument> buildDocuments(User user, ProsecutorProtest protest, List<String> uriList) {
        List<ProsecutorProtestDocument> documents = new ArrayList<>();

        for (String uri : uriList) {
            ProsecutorProtestDocument document = new ProsecutorProtestDocument(user, protest, uri);
            documents.add(document);
        }

        return documents;
    }

    private void validateProsecutorProtestCreation(User user, Resolution resolution, ProsecutorProtestCreateRequestDTO requestDTO) {
        Long resolutionId = requestDTO.getResolutionId();

        checkResolutionIdAndNumber(resolutionId, requestDTO.getNumber());

        List<Decision> decisions = decisionService.findByResolutionId(resolutionId);
        decisionAccessService.checkIsNotCourt(resolution);
        resolutionActionService.checkResolutionDecisions(user, decisions, CREATE_PROSECUTOR_PROTEST);

        validateProtestGeneralCase(resolution, requestDTO);
    }

    private void validateProsecutorProtestUpdate(User user, ProsecutorProtest protest, ProsecutorProtestUpdateRequestDTO requestDTO) {

        String protestNumber = protest.getNumber();
        String dtoNumber = requestDTO.getNumber();
        Long resolutionId = protest.getResolutionId();

        if (!protestNumber.equals(dtoNumber)) {
            checkResolutionIdAndNumber(resolutionId, dtoNumber);
        }

        Resolution resolution = resolutionService.getById(resolutionId);
        AdmCase admCase = resolution.getAdmCase();

        validateProtestChange(user, protest, admCase);
        validateProtestGeneralCase(resolution, requestDTO);
    }

    private void validateProtestChange(User user, ProsecutorProtest protest, AdmCase admCase) {
        checkAccepted(protest);
        admCaseAccessService.checkAccessOnAdmCase(user, admCase);
    }

    private void validateProtestGeneralCase(Resolution resolution, AbstractProsecutorProtestRequestDTO requestDTO) {

        if (validationService.checkRegionNotIn(requestDTO, resolution.getRegion())) {
            throw new ValidationException(ErrorCode.RESOLUTION_REGION_NOT_IN_PROSECUTOR_VISIBILITY);
        }

        if (validationService.checkDistrictNotIn(requestDTO, resolution.getDistrict())) {
            throw new ValidationException(ErrorCode.RESOLUTION_DISTRICT_NOT_IN_PROSECUTOR_VISIBILITY);
        }

        LocalDate protestDate = requestDTO.getProtestDate();

        if (protestDate.isAfter(LocalDate.now())) {
            throw new ValidationException(ErrorCode.PROTEST_DATE_IN_FUTURE);
        }

        if (protestDate.isBefore(resolution.getResolutionTime().toLocalDate())) {
            throw new ValidationException(ErrorCode.PROTEST_DATE_BEFORE_RESOLUTION_TIME);
        }
    }

    private void checkAccepted(ProsecutorProtest protest) {
        if (protest.isAccepted()) {
            throw new ValidationException(ErrorCode.PROSECUTOR_PROTEST_ALREADY_ACCEPTED);
        }
    }

    private void checkResolutionIdAndNumber(Long resolutionId, String number) {
        if (protestRepository.existsByResolutionIdAndNumber(resolutionId, number)) {
            throw new ValidationException(ErrorCode.PROTEST_FOR_THIS_RESOLUTION_AND_NUMBER_ALREADY_EXISTS);
        }
    }
}
