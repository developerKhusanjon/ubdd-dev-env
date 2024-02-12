package uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.protest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.AbstractProsecutorDocumentResponseDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocumentProjection;


@Getter
@EqualsAndHashCode(callSuper = true)
public class ProsecutorProtestDocumentResponseDTO extends AbstractProsecutorDocumentResponseDTO {
    private final Long protestId;
    private final String protestNumber;

    public ProsecutorProtestDocumentResponseDTO(ProsecutorProtestDocument document, String number) {
        super(document);
        this.protestId = document.getProtestId();
        this.protestNumber = number;
    }

    public ProsecutorProtestDocumentResponseDTO(ProsecutorProtestDocumentProjection dp) {
        super(dp);
        this.protestId = dp.getProtestId();
        this.protestNumber = dp.getProtestNumber();
    }
}
