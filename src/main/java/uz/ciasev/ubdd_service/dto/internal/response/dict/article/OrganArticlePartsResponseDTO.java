package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.Data;

import java.util.Set;

@Data
public class OrganArticlePartsResponseDTO {

    private Set<OrganArticlePartResponseDTO> articleParts;
}
