package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

public interface CourtArticleRequestDTO {
    void setArticleId(Long externalArticleId);

    void setArticlePartId(Long externalArticlePartId);

    void setViolationId(Long externalArticleViolationTypeId);
}
