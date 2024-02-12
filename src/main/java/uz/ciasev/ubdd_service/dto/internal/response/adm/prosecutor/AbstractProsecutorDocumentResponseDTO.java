package uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.prosecutor.AbstractProsecutorDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocumentProjection;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocumentProjection;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

import java.time.LocalDateTime;

@Data
public abstract class AbstractProsecutorDocumentResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final Long userId;
    private final LocalFileUrl url;

    public AbstractProsecutorDocumentResponseDTO(AbstractProsecutorDocument document) {
        this.id = document.getId();
        this.createdTime = document.getCreatedTime();
        this.userId = document.getUserId();
        this.url = LocalFileUrl.ofNullable(document.getUri());
    }

    public AbstractProsecutorDocumentResponseDTO(ProsecutorProtestDocumentProjection dp) {
        this.id = dp.getId();
        this.createdTime = dp.getCreatedTime();
        this.userId = dp.getUserId();
        this.url = LocalFileUrl.ofNullable(dp.getUri());
    }

    public AbstractProsecutorDocumentResponseDTO(ProsecutorOpinionDocumentProjection dp) {
        this.id = dp.getId();
        this.createdTime = dp.getCreatedTime();
        this.userId = dp.getUserId();
        this.url = LocalFileUrl.ofNullable(dp.getUri());
    }
}
