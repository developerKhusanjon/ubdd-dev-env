package uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.opinion;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.AbstractProsecutorResponseDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinion;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ProsecutorOpinionResponseDTO extends AbstractProsecutorResponseDTO {

    private final Long id;
    private final Long admCaseId;
    private final LocalDate opinionDate;

    public ProsecutorOpinionResponseDTO(ProsecutorOpinion opinion) {
        super(opinion);

        this.id = opinion.getId();
        this.admCaseId = opinion.getAdmCaseId();
        this.opinionDate = opinion.getOpinionDate();
    }
}
