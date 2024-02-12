package uz.ciasev.ubdd_service.mvd_core.api.court.service.nine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CheckCourtDuplicateRequestService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.exception.court.DuplicateCourtRequestException;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.main.ActorService;
import uz.ciasev.ubdd_service.service.main.PersonDataService;
import uz.ciasev.ubdd_service.service.person.PersonService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.victim.VictimService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NineMethodFromCourtServiceImpl implements NineMethodFromCourtService {

    private final PersonService personService;
    private final ActorService newActorService;
    private final AdmCaseService admCaseService;
    private final ProtocolService protocolService;
    private final PersonDataService personDataService;
    private final VictimService victimService;
    private final CheckCourtDuplicateRequestService courtDuplicateRequestService;

    @Override
    @Transactional
    public Person accept(CourtVictimRequestDTO victimRequestDTO) {
        String pinpp = (victimRequestDTO.getPinpp() != null)
                ? victimRequestDTO.getPinpp()
                : personService.generateFakePinpp(victimRequestDTO.getPerson(), victimRequestDTO.getDocument());

//        Нельзя делать обычную проверку на дубликат, потаму что при обычной проверке будет возвращаться result = 1 и envelopedId лога.
//        А суд в методе создения в envelopedId ждет id потерпевшего.
        try {
            courtDuplicateRequestService.checkAndRemember(victimRequestDTO);
        } catch (DuplicateCourtRequestException e) {
            return personService.findByPinpp(pinpp)
                    .orElseThrow(() -> new CourtValidationException("Victim person not found for duplicate request"));
        }


        // почему здесь такая валидация. Если в деле два нарушителя, они могут быть друг другу потерпевшими
//        validateViolatorsAsVictims(victimRequestDTO.getCaseId(), pinpp);

        return handle(victimRequestDTO);
    }

    private Person handle(CourtVictimRequestDTO victimRequestDTO) {
//        AdmCase admCase = admCaseService.getByIdAndClaimId(victimRequestDTO.getCaseId(), victimRequestDTO.getClaimId());
        AdmCase admCase = admCaseService.getById(victimRequestDTO.getCaseId());

        Pair<Person, ? extends PersonDocument> personWithDocument = personDataService.provideByPinppOrManualDocument(victimRequestDTO);
        List<Protocol> protocols = protocolService.findAllProtocolsInAdmCase(victimRequestDTO.getCaseId());

        Person victim = null;
        for (Protocol protocol : protocols) {
            if (protocol.getViolatorDetail().getViolator().getPerson().getPinpp().equals(personWithDocument.getFirst().getPinpp())) {
                continue;
            }

            List<String> protocolVictims = victimService.findVictimsPinppByProtocolId(protocol.getId());
            if (protocolVictims.contains(personWithDocument.getFirst().getPinpp())) {
                continue;
            }

            Pair<Victim, VictimDetail> victimWithDetailPair = newActorService.createVictimWithDetail(null, personWithDocument, admCase, protocol, victimRequestDTO);
            victim = victimWithDetailPair.getFirst().getPerson();
        }
        return victim;
    }

//    private void validateViolatorsAsVictims(Long caseId, String pinpp) {
//        if (personService.findViolatorsPinppByAdmCaseId(caseId)
//                .parallelStream()
//                .filter(Objects::nonNull)
//                .allMatch(p -> p.getPinpp().equals(pinpp))) {
//                    throw new CourtGeneralException(ErrorCode.VIOLATOR_CANT_BE_VICTIM_IN_THIS_CASE);
//                }
//    }
}
