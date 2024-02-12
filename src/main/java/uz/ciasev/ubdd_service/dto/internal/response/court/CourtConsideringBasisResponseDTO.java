package uz.ciasev.ubdd_service.dto.internal.response.court;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;

@Getter
public class CourtConsideringBasisResponseDTO extends DictResponseDTO {
    private final Boolean hasAdditions;

    public CourtConsideringBasisResponseDTO(CourtConsideringBasis entity) {
        super(entity);
        this.hasAdditions = entity.getHasAdditions();
     }
}
