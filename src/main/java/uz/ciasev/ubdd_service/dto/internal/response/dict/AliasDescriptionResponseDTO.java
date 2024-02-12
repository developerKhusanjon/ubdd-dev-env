package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.AliasDescription;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliasDescriptionResponseDTO {

    private Long id;
    private String aliasName;
    private String aliasValue;
    private String label;
    private String description;

    public AliasDescriptionResponseDTO(AliasDescription aliasDescription) {
        this.id = aliasDescription.getId();
        this.aliasName = aliasDescription.getAliasName();
        this.aliasValue = aliasDescription.getAliasValue();
        this.label = aliasDescription.getLabel();
        this.description = aliasDescription.getDescription();
    }
}
