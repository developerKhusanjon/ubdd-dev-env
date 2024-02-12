package uz.ciasev.ubdd_service.service.main;


import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.dto.internal.request.ActorRebindRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.PersonListResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;

import javax.annotation.Nullable;
import java.util.List;

public interface ActorService {

    Pair<Violator, ViolatorDetail> createViolatorWithDetail(@Nullable User user, Pair<Person, ? extends PersonDocument> personWithDocument, AdmCase admCase, ViolatorCreateRequestDTO violatorRequestDTO, ProtocolRequestDTO protocol);

    Pair<Victim, VictimDetail> createVictimWithDetail(@Nullable User user, Pair<Person, ? extends PersonDocument> personWithDocument, AdmCase admCase, Protocol protocol, VictimProtocolRequestDTO victimRequestDTO);

    Pair<Participant, ParticipantDetail> createParticipantWithDetail(@Nullable User user, Pair<Person, ? extends PersonDocument> personWithDocument, AdmCase admCase, Protocol protocol, ParticipantProtocolRequestDTO participantRequestDTO);

    void deleteVictimDetail(VictimDetail victimDetail);

    void deleteParticipantDetail(ParticipantDetail participantDetail);

    void deleteIfNotHasDetails(Participant participant);

    void deleteIfNotHasDetails(Victim victim);

    void deleteIfNotHasDetails(Violator violator);

    List<PersonListResponseDTO> getAllPersonsInAdmCase(Long admCaseId);

    List<PersonListResponseDTO> getAllViolatorAndVictimPersonsInAdmCase(Long admCaseId);

    Violator rebindViolator(User user, Long violatorId, ActorRebindRequestDTO requestDTO);

    boolean isAllVictimsRelatedWithViolator(List<Long> victimsId, Violator violator);

    boolean isAllVictimsRelatedWithAdmCase(List<Long> victimsId, Long admCaseId);

    void updateViolatorServiceData(User user, Long id);
}
