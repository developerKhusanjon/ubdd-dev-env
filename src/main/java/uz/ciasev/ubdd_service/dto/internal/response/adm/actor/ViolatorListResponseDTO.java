package uz.ciasev.ubdd_service.dto.internal.response.adm.actor;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.utils.types.LocalFileUrl;

@Getter
public class ViolatorListResponseDTO extends AbstractViolatorResponseDTO {

    private final Long id;
    private final LocalFileUrl photoUrl;


    public ViolatorListResponseDTO(Violator violator, Person person) {
        super(violator, person);

        this.id = violator.getId();
        this.photoUrl = LocalFileUrl.ofNullable(violator.getPhotoUri());
    }
}