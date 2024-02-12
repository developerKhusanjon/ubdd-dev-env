package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessibleArticlesDTO {

    List<AccessibleArticle> articles = new ArrayList<>();
}
