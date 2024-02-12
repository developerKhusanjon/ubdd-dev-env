package uz.ciasev.ubdd_service.service.evidence;

import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.evidence.EvidenceCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.evidence.EvidenceRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.evidence.EvidenceUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.IDResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.EvidenceDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.evidence.Evidence;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface EvidenceService {

    List<EvidenceDetailResponseDTO> findAllDetailByAdmCaseId(Long admCaseId);

    EvidenceDetailResponseDTO findDetailById(Long id);

    Evidence findById(Long id);

    List<Evidence> findAllByAdmCaseId(Long admCaseId);

    boolean existsByAdmCaseId(Long admCaseId);

    IDResponseDTO create(User user, EvidenceCreateRequestDTO evidenceRequestDTO);

    Evidence update(User user, Long id, EvidenceUpdateRequestDTO evidenceRequestDTO);

    Evidence delete(User user, Long id, ChangeReasonRequestDTO reasonDTO);

    Evidence create(User user, AdmCase admCase, Person person, EvidenceRequestDTO evidenceRequestDTO);
}
