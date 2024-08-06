package uz.ciasev.ubdd_service.service.main.protocol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.ChangeReasonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.participant.ParticipantProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.*;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.EditProtocolVehicleNumberRegistrationResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.ProtocolVehicleNumberEditDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.history.QualificationRegistrationType;
import uz.ciasev.ubdd_service.entity.participant.Participant;
import uz.ciasev.ubdd_service.entity.participant.ParticipantDetail;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.victim.Victim;
import uz.ciasev.ubdd_service.entity.victim.VictimDetail;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartRepository;
import uz.ciasev.ubdd_service.repository.history.EditProtocolVehicleNumberRegistrationRepository;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolRepository;
import uz.ciasev.ubdd_service.service.AdmCaseChangeReasonService;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.service.juridic.JuridicService;
import uz.ciasev.ubdd_service.service.main.ActorService;
import uz.ciasev.ubdd_service.service.main.PersonDataService;
import uz.ciasev.ubdd_service.service.main.migration.SeparationService;
import uz.ciasev.ubdd_service.service.participant.ParticipantDetailService;
import uz.ciasev.ubdd_service.service.ubdd_data.UbddDataToProtocolBindService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolSearchService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.protocol.RepeatabilityService;
import uz.ciasev.ubdd_service.service.validation.ProtocolValidationService;
import uz.ciasev.ubdd_service.service.victim.VictimDetailService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProtocolMainServiceImpl implements ProtocolMainService {

    private final AdmCaseAccessService admCaseAccessService;
    private final ProtocolValidationService protocolValidationService;
    private final AdmCaseService admCaseService;
    private final JuridicService juridicService;
    private final ProtocolService protocolService;
    private final RepeatabilityService protocolRepeatabilityService;
    private final ActorService newActorService;
    private final PersonDataService personDataService;
    private final SeparationService separationService;
    private final VictimDetailService victimDetailService;
    private final ParticipantDetailService participantDetailService;
    private final AdmCaseChangeReasonService admCaseChangeReasonService;
    private final ProtocolSearchService protocolSearchService;
    private final HistoryService historyService;
    private final ProtocolRepository protocolRepository;
    private final UbddDataToProtocolBindService ubddDataToProtocolBindService;
    private final EditProtocolVehicleNumberRegistrationRepository editProtocolVehicleNumberRegistrationRepository;

    private final ArticlePartRepository articlePartRepository;


    @Override
    @Transactional
    public VictimDetail addVictimToProtocol(User user, Long protocolId, VictimProtocolRequestDTO victimRequestDTO) {
        AdmCase admCase = admCaseService.getByProtocolId(protocolId);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.ADD_VICTIM_TO_PROTOCOL, admCase);
        Protocol protocol = protocolService.findById(protocolId);

        Pair<Person, ? extends PersonDocument> personWithDocument = personDataService.provideByPinppOrManualDocument(victimRequestDTO);
        Person person = personWithDocument.getFirst();

        protocolValidationService.validateVictimByProtocol(protocol, person, victimRequestDTO);
//        protocolValidationService.validateVictimUniqueness(protocol, person); перенесла внутрь метода

        Pair<Victim, VictimDetail> victimWithDetailPair = newActorService.createVictimWithDetail(user, personWithDocument, admCase, protocol, victimRequestDTO);

        return victimWithDetailPair.getSecond();
    }

    @Override
    @Transactional
    public ParticipantDetail addParticipantToProtocol(User user,
                                                      Long protocolId,
                                                      ParticipantProtocolRequestDTO participantRequestDTO) {
        AdmCase admCase = admCaseService.getByProtocolId(protocolId);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.ADD_PARTICIPANT_TO_PROTOCOL, admCase);
        Protocol protocol = protocolService.findById(protocolId);

        Pair<Person, ? extends PersonDocument> personWithDocument = personDataService.provideByPinppOrManualDocument(participantRequestDTO);
        Person person = personWithDocument.getFirst();

        protocolValidationService.validateParticipantByProtocol(protocol, person, participantRequestDTO);
