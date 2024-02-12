package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystem;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalSystemResponseDTO {

    private Long id;
    private String alias;
    private MultiLanguage name;
    private Boolean isActive;

    public ExternalSystemResponseDTO(ExternalSystem externalSystem) {
        this.id = externalSystem.getId();
        this.name = externalSystem.getName();
        this.alias = externalSystem.getAlias().toString();
        this.isActive = externalSystem.getIsActive();
    }
}
