package uz.ciasev.ubdd_service.dto.internal.request.victim;

import uz.ciasev.ubdd_service.dto.internal.request.ActorRequest;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonRequestDTO;

public interface VictimProtocolRequestDTO extends VictimRequestDTO, ActorRequest {

    String getPinpp();

    PersonRequestDTO getPerson();

    PersonDocumentRequestDTO getDocument();

    AddressRequestDTO getActualAddress();

    AddressRequestDTO getPostAddress();

    VictimDetailRequestDTO getVictimDetail();
}
