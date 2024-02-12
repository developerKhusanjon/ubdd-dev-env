package uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProsecutorProtestUpdateRequestDTO extends AbstractProsecutorProtestRequestDTO {

    public ProsecutorProtest applyTo(ProsecutorProtest protest) {
        return super.applyTo(protest);
    }
}
