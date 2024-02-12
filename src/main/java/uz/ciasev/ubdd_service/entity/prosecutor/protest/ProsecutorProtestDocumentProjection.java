package uz.ciasev.ubdd_service.entity.prosecutor.protest;

import java.time.LocalDateTime;

public interface ProsecutorProtestDocumentProjection {
    LocalDateTime getCreatedTime();
    Long getUserId();
    Long getId();
    Long getProtestId();
    String getProtestNumber();
    String getUri();
}
