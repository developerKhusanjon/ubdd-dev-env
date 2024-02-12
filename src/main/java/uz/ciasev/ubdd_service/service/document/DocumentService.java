package uz.ciasev.ubdd_service.service.document;

import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.document.DocumentCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.document.DocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.document.DocumentUpdateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.IDResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.DocumentDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.document.Document;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface DocumentService {

    Document create(User user, AdmCase admCase, Person person, DocumentRequestDTO documentRequestDTO);

    IDResponseDTO create(User user, DocumentCreateRequestDTO documentProtocolRequestDTO);

    Document update(User user, Long id, DocumentUpdateRequestDTO documentRequestDTO);

    Document delete(User user, Long id, ChangeReasonRequestDTO requestDTO);

    List<DocumentDetailResponseDTO> findByAdmCaseId(Long admCaseId);

    DocumentDetailResponseDTO findDetailById(Long id);

    List<Document> findScenePhotosByAdmCase(Long admCaseId);
}
