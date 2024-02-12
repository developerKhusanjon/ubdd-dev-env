package uz.ciasev.ubdd_service.dto.internal.request.prosecutor.protest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.AbstractProsecutorRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.prosecutor.ProsecutorProtestReason;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractProsecutorProtestRequestDTO extends AbstractProsecutorRequestDTO {

    @NotNull(message = ErrorCode.PROTEST_DATE_REQUIRED)
    private LocalDate protestDate;

    @NotBlank(message = ErrorCode.NUMBER_REQUIRED)
    @Size(max = 32, message = ErrorCode.NUMBER_MORE_THAN_32_CHARACTERS)
    private String number;

    @NotNull(message = ErrorCode.REASON_REQUIRED)
    @ActiveOnly(message = ErrorCode.REASON_DEACTIVATED)
    @JsonProperty(value = "reasonId")
    private ProsecutorProtestReason reason;

    public ProsecutorProtest applyTo(ProsecutorProtest protest) {
        ProsecutorProtest prosecutorProtest = (ProsecutorProtest) super.applyTo(protest);
        prosecutorProtest.setProtestDate(this.protestDate);
        prosecutorProtest.setNumber(this.number);
        prosecutorProtest.setReason(this.reason);
        return prosecutorProtest;
    }
}
