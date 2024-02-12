package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.dto.internal.response.adm.InvoiceResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.VictimListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.MibCardResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.violator.Violator;

@Getter
@Setter
public class CompensationDetailResponseDTO extends CompensationListResponseDTO {

    private final MibCardResponseDTO mibCard;


    public CompensationDetailResponseDTO(Compensation compensation,
//                                         Resolution resolution,
                                         Violator violator,
                                         Person person,
                                         VictimListResponseDTO victim,
                                         InvoiceResponseDTO invoice,
                                         MibCardResponseDTO mibCard) {
        super(compensation,
//                resolution,
                violator,
                person, victim, invoice);
        this.mibCard = mibCard;
    }
}

