package uz.ciasev.ubdd_service.dto.internal.request;

import uz.ciasev.ubdd_service.utils.validator.CitizenshipEquality;
import uz.ciasev.ubdd_service.utils.validator.ValidActors;

@ValidActors
@CitizenshipEquality
public interface ActorRequest {

    String getPinpp();

    PersonDocumentRequestDTO getDocument();

    PersonRequestDTO getPerson();
}
