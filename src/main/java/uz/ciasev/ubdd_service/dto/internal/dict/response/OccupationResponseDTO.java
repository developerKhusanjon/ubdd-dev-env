package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;

@Getter
public class OccupationResponseDTO extends DictResponseDTO {

    private final Boolean isWorker;

    public OccupationResponseDTO(Occupation occupation) {
        super(occupation);
        this.isWorker = occupation.getIsWorker();
    }
}
