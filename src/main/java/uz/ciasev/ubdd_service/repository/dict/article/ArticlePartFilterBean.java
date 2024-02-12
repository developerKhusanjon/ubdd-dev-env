package uz.ciasev.ubdd_service.repository.dict.article;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.specifications.dict.ArticlePartSpecifications;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.filters.LongFilter;

@Component
@AllArgsConstructor
public class ArticlePartFilterBean {
    private final ArticlePartSpecifications specifications;

    @Bean
    public FilterHelper<ArticlePart> getArticleTagFilterHelper() {
        return new FilterHelper<ArticlePart> (
                Pair.of("tagId", new LongFilter<ArticlePart>(specifications::withTagId))
        );
    }
}
