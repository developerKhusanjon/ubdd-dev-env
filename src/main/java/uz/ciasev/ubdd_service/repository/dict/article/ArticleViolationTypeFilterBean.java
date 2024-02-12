package uz.ciasev.ubdd_service.repository.dict.article;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.specifications.dict.ArticleViolationTypeSpecifications;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.filters.LongFilter;

@Component
@AllArgsConstructor
public class ArticleViolationTypeFilterBean {
    private final ArticleViolationTypeSpecifications specifications;

    @Bean
    public FilterHelper<ArticleViolationType> getArticleViolationTypeFilterHelper() {
        return new FilterHelper<ArticleViolationType> (
                Pair.of("tagId", new LongFilter<ArticleViolationType>(specifications::withTagId))
        );
    }
}
