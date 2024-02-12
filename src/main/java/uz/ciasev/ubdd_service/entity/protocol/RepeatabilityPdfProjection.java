package uz.ciasev.ubdd_service.entity.protocol;

import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import java.time.LocalDateTime;


public interface RepeatabilityPdfProjection {

    Long getFromProtocolId();

    Long getDecisionId();

    Long getProtocolId();

    ArticlePart getArticlePart();

    LocalDateTime getViolationTime();
}
