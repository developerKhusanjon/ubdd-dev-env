package uz.ciasev.ubdd_service.service.prosecutor.protest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest.ProsecutorProtestCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest.ProsecutorProtestDocumentCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest.ProsecutorProtestUpdateRequestDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocumentProjection;
import uz.ciasev.ubdd_service.entity.user.User;


public interface ProsecutorProtestService {

    ProsecutorProtest createProtest(User user, ProsecutorProtestCreateRequestDTO requestDTO);

    Page<ProsecutorProtest> findAllByResolutionId(Long resolutionId, Pageable pageable);

    Page<Pair<ProsecutorProtestDocument, String>> findAllDocumentsByProtestId(Long protestId, Pageable pageable);

    void acceptProtestById(User user, Long protestId);

    ProsecutorProtest getById(Long id);

    void updateProtestById(User user, Long id, ProsecutorProtestUpdateRequestDTO requestDTO);

    void addDocumentByProtestId(User user, Long id, ProsecutorProtestDocumentCreateRequestDTO requestDTO);

    Page<ProsecutorProtestDocumentProjection> findAllDocumentsByResolutionId(Long resolutionId, Pageable pageable);

}
