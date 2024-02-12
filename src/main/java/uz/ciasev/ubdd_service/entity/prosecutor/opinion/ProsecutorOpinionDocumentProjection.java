package uz.ciasev.ubdd_service.entity.prosecutor.opinion;

import java.time.LocalDateTime;

public interface ProsecutorOpinionDocumentProjection {
    LocalDateTime getCreatedTime();
    Long getUserId();
    Long getId();
    Long getOpinionId();
    String getUri();
}
