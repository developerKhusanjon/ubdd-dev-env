package uz.ciasev.ubdd_service.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequirementDTO {

    private Long articleId;
    private Long articlePartId;
    private String description;

}
