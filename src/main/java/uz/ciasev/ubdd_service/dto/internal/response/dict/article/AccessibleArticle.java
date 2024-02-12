package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessibleArticle {

    private Long id;
    private Set<Long> parts;
}
