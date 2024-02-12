package uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.opinion;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.AbstractProsecutorDocumentResponseDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocumentProjection;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ProsecutorOpinionDocumentResponseDTO extends AbstractProsecutorDocumentResponseDTO {

    private final Long opinionId;

    public ProsecutorOpinionDocumentResponseDTO(ProsecutorOpinionDocument document) {
        super(document);
        this.opinionId = document.getOpinionId();
    }

    public ProsecutorOpinionDocumentResponseDTO(ProsecutorOpinionDocumentProjection dp) {
        super(dp);
        this.opinionId = dp.getOpinionId();
    }
}
