package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

@Getter
public class OrganListResponseDTO extends AliasedDictResponseDTO {

    private final LocalFileUrl logoUrl;
    private final Boolean isGlobalCriminalInvestigator;

    public OrganListResponseDTO(Organ entity) {
        super(entity);
        this.logoUrl = LocalFileUrl.ofNullable(entity.getLogoPath());
        this.isGlobalCriminalInvestigator = entity.getIsGlobalCriminalInvestigator();
    }
}
