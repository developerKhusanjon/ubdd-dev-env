package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.InnerPersonResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.victim.Victim;

@Getter
public class AbstractVictimResponseDTO {

    @JsonUnwrapped
    private final InnerPersonResponseDTO person;

    private final Long admCaseId;
    private final Long mergedToVictimId;
    private final Long actualAddressId;
    private final Long postAddressId;
    private final String inn;
    private final String mobile;
    private final String landline;
    private final Boolean notificationViaSms = null;
    private final Boolean notificationViaMail = null;

    public AbstractVictimResponseDTO(Victim victim,
                             Person person) {

        this.person = new InnerPersonResponseDTO(person);

        this.admCaseId = victim.getAdmCaseId();
        this.mergedToVictimId = victim.getMergedToVictimId();
        this.actualAddressId = victim.getActualAddressId();
        this.postAddressId = victim.getPostAddressId();
        this.inn = victim.getInn();
        this.mobile = victim.getMobile();
        this.landline = victim.getLandline();

    }
}
