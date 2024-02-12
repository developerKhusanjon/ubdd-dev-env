package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrganArticlePartResponseDTO {

    private Long id;
    private Boolean isHeaderOnly;

    public OrganArticlePartResponseDTO(Long id) {
        this.id = id;
    }

    public OrganArticlePartResponseDTO(Long id, Boolean isHeaderOnly) {
        this.id = id;
        this.isHeaderOnly = isHeaderOnly;
    }
}
