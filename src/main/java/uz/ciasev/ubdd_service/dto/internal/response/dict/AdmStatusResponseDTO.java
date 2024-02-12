package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Data
public class AdmStatusResponseDTO {

    private final Long id;
    private final String alias;
    private final MultiLanguage name;
    private final MultiLanguage caseName;
    private final String color;
    @Deprecated
    private final Boolean isActive = true;

    public AdmStatusResponseDTO(AdmStatus admStatus) {
        this.id = admStatus.getId();
        this.alias = admStatus.getAlias().name();
        this.name = admStatus.getName();
        this.caseName = admStatus.getCaseName();
        this.color = admStatus.getColor();
    }
}
