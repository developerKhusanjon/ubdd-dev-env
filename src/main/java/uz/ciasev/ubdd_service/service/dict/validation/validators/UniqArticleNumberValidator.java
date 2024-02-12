package uz.ciasev.ubdd_service.service.dict.validation.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.requests.ArticleUpdateDTOI;
import uz.ciasev.ubdd_service.exception.dict.ArticleNumberAlreadyExistsException;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.SpecificationsHelper;
import uz.ciasev.ubdd_service.specifications.dict.ArticleSpecifications;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;


@Component
@RequiredArgsConstructor
public class UniqArticleNumberValidator implements DictionaryCreateValidator<Article, ArticleUpdateDTOI>, DictionaryUpdateValidator<Article, ArticleUpdateDTOI> {

    private final ArticleSpecifications specifications;

    @Override
    public void validate(DictionaryServiceWithRepository<Article> service, ArticleUpdateDTOI request) {
        validate(service, request, SpecificationsHelper.getEmpty());
    }

    @Override
    public void validate(DictionaryServiceWithRepository<Article> service, Article entity, ArticleUpdateDTOI request) {
        if (!entity.getIsActive()) {
            return;
        }
        validate(service, request, Specification.not(specifications.withId(entity.getId())));
    }

    @Override
    public Class<Article> getValidatedType() {
        return Article.class;
    }

    @Override
    public Class<ArticleUpdateDTOI> getRequestType() {
        return ArticleUpdateDTOI.class;
    }

    private void validate(DictionaryServiceWithRepository<Article> service, ArticleUpdateDTOI request, Specification<Article> extraSpec) {
        Specification<Article> spec = SpecificationsCombiner.andAll(
                extraSpec,
                DictionarySpecifications.withIsActive(true),
                specifications.withNumber(request.getNumber()),
                specifications.withPrim(request.getPrim())
        );

        boolean codeNoExists = service.getRepository().count(spec) == 0;

        if (codeNoExists) {
            return;
        }
        throw new ArticleNumberAlreadyExistsException(request.getNumber(), request.getPrim());
    }
}
