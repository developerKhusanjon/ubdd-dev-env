package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.DamageType;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DamageTypeResponseDTO {

    private Long id;
    private String alias;
    private String code;
    private MultiLanguage name;
    private Boolean isActive;

    public DamageTypeResponseDTO(DamageType damageType) {
        this.id = damageType.getId();
        this.code = damageType.getCode();
        this.alias = damageType.getAlias().toString();
        this.name = damageType.getName();
        this.isActive = damageType.getIsActive();
    }
}
