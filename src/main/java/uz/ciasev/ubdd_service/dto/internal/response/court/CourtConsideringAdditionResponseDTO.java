package uz.ciasev.ubdd_service.dto.internal.response.court;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;

@Getter
public class CourtConsideringAdditionResponseDTO extends DictResponseDTO {
    private final Long courtConsideringBasisId;

    public CourtConsideringAdditionResponseDTO(CourtConsideringAddition entity) {
        super(entity);
        this.courtConsideringBasisId = entity.getCourtConsideringBasisId();
     }
}
