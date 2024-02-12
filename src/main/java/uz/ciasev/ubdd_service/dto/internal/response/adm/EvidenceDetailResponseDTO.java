package uz.ciasev.ubdd_service.dto.internal.response.adm;

import lombok.Data;
import uz.ciasev.ubdd_service.dto.internal.response.PersonListResponseDTO;
import uz.ciasev.ubdd_service.entity.evidence.Evidence;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Data
public class EvidenceDetailResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final LocalDateTime editedTime;
    private final Long userId;
    private final Long admCaseId;
    private final Long personId;
    private final Long evidenceCategoryId;
    private final Long measureId;
    private final Double quantity;
    private final Long currencyId;
    private final Long cost;
    private final String description;
    private final Integer evidenceSudId;
    private final LocalFileUrl evidencePhotoUrl;

    private final PersonListResponseDTO person;

    public EvidenceDetailResponseDTO(Evidence evidence, PersonListResponseDTO person) {
        this.id = evidence.getId();
        this.createdTime = evidence.getCreatedTime();
        this.editedTime = evidence.getEditedTime();
        this.userId = evidence.getUserId();
        this.admCaseId = evidence.getAdmCaseId();
        this.personId = evidence.getPersonId();
        this.evidenceCategoryId = evidence.getEvidenceCategoryId();
        this.measureId = evidence.getMeasureId();
        this.quantity = evidence.getQuantity();
        this.currencyId = evidence.getCurrencyId();
        this.cost = evidence.getCost();
        this.description = evidence.getDescription();
        this.evidenceSudId = evidence.getEvidenceSudId();
        this.evidencePhotoUrl = LocalFileUrl.ofNullable(evidence.getEvidencePhotoUri());

        this.person = person;
    }
}