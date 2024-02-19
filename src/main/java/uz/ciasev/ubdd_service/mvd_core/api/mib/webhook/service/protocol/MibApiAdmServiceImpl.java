package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.service.protocol;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmCaseCreatedResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibAdmTerminationDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.MibResolutionRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibPaymentDTO;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution.MibRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.*;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.history.QualificationRegistrationType;
import uz.ciasev.ubdd_service.entity.mib.PaymentData;
import uz.ciasev.ubdd_service.entity.protocol.Juridic;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;
import uz.ciasev.ubdd_service.exception.MibNoAccessOnResolutionException;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.dict.resolution.DecisionTypeDictionaryService;
import uz.ciasev.ubdd_service.service.dict.resolution.TerminationReasonDictionaryService;
import uz.ciasev.ubdd_service.service.execution.ExecutionCallbackService;
import uz.ciasev.ubdd_service.service.generator.ValueNumberGeneratorService;
import uz.ciasev.ubdd_service.service.execution.ManualBillingService;
import uz.ciasev.ubdd_service.service.juridic.JuridicService;
import uz.ciasev.ubdd_service.service.main.ActorService;
import uz.ciasev.ubdd_service.service.main.PersonDataService;
import uz.ciasev.ubdd_service.service.main.resolution.ResolutionHelpService;
import uz.ciasev.ubdd_service.service.main.resolution.SingleResolutionBuildService;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedSingleResolutionDTO;
import uz.ciasev.ubdd_service.service.protocol.ProtocolCreateRequest;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.resolution.*;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentActionService;
import uz.ciasev.ubdd_service.service.status.StatusService;
import uz.ciasev.ubdd_service.service.user.InspectorService;
import uz.ciasev.ubdd_service.service.validation.ProtocolValidationService;
import uz.ciasev.ubdd_service.utils.ConvertUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.EXECUTED;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.IN_EXECUTION_PROCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class MibApiAdmServiceImpl implements MibApiAdmService {

    private final AdmCaseService admCaseService;
    private final ProtocolValidationService protocolValidationService;
    private final JuridicService juridicService;
    private final ProtocolService protocolService;
    private final ActorService newActorService;
    private final PersonDataService personDataService;
    private final SingleResolutionBuildService resolutionBuildService;
    private final DecisionService decisionService;
    private final ResolutionService resolutionService;
    private final PunishmentActionService punishmentService;
    private final InspectorService inspectorService;
    private final ExecutionCallbackService executionCallbackService;
    private final ResolutionHelpService helpService;
    private final ResolutionActionService resolutionActionService;
    private final HistoryService historyService;
    private final TerminationReasonDictionaryService terminationReasonDictionaryService;
    private final ManualBillingService manualBillingService;
    private final StatusService admStatusService;


    private MibAdmCaseCreatedResponseDTO checkExistingResolution(MibAdmCaseRequestDTO request) {

        String resolutionNumber = Optional.ofNullable(request.getResolution()).map(MibResolutionRequestDTO::getResolutionNumber).orElse(null);
        if (resolutionNumber == null) {
            return null;
        }
        return checkExistingResolution(resolutionNumber);
    }

    private MibAdmCaseCreatedResponseDTO checkExistingResolution(String resolutionNumber) {

        Decision decision = decisionService.findBySeriesAndNumber("", resolutionNumber).orElse(null);
        if (decision == null) {
            return null;
        }

        Protocol protocol = protocolService.findSingleMainByAdmCaseId(decision.getResolution().getAdmCaseId());
        if (protocol == null) {
            return null;
        }

        return new MibAdmCaseCreatedResponseDTO(protocol, decision);
    }

    @Override
    @Transactional(timeout = 90)
    public MibAdmCaseCreatedResponseDTO createAdmCase(MibAdmCaseRequestDTO request) {

        MibAdmCaseCreatedResponseDTO checkResult = checkExistingResolution(request);
        if (checkResult != null) {
            return checkResult;
        }

        ExternalInspector inspector = inspectorService.buildMibInspector(request.getInspector());

        //  СОЗДАНИЕ ПРОТОКОЛА
        ProtocolRequestDTO protocolDTO = request.getProtocol();
        ViolatorCreateRequestDTO violatorRequestDTO = protocolDTO.getViolator();
        Pair<Person, ? extends PersonDocument> personWithDocument = personDataService.provideByPinppOrManualDocument(violatorRequestDTO);
        Person person = personWithDocument.getFirst();
        protocolValidationService.validateViolatorByProtocol(protocolDTO, person);

        AdmCase admCase = admCaseService.createEmptyAdmCase(inspector);

        //  Создание нарушителя
        Pair<Violator, ViolatorDetail> violatorWithDetail = newActorService.createViolatorWithDetail(null, personWithDocument, admCase, violatorRequestDTO, protocolDTO);
        ViolatorDetail violatorDetail = violatorWithDetail.getSecond();

        //  Создание данных должносного лица
        Optional<Juridic> juridic = Optional.ofNullable(protocolDTO.getJuridic()).map(j -> juridicService.create(null, j));

        //  Создание протакола
        ProtocolCreateRequest protocol = protocolDTO.buildProtocol();
        protocol.setJuridic(juridic.orElse(null));
        Protocol savedProtocol = protocolService.create(inspector, violatorDetail, protocol, List.of());
        historyService.registerProtocolQualification(savedProtocol, protocolDTO, QualificationRegistrationType.CREATE);

        //  СОЗДАНИЕ РЕШЕНИЯ
        MibResolutionRequestDTO mibResolutionRequestDTO = request.getResolution();

        ValueNumberGeneratorService numberGenerator = new ValueNumberGeneratorService(mibResolutionRequestDTO.getResolutionNumber(), true);
        Place resolutionPlace = inspector;
        SingleResolutionRequestDTO innerResolutionDTO = resolutionBuildService.buildResolutionForPenalty(
                savedProtocol,
                mibResolutionRequestDTO.getPenaltyAmount(),
                mibResolutionRequestDTO.getResolutionTime(),
                mibResolutionRequestDTO.getInspectorSignature(),
                inspector
        );

        ResolutionCreateRequest resolution = helpService.buildResolution(innerResolutionDTO);

//        resolution.setSimplified(false); // todo не забыть переделать

        Decision decision = helpService.buildDecision(violatorWithDetail.getFirst(), innerResolutionDTO, OrganAccountSettings::getEmpty);
        decision.setIsSavedPdf(true);

        CreatedSingleResolutionDTO data = helpService.resolve(
//                AdmEventType.MIB_RESOLUTION_CREATE,
                admCase,
                inspector,
                resolutionPlace,
                numberGenerator,
                numberGenerator,
                resolution,
                decision,
                List.of()
        );

        Decision savedDecision = data.getCreatedDecision().getDecision();

        return new MibAdmCaseCreatedResponseDTO(savedProtocol, savedDecision);
    }

    @Override
    @Transactional(timeout = 90)
    public void execution(Long decisionId, MibRequestDTO result) {

        Decision decision = decisionService.getById(decisionId);

        if (!decision.getResolution().getOrgan().isMib()) {
            throw new MibNoAccessOnResolutionException(decision.getResolution());
        }

        MibCaseStatusAlias caseStatus = result.getExecutionCaseStatus().getAlias();
        if (MibCaseStatusAlias.TERMINATION.equals(caseStatus)) {
            cancelExecution(decision);
            return;
        }

        List<PaymentData> payments = result.getPayments().stream().map(MibPaymentDTO::buildPaymentData).collect(Collectors.toList());
        LocalDate lastPayDate = result.getPayments().stream().map(MibPaymentDTO::getPaymentTime).map(LocalDateTime::toLocalDate).max(Comparator.naturalOrder()).orElse(LocalDate.now());

        Punishment punishment = decision.getMainPunishment();

        manualBillingService.replaceMibPayments(punishment, payments);
        punishmentService.addExecution(punishment, ExecutorType.MIB_ORGAN, "IIB", new ForceExecutionDTO(lastPayDate, ForceExecutionType.MIB));
        executionCallbackService.executeCallback(decision);

    }

    private void cancelExecution(Decision decision) {

        Punishment punishment = decision.getMainPunishment();

        manualBillingService.deleteMibPayments(punishment);
        punishmentService.deleteExecution(decision.getMainPunishment(), ExecutorType.MIB_ORGAN, "IIB", ForceExecutionType.MIB);

        admStatusService.cancelStatus(decision, EXECUTED, IN_EXECUTION_PROCESS);
        admStatusService.cancelStatus(decision.getResolution(), EXECUTED, IN_EXECUTION_PROCESS);

        executionCallbackService.executeCallbackWithoutLazy(decision);
    }

    @Override
    @Transactional(timeout = 90)
    public void decisionPdf(Long decisionId, byte[] fileContent) {

        Decision decision = decisionService.getById(decisionId);
        Resolution resolution = decision.getResolution();

        if (!resolution.getOrgan().isMib()) {
            throw new MibNoAccessOnResolutionException(resolution);
        }

        resolutionService.setPdfFile(resolution, fileContent);
    }

    @Override
    @Transactional
    public void terminateResolution(MibAdmTerminationDTO dto) {

        MibAdmCaseCreatedResponseDTO checkResult = checkExistingResolution(dto.getResolutionNumber());
        if (checkResult != null) {
            return;
        }

        Decision decision = decisionService.getById(dto.getOffenseId());

        if (!decision.getResolution().isActive()) {
            return;
        }

        ExternalInspector inspector = inspectorService.buildMibInspector(dto.getInspector());


        // CANCEL OLD RESOLUTION ------------------------------------------------------------------

        resolutionActionService.cancelResolutionByMib(decision.getResolution(), dto, inspector);


        // CREATE NEW RESOLUTION (AS IN THE 3D MIB METHOD) ----------------------------------------

        Resolution newResolution = createNewResolutionOfTermination(decision, dto, inspector);

        resolutionService.setPdfFile(newResolution, ConvertUtils.base64ToBytes(dto.getFile()));
    }

    private Resolution createNewResolutionOfTermination(Decision oldDecision, MibAdmTerminationDTO dto, Inspector inspector) {

        SingleResolutionRequestDTO innerResolutionDTO = new SingleResolutionRequestDTO();

        innerResolutionDTO.setDecisionType(DecisionTypeAlias.TERMINATION);
        innerResolutionDTO.setTerminationReason(terminationReasonDictionaryService.getById(6L));
        innerResolutionDTO.setResolutionTime(dto.getDateDoc().atStartOfDay());
        innerResolutionDTO.setExecutionFromDate(dto.getDateDoc());
//        innerResolutionDTO.setSimplified(false);

        //

        ResolutionCreateRequest resolution = helpService.buildResolution(innerResolutionDTO);

        Decision decision = helpService.buildDecision(oldDecision.getViolator(), innerResolutionDTO, OrganAccountSettings::getEmpty);
        decision.setIsSavedPdf(true);

        ValueNumberGeneratorService numberGenerator = new ValueNumberGeneratorService(dto.getResolutionNumber(), true);

        CreatedResolutionDTO data = helpService.resolve(
//                AdmEventType.MIB_RESOLUTION_CREATE,
                oldDecision.getResolution().getAdmCase(),
                inspector,
                inspector,
                numberGenerator,
                numberGenerator,
                resolution,
                decision,
                List.of()
        );
        return data.getResolution();
    }
}
