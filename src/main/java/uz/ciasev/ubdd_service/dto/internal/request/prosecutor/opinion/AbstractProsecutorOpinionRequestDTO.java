package uz.ciasev.ubdd_service.dto.internal.request.prosecutor.opinion;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.dto.internal.request.prosecutor.AbstractProsecutorRequestDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinion;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.NotInFuture;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractProsecutorOpinionRequestDTO extends AbstractProsecutorRequestDTO {

    @NotInFuture(message = ErrorCode.OPINION_DATE_IN_FUTURE)
    @NotNull(message = ErrorCode.OPINION_DATE_REQUIRED)
    private LocalDate opinionDate;

    public ProsecutorOpinion buildOpinion(ProsecutorOpinion opinion) {
        ProsecutorOpinion prosecutorOpinion = (ProsecutorOpinion) super.applyTo(opinion);
        prosecutorOpinion.setOpinionDate(this.opinionDate);
        return prosecutorOpinion;
    }
}
