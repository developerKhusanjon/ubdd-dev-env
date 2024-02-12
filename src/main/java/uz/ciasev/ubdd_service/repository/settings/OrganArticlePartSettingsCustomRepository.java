package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.settings.OrganAbstractArticlePartSettings;
import uz.ciasev.ubdd_service.entity.settings.OrganArticleSettingsProjection;
import uz.ciasev.ubdd_service.repository.protocol.EntityRepository;

import java.util.List;

public interface OrganArticlePartSettingsCustomRepository<T extends OrganAbstractArticlePartSettings> extends EntityRepository<T> {

    List<Article> findArticle(Specification<T> specification);

    List<Article> findArticle(Specification<T> specification, Sort sort);

    List<ArticlePart> findArticlePart(Specification<T> specification);

    List<ArticlePart> findArticlePart(Specification<T> specification, Sort sort);

    List<OrganArticleSettingsProjection> findOrganArticleSettingsProjection(Specification<T> specification);

    List<OrganArticleSettingsProjection> findOrganArticleSettingsProjection(Specification<T> specification, Sort sort);
}
