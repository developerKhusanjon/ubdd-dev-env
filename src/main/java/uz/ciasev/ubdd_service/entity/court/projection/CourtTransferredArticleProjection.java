package uz.ciasev.ubdd_service.entity.court.projection;

public interface CourtTransferredArticleProjection {

    Long getTransferId();

    Long getInternalArticlePartId();

    Long getExternalArticleViolationTypeId();

    Long getExternalArticleId();

    Long getExternalArticlePartId();

}
