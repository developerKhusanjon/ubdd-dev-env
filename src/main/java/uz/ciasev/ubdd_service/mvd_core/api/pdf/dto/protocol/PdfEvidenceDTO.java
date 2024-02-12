package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.evidence.Evidence;

@Data
public class PdfEvidenceDTO {

    private String name;
    private Double count;

    public PdfEvidenceDTO(Evidence evidence) {
        this.name = evidence.getEvidenceCategory().getDefaultName();
        this.count = evidence.getQuantity();
    }
}
