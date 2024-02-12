package uz.ciasev.ubdd_service.entity.protocol;

public interface ProtocolArticlesProjection {

    Long getProtocolId();
    String getArticlePartShortName();
    Long getArticlePartId();
    String getArticleViolationTypeShortName();
    Long getArticleViolationTypeId();
    Long getArticleId();
    Boolean getIsMain();
}
