package uz.ciasev.ubdd_service.entity.webhook.ibd;

public interface IBDWebhookArticlesProjection {

    Long getProtocolId();

    String getArticlePartShortName();

    Long getArticlePartId();

    String getArticleName();

    String getArticleViolationTypeShortName();

    Long getArticleViolationTypeId();

    Long getArticleId();

    Boolean getIsMain();
}
