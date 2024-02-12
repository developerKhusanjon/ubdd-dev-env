package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.dto.internal.request.ArticleRequest;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartViolationTypeService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@RequiredArgsConstructor
public class ConsistArticleValidator implements ConstraintValidator<ConsistArticle, ArticleRequest> {

    @Autowired
    private final ArticlePartViolationTypeService articlePartViolationTypeService;

    @Override
    public void initialize(ConsistArticle constraintAnnotation) {
    }

    @Override
    public boolean isValid(ArticleRequest articleDTO, ConstraintValidatorContext context) {

        if (articleDTO == null
                || articleDTO.getArticlePart() == null
                || articleDTO.getArticleViolationType() == null)
            return true;

        ArticlePart articlePart = articleDTO.getArticlePart();
        ArticleViolationType violationType = articleDTO.getArticleViolationType();

//        List<ArticlePart> articleParts = articlePartViolationTypeService.findArticlePartsByArticleViolationTypeId(violationType.getId());
//
//        return articleParts.stream()
//                .map(ArticlePart::getId)
//                .anyMatch(id -> id.equals(articlePart.getId()));

        Optional<ArticlePartViolationType> articlePartViolationType = articlePartViolationTypeService.findByArticlePartIdAndViolationTypeId(articlePart, violationType);
        return articlePartViolationType.isPresent();
    }
}
