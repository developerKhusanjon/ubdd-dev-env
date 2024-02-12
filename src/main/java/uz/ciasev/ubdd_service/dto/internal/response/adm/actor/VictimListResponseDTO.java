package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

@Getter
public class VictimListResponseDTO extends AbstractVictimResponseDTO {

    private final Long id;
    private final LocalFileUrl photoUrl;

    public VictimListResponseDTO(Victim victim,
                                 Person person) {

        super(victim, person);
        this.id = victim.getId();
        this.photoUrl = LocalFileUrl.ofNullable(victim.getPhotoUri());
    }
}
