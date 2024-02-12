package uz.ciasev.ubdd_service.service.prosecutor.opinion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion.ProsecutorOpinionCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion.ProsecutorOpinionDocumentCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion.ProsecutorOpinionUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinion;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocumentProjection;
import uz.ciasev.ubdd_service.entity.user.User;

public interface ProsecutorOpinionService {

    ProsecutorOpinion createOpinion(User user, ProsecutorOpinionCreateRequestDTO requestDTO);

    Page<ProsecutorOpinion> findAllByAdmCaseId(Long admCaseId, Pageable pageable);

    Page<ProsecutorOpinionDocument> findAllDocumentsByOpinionId(Long id, Pageable pageable);

    ProsecutorOpinion findOpinionById(Long id);

    void updateOpinionById(User user, Long id, ProsecutorOpinionUpdateRequestDTO requestDTO);

    void addDocumentByOpinionId(User user, Long id, ProsecutorOpinionDocumentCreateRequestDTO requestDTO);

    Page<ProsecutorOpinionDocumentProjection> findAllDocumentsByAdmCaseId(Long admCaseId, Pageable pageable);

}