//        protocolValidationService.validateParticipantUniqueness(protocol, person, participantRequestDTO); перенесли внутрь

        Pair<Participant, ParticipantDetail> participantWithDetailPair = newActorService.createParticipantWithDetail(user, personWithDocument, admCase, protocol, participantRequestDTO);

        return participantWithDetailPair.getSecond();
    }

    @Override
    @Transactional
    @DigitalSignatureCheck(event = SignatureEvent.VICTIM_DELETE)
    public VictimDetail deleteVictimDetailFromProtocol(User user, Long victimId, Long protocolId, ChangeReasonRequestDTO changeReason) {

        protocolValidationService.validateVictimHasNoDamage(protocolId, victimId);

        VictimDetail victimDetail = victimDetailService.findByVictimIdAndProtocolId(victimId, protocolId);
        AdmCase admCase = admCaseService.getById(victimDetail.getVictim().getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.DELETE_VICTIM_FROM_PROTOCOL, admCase);

        newActorService.deleteVictimDetail(victimDetail);

        admCaseChangeReasonService.create(user, admCase, victimDetail, changeReason);

        return victimDetail;
    }

    @Override
    @Transactional
    public void deleteParticipantFromProtocol(User user, Long participantId, Long protocolId, ChangeReasonRequestDTO changeReason) {
        ParticipantDetail participantDetail = participantDetailService.findByParticipantIdAndProtocolId(participantId, protocolId);
        AdmCase admCase = admCaseService.getById(participantDetail.getParticipant().getAdmCaseId());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.DELETE_PARTICIPANT_FROM_PROTOCOL, admCase);

        newActorService.deleteParticipantDetail(participantDetail);

        admCaseChangeReasonService.create(user, admCase, participantDetail, changeReason);
    }

    @Override
    @Transactional
    public void makeProtocolMain(User user, Long protocolId) {
//        Protocol protocol = protocolService.getDTOById(protocolId);
//        if (!protocol.isActive()) {
//            throw new ProtocolNotActiveException();
//        }
//
//        AdmCase fromAdmCase = admCaseService.getByProtocolId(protocolId);
//        admCaseAccessService.checkConsiderActionWithAdmCase(user, CHANG_MAIN_PROTOCOL, fromAdmCase);
        Protocol protocol = getProtocolForAction(user, protocolId, CHANG_MAIN_PROTOCOL);

        separationService.resetViolatorMainProtocols(protocol);
    }

    @Override
    @Transactional
    public Protocol editProtocolQualification(User user, Long protocolId, QualificationRequestDTO requestDTO) {

        Protocol protocol = protocolService.findById(protocolId);

        if (requestDTO.getArticlePart() == null) {
            Long articleId = requestDTO.retrieveArticle().getId();
            List<ArticlePart> articleParts = articlePartRepository.findAllByArticleId(articleId);
            if (!articleParts.isEmpty()) {
                requestDTO.attachArticlePart(articleParts.get(0));
                requestDTO.attachArticleViolationType(null);
            }
        }

        historyService.registerProtocolQualification(protocol, requestDTO, QualificationRegistrationType.EDIT_QUALIFICATION);

        Long oldJuridicId = protocol.getJuridicId();
        Juridic juridic = juridicService.replace(user, protocol.getJuridicId(), requestDTO.getJuridic());
        protocol.setJuridic(juridic);

        protocolRepeatabilityService.replace(user, protocol, requestDTO.getRepeatabilityProtocolsId());

        Protocol savedProtocol = protocolService.update(
                requestDTO.applyTo(protocol),
                requestDTO.getAdditionArticles().stream().map(QualificationArticleRequestDTO::buildProtocolArticle).collect(Collectors.toList())
        );

        juridicService.delete(user, oldJuridicId);

        return savedProtocol;
    }

    @Override
    @Transactional
    public void editProtocolMainArticle(User user, Long protocolId, Long protocolArticleId) {
        Protocol protocol = getProtocolForAction(user, protocolId, CHECK_PROTOCOL_MAIN_ARTICLE);
        ProtocolArticle protocolArticle = protocolService.getProtocolArticleById(protocolArticleId);

        protocolValidationService.validateMainArticle(user, protocol, protocolArticle);

        historyService.registerEditProtocolMainArticle(user, protocol, protocolArticle);

        protocolService.editMainArticle(protocol, protocolArticle);
    }

    @Override
    @Transactional
    public AdmCase separateProtocol(User user, Long protocolId) {
        AdmCase fromAdmCase = admCaseService.getByProtocolId(protocolId);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, SEPARATE_PROTOCOL, fromAdmCase);


        if (protocolSearchService.getCountByAdmCaseId(fromAdmCase.getId()) <= 1) {
            throw new ValidationException(ErrorCode.SEPARATE_LAST_PROTOCOL_FROM_ADM_CASE_UNACCEPTABLE);
        }

        Protocol separatedProtocol = protocolService.findById(protocolId);

        AdmCase toAdmCase = admCaseService.createCopyAdmCase(user, fromAdmCase);
        return separationService.separateProtocols(fromAdmCase, toAdmCase, List.of(separatedProtocol));
    }

    @Override
    @Transactional
    public void editProtocolViolationTime(User user, Long protocolId, ProtocolEditRequestDTO requestDTO) {
        Protocol protocol = getProtocolForAction(user, protocolId, EDIT_PROTOCOL_VIOLATION_TIME);

        if (!protocolValidationService.validateFirstDateLessThenSecond(requestDTO.getRegistrationTime(), protocol.getCreatedTime())) {
            throw new ValidationException(ErrorCode.REGISTRATION_TIME_MORE_THAN_CREATED_TIME);
        }

        protocol.setRegistrationTime(requestDTO.getRegistrationTime());
        protocol.setViolationTime(requestDTO.getViolationTime());

        protocolRepository.save(protocol);
    }

    @Override
    @Transactional
    public void setTrackNumber(User user, Long protocolId, String trackNumber) {
        Protocol protocol = getProtocolForAction(user, protocolId, SET_PROTOCOL_TRACK_NUMBER);

//        List<AdmCase> cases = protocolRepository.findAdmCasesByProtocolId(protocolId);
//        cases.forEach(ac -> admCaseAccessService.checkConsiderActionWithAdmCase(user, ActionAlias.SET_PROTOCOL_TRACK_NUMBER, ac));
//
//        protocolRepository.setTrackNumber(protocolId, trackNumber);

        protocol.setTrackNumber(trackNumber);

        protocolRepository.save(protocol);
    }

    @Override
    public void editPaperProps(User user, Long protocolId, PaperPropsRequestDTO requestDTO) {
        Protocol protocol = getProtocolForAction(user, protocolId, EDIT_PROTOCOL_PAPER_PROPS);

        if (protocol.getOldNumber() == null || protocol.getOldNumber().isBlank()) {
            throw new ValidationException(ErrorCode.PROTOCOL_CURRENT_OLD_NUMBER_IS_EMPTY);
        }

        protocolValidationService.checkBlankSeriesAndNumberByOrgan(protocol.getOrgan(), requestDTO, Optional.ofNullable(protocol));

        protocol.setOldNumber(requestDTO.getBlankNumber());
        protocol.setOldSeries(requestDTO.getBlankSeries());

        protocolRepository.save(protocol);
    }

    @Override
    @Transactional
    public void protocolVehicleNumberEdit(User user, Long protocolId, ProtocolVehicleNumberEditDTO dto) {

        Protocol protocol = getProtocolForAction(user, protocolId, EDIT_PROTOCOL_VEHICLE_NUMBER);

        //validateVehicleNumberEdit(protocol, dto); // TODO: позже эту валидацию нужно будет вернуть

        String oldNumber = protocol.getVehicleNumber();

        // SAVE PROTOCOL
        protocol.setVehicleNumber(dto.getNewVehicleNumber());
        protocolRepository.save(protocol);

        // SAVE BIND
        ubddDataToProtocolBindService.save(user, protocolId, dto.getUbddDataBind());

        // REGISTER HISTORY
        historyService.registerProtocolVehicleNumberEdit(user, protocolId, oldNumber, dto);
    }

    private void validateVehicleNumberEdit(Protocol protocol, ProtocolVehicleNumberEditDTO dto) {

        String oldNumber = Optional.ofNullable(protocol.getVehicleNumber()).map(String::trim).orElse("");
        String newNumber = Optional.ofNullable(dto.getNewVehicleNumber()).map(String::trim).orElse("");

        if (oldNumber.equals(newNumber)) {
            throw new ValidationException(ErrorCode.VEHICLE_NUMBER_IDENTICAL);
        }
    }

    private Protocol getProtocolForAction(User user, Long protocolId, ActionAlias actionAlias) {

        AdmCase admCase = admCaseService.getByProtocolId(protocolId);

        admCaseAccessService.checkConsiderActionWithAdmCase(user, actionAlias, admCase);

        return protocolService.findById(protocolId);
    }

    @Override
    public List<EditProtocolVehicleNumberRegistrationResponseDTO> findProtocolVehicleNumberHistory(User user, Long protocolId) {

        return editProtocolVehicleNumberRegistrationRepository.findAllByProtocolIdOrderByCreatedTime(protocolId)
                .stream()
                .map(EditProtocolVehicleNumberRegistrationResponseDTO::new)
                .collect(Collectors.toList());
    }
}