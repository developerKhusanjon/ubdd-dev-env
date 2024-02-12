package uz.ciasev.ubdd_service.service.main.resolution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartViolationTypeService;

import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias.PUNISHMENT;
import static uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias.PENALTY;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final ArticlePartViolationTypeService articlePartViolationTypeService;


    @Override
    public PenaltyPunishment.DiscountVersion calculateDiscount(SingleResolutionRequestDTO requestDTO) {
        if (requestDTO.getDecisionType().not(PUNISHMENT)) {
            return PenaltyPunishment.DiscountVersion.NO;
        }

        if (requestDTO.getMainPunishment().getPunishmentType().not(PENALTY)) {
            return PenaltyPunishment.DiscountVersion.NO;
        }

        boolean isDiscount = calculateDiscountByArticle(requestDTO);
        if (!isDiscount) {
            return PenaltyPunishment.DiscountVersion.NO;
        }

        return PenaltyPunishment.DiscountVersion.V2;
    }

    private boolean calculateDiscountByArticle(SingleResolutionRequestDTO requestDTO) {

        ArticlePart articlePart = requestDTO.getArticlePart();
        ArticleViolationType articleViolationType = requestDTO.getArticleViolationType();

        boolean isDiscount = articlePart.isDiscount();

        Boolean hasDiscountByViolationType = hasDiscountByViolationType(articlePart, articleViolationType);

        if (hasDiscountByViolationType != null) {
            isDiscount &= hasDiscountByViolationType;
        }

        return isDiscount;
    }

    private Boolean hasDiscountByViolationType(ArticlePart articlePart, ArticleViolationType articleViolationType) {

        if (articlePart == null || articleViolationType == null) {
            return null;
        }

        Optional<ArticlePartViolationType> violationType =
                articlePartViolationTypeService.findByArticlePartIdAndViolationTypeId(articlePart, articleViolationType);

        return violationType.map(ArticlePartViolationType::isDiscount).orElse(null);
    }
}
