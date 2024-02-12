package uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.protest;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.AbstractProsecutorResponseDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractProsecutorProtestResponseDTO extends AbstractProsecutorResponseDTO {
    private final LocalDate protestDate;
    private final String number;
    private final Long reasonId;

    public AbstractProsecutorProtestResponseDTO(ProsecutorProtest protest) {
        super(protest);
        this.protestDate = protest.getProtestDate();
        this.number = protest.getNumber();
        this.reasonId = protest.getReasonId();
    }
}
