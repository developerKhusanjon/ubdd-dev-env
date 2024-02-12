package uz.ciasev.ubdd_service.service.evidence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.evidence.EvidenceCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.evidence.EvidenceRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.evidence.EvidenceUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.IDResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.PersonListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.EvidenceDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.evidence.Evidence;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.EvidenceRepository;
import uz.ciasev.ubdd_service.service.AdmCaseChangeReasonService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.service.person.PersonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvidenceServiceImpl implements EvidenceService {

    private final EvidenceRepository evidenceRepository;
    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final AdmCaseChangeReasonService changeReasonService;
    private final PersonService personService;

    @Override
    public Evidence findById(Long id) {
        return evidenceRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Evidence.class, id));
    }

    @Override
    public List<EvidenceDetailResponseDTO> findAllDetailByAdmCaseId(Long admCaseId) {
        return findAllByAdmCaseId(admCaseId)
                .stream()
                .map(this::buildDetail)
                .collect(Collectors.toList());
    }

    @Override
    public EvidenceDetailResponseDTO findDetailById(Long id) {
        return buildDetail(findById(id));
    }

    @Override
    public List<Evidence> findAllByAdmCaseId(Long admCaseId) {
        return evidenceRepository.findAllByAdmCaseId(admCaseId);
    }

    @Override
    public boolean existsByAdmCaseId(Long admCaseId) {
        return !evidenceRepository.findAllIdByAdmCaseId(admCaseId).isEmpty();
    }

    @Override
    public IDResponseDTO create(User user, EvidenceCreateRequestDTO evidenceRequestDTO) {

        AdmCase admCase = admCaseService.getById(evidenceRequestDTO.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.ADD_EVIDENCE_TO_ADM_CASE, admCase);

        Person person = Optional.ofNullable(evidenceRequestDTO.getPersonId()).map(personService::findById).orElse(null);

        return new IDResponseDTO(create(user, admCase, person, evidenceRequestDTO).getId());
    }

    @Override
    @Transactional
    public Evidence update(User user, Long id, EvidenceUpdateRequestDTO evidenceRequestDTO) {

        Evidence evidence = findById(id);
        AdmCase admCase = admCaseService.getById(evidence.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.EDIT_EVIDENCE, admCase);

        evidence.setPerson(Optional.ofNullable(evidenceRequestDTO.getPersonId()).map(personService::findById).orElse(null));

        changeReasonService.create(user, admCase, evidence, evidenceRequestDTO.getChangeReason());
        return evidenceRepository.save(evidenceRequestDTO.applyTo(evidence));
    }

    @Override
    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.EVIDENCE_DELETE)
    public Evidence delete(User user, Long id, ChangeReasonRequestDTO reasonDTO) {

        Evidence evidence = findById(id);
        AdmCase admCase = admCaseService.getById(evidence.getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.DELETE_EVIDENCE, admCase);

        changeReasonService.create(user, admCase, evidence, reasonDTO);
        evidenceRepository.delete(evidence);

        return evidence;
    }

    @Override
    public Evidence create(User user, AdmCase admCase, Person person, EvidenceRequestDTO evidenceRequestDTO) {

        Evidence evidence = evidenceRequestDTO.buildEvidence();
        evidence.setUser(user);
        evidence.setAdmCase(admCase);
        evidence.setPerson(person);

        return evidenceRepository.save(evidence);
    }

    private EvidenceDetailResponseDTO buildDetail(Evidence evidence) {

        PersonListResponseDTO person = Optional.ofNullable(evidence.getPersonId()).map(personService::findDetailById).orElse(null);
        return new EvidenceDetailResponseDTO(evidence, person);
    }
}