package uz.ciasev.ubdd_service.entity.court.projection;

import java.time.LocalDateTime;

public interface CourtViolatorTransferredArticleProjection extends CourtTransferredArticleProjection {

    Long getProtocolId();

    LocalDateTime getViolationTime();

}
