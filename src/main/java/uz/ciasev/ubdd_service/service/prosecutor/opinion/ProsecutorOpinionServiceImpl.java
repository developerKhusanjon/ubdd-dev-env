package uz.ciasev.ubdd_service.service.prosecutor.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion.AbstractProsecutorOpinionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion.ProsecutorOpinionCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion.ProsecutorOpinionDocumentCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion.ProsecutorOpinionUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinion;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocumentProjection;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.prosecutor.opinion.ProsecutorOpinionDocumentRepository;
import uz.ciasev.ubdd_service.repository.prosecutor.opinion.ProsecutorOpinionRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProsecutorOpinionServiceImpl implements ProsecutorOpinionService {

    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final ValidationService validationService;
    private final ProtocolService protocolService;
    private final ProsecutorOpinionRepository opinionRepository;
    private final ProsecutorOpinionDocumentRepository documentRepository;

    @Override
    @Transactional
    public ProsecutorOpinion createOpinion(User user, ProsecutorOpinionCreateRequestDTO requestDTO) {

        AdmCase admCase = admCaseService.getById(requestDTO.getAdmCaseId());
        validateProsecutorOpinionCreate(user, requestDTO, admCase);

        ProsecutorOpinion opinion = requestDTO.buildOpinion();
        opinion.setUser(user);
        opinion.setAdmCase(admCase);

        List<ProsecutorOpinionDocument> documentEntities = requestDTO.getDocuments().stream()
                        .map(uri -> new ProsecutorOpinionDocument(user, opinion, uri))
                        .collect(Collectors.toList());

        opinionRepository.save(opinion);
        documentRepository.saveAll(documentEntities);

        return opinion;
    }

    @Override
    @Transactional
    public void updateOpinionById(User user, Long id, ProsecutorOpinionUpdateRequestDTO requestDTO) {
        ProsecutorOpinion opinion = findOpinionById(id);
        AdmCase admCase = admCaseService.getById(opinion.getAdmCaseId());

        validateProsecutorOpinionUpdate(user, requestDTO, admCase, opinion);

        opinion = requestDTO.buildOpinion(opinion);
        opinionRepository.save(opinion);
    }

    @Override
    public Page<ProsecutorOpinion> findAllByAdmCaseId(Long admCaseId, Pageable pageable) {
        return opinionRepository.findAllByAdmCaseId(admCaseId, pageable);
    }

    @Override
    public Page<ProsecutorOpinionDocument> findAllDocumentsByOpinionId(Long id, Pageable pageable) {
        return documentRepository.findAllByOpinionId(id, pageable);
    }

    @Override
    public ProsecutorOpinion findOpinionById(Long id) {
        Optional<ProsecutorOpinion> opinionOptional = opinionRepository.findById(id);

        return opinionOptional.orElseThrow(() -> new EntityByIdNotFound(ProsecutorOpinion.class, id));
    }

    @Override
    @Transactional
    public void addDocumentByOpinionId(User user, Long id, ProsecutorOpinionDocumentCreateRequestDTO requestDTO) {

    }

    @Override
    public Page<ProsecutorOpinionDocumentProjection> findAllDocumentsByAdmCaseId(Long admCaseId, Pageable pageable) {
        return documentRepository.findAllByAdmCaseId(admCaseId, pageable);
    }

    private void validateProsecutorOpinionUpdate(User user,
                                                 ProsecutorOpinionUpdateRequestDTO requestDTO,
                                                 AdmCase admCase,
                                                 ProsecutorOpinion opinion) {
        String entityProsecutorInfo = opinion.getProsecutorInfo();
        String dtoProsecutorInfo = requestDTO.getProsecutorInfo();
        LocalDate entityOpinionDate = opinion.getOpinionDate();
        LocalDate dtoOpinionDate = requestDTO.getOpinionDate();

        if (entityProsecutorInfo.equals(dtoProsecutorInfo) && entityOpinionDate.equals(dtoOpinionDate)) {
            checkIfOpinionAlreadyExists(requestDTO, admCase.getId());
        }

        admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, ActionAlias.EDIT_PROSECUTOR_OPINION, admCase);
        validateActionOnProsecutorOpinion(requestDTO, admCase);
    }

    private void validateProsecutorOpinionCreate(User user, ProsecutorOpinionCreateRequestDTO requestDTO, AdmCase admCase) {
        checkIfOpinionAlreadyExists(requestDTO, admCase.getId());
        admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, ActionAlias.CREATE_PROSECUTOR_OPINION, admCase);
        validateActionOnProsecutorOpinion(requestDTO, admCase);
    }

    private void validateActionOnProsecutorOpinion(AbstractProsecutorOpinionRequestDTO requestDTO, AdmCase admCase) {
        LocalDate opinionDate = requestDTO.getOpinionDate();
        Protocol protocol = protocolService.findEarliestProtocolInAdmCase(admCase.getId());
        LocalDate protocolDate = protocol.getCreatedTime().toLocalDate();

        if (opinionDate.isBefore(protocolDate)) {
            throw new ValidationException(ErrorCode.OPINION_DATE_BEFORE_FIRST_PROTOCOL_DATE);
        }
        if (validationService.checkRegionNotIn(requestDTO, admCase.getRegion())) {
            throw new ValidationException(ErrorCode.ADM_CASE_REGION_NOT_IN_PROSECUTOR_VISIBILITY);
        }

        if (validationService.checkDistrictNotIn(requestDTO, admCase.getDistrict())) {
            throw new ValidationException(ErrorCode.ADM_CASE_DISTRICT_NOT_IN_PROSECUTOR_VISIBILITY);
        }
    }

    private void checkIfOpinionAlreadyExists(AbstractProsecutorOpinionRequestDTO requestDTO, Long admCaseId) {
        if (opinionRepository.existsByAdmCaseIdAndProsecutorInfoAndOpinionDate(
                admCaseId,
                requestDTO.getProsecutorInfo(),
                requestDTO.getOpinionDate())) {
            throw new ValidationException(ErrorCode.OPINION_FOR_ADM_CASE_AND_DATE_ALREADY_EXISTS);
        }
    }

}
