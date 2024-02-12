package uz.ciasev.ubdd_service.service.violation_event.decision;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.request.document.DocumentRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;
import uz.ciasev.ubdd_service.service.document.DocumentService;

import java.util.Optional;
import java.util.stream.Stream;

import static uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias.SCENE_PHOTO;
import static uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias.VEHICLE_NUMBER_PHOTO;

@Service
@RequiredArgsConstructor
public class ViolationEventPhotoSaveServiceImpl implements ViolationEventPhotoSaveService {

    private final DocumentService documentService;
    private final AliasedDictionaryService<DocumentType, DocumentTypeAlias> documentTypeService;

    @Override
    @Transactional
    public void save(User user, ViolationEventApiDTO event, AdmCase admCase) {
        Stream.of(
                        Pair.of(Optional.ofNullable(event.getVehicleNumberPhotoPath()), VEHICLE_NUMBER_PHOTO),
                        Pair.of(Optional.ofNullable(event.getFarViewPhotoPath()), SCENE_PHOTO),
                        Pair.of(Optional.ofNullable(event.getNearViewPhotoPath()), SCENE_PHOTO)
                )
                .filter(e -> e.getFirst().isPresent())
                .map(e -> buildRequest(event, e.getFirst().get(), e.getSecond()))
                .forEach(requestDTO -> documentService.create(user, admCase, null, requestDTO));
    }

    private DocumentRequestDTO buildRequest(ViolationEventApiDTO event, String fileUri, DocumentTypeAlias typeAlias) {
        DocumentType documentType = documentTypeService.getByAlias(typeAlias);
        DocumentRequestDTO requestDTO = new DocumentRequestDTO();
        requestDTO.setDocumentType(documentType);
        requestDTO.setDescription("Violation event photo");
        requestDTO.setUri(fileUri);
        return requestDTO;
    }
}
