package uz.ciasev.ubdd_service.dto.internal.response.dict;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.AliasedDictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;

@Getter
public class MibCaseStatusResponseDTO extends AliasedDictResponseDTO {
    private final Boolean isSuspensionArticle;
    private final String color;

    public MibCaseStatusResponseDTO(MibCaseStatus entity, String color) {
        super(entity);
        this.isSuspensionArticle = entity.getIsSuspensionArticle();
        this.color = color;
    }
}
