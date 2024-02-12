package uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.damage.DamageRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimProtocolRequestDTO;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CourtVictimRequestDTO implements CourtBaseDTO, VictimProtocolRequestDTO {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long caseId;

    @NotNull(message = ErrorCode.CLAIM_ID_REQUIRED)
    private Long claimId;

    private String pinpp;

    private PersonRequestDTO person;

    private PersonDocumentRequestDTO document;

    private AddressRequestDTO actualAddress;

    private AddressRequestDTO postAddress;

    private VictimDetailRequestDTO victimDetail;

    private String mobile;

    private String landline;

    private String inn;

    @Valid
    private List<DamageRequestDTO> damages;

    public Victim applyTo(Victim victim) {
        victim.setMobile(this.mobile);
        victim.setLandline(this.landline);
        victim.setInn(this.inn);

        return victim;
    }
}
