package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.LastEmploymentInfoDTO;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;

@Getter
public class VictimDetailResponseDTO extends VictimDetailListResponseDTO {

    private final String signature;

    private final AddressResponseDTO documentGivenAddress;

    public VictimDetailResponseDTO(VictimDetail victimDetail,
                                   LastEmploymentInfoDTO lastEmploymentInfoDTO,
                                   Victim victim,
                                   Person person,
                                   AddressResponseDTO documentGivenAddress
    ) {

        super(victimDetail, lastEmploymentInfoDTO, victim, person);
        this.signature = victimDetail.getSignature();
        this.documentGivenAddress = documentGivenAddress;
    }
}
