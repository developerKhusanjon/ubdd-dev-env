package uz.ciasev.ubdd_service.mvd_core.api.pdf.dto.protocol;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;

@Data
public class PdfDamageDTO {

    private String name;
    private Long amount;
    private VictimTypeAlias victimType;
}
