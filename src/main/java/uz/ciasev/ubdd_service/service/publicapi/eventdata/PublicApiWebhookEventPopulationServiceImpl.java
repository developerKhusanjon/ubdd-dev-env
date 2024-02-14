package uz.ciasev.ubdd_service.service.publicapi.eventdata;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookType;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.PublicApiWebhookEventDataCourtDTO;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.PublicApiWebhookEventDataDTO;
import uz.ciasev.ubdd_service.service.publicapi.PublicApiWebhookEventService;
import uz.ciasev.ubdd_service.service.publicapi.PublicApiWebhookService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.utils.AdmCaseOrganService;
import uz.ciasev.ubdd_service.service.utils.EntityAdmCaseService;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicApiWebhookEventPopulationServiceImpl implements PublicApiWebhookEventPopulationService {

    private final PublicApiWebhookEventDataService publicApiWebhookEventDataService;
    private final PublicApiWebhookEventService publicApiWebhookEventService;
    private final PublicApiWebhookService publicApiWebhookService;
    private final AdmCaseOrganService entityOrganService;
    private final EntityAdmCaseService entityAdmCaseService;
    private final AdmCaseService admCaseService;

    private void addOldMibEvent(MibCardMovement movement, AdmCase admCase) {
        return;
//        addEvent(
//                PublicApiWebhookType.MIB,
//                admCase.getOrgan(),
//                admCase,
//                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeMibExecutionDTO(movement))
//        );
    }

    @Override
    public void addSendToMibEvent(MibCardMovement movement) {


        AdmCase admCase = entityAdmCaseService.by(movement);

        addEvent(
                PublicApiWebhookType.MIB_SEND,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeMibExecutionDTO(movement))
        );

        addOldMibEvent(movement, admCase);

    }

    @Override
    public void addValidationMibEvent(MibCardMovement movement) {

        AdmCase admCase = entityAdmCaseService.by(movement);

        addEvent(
                PublicApiWebhookType.MIB_VALIDATION,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeMibExecutionDTO(movement))
        );

        addOldMibEvent(movement, admCase);
    }

    @Override
    public void addMibAcceptEvent(MibCardMovement movement) {
// TODO: 10.11.2023 next 2 
        AdmCase admCase = entityAdmCaseService.by(movement);
        try {

            addEvent(
                    PublicApiWebhookType.MIB_ACCEPTED,
                    admCase.getOrgan(),
                    admCase,
                    () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeMibExecutionDTO(movement))
            );
        } catch (NullPointerException e) {
    throw new RuntimeException("PublicApiWebhookEventPopulationServiceImpl addMibAcceptEvent throw NullPointer exception");
        }

        addOldMibEvent(movement, admCase);
    }

    @Override
    public void addMibPaidEvent(MibCardMovement movement) {

        AdmCase admCase = entityAdmCaseService.by(movement);

        addEvent(
                PublicApiWebhookType.MIB_PAID,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeMibExecutionDTO(movement))
        );

        addOldMibEvent(movement, admCase);
    }

    @Override
    public void addMibReturnEvent(MibCardMovement movement) {

        AdmCase admCase = entityAdmCaseService.by(movement);

        addEvent(
                PublicApiWebhookType.MIB_RETURN,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeMibExecutionDTO(movement))
        );

        addOldMibEvent(movement, admCase);
    }

    @Override
    public void addMibExecuteEvent(MibCardMovement movement) {

        AdmCase admCase = entityAdmCaseService.by(movement);

        addEvent(
                PublicApiWebhookType.MIB_EXECUTED,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeMibExecutionDTO(movement))
        );

        addOldMibEvent(movement, admCase);
    }

    @Override
    public void addCourtEvent(PublicApiWebhookEventDataCourtDTO courtEventDTO) {

        AdmCase admCase = admCaseService.getById(courtEventDTO.getCaseId());

        addEvent(
                PublicApiWebhookType.COURT,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(courtEventDTO)
        );
    }

    @Override
    public void addInvoiceEvent(Invoice invoice) {

        AdmCase admCase = entityAdmCaseService.by(invoice);
        Organ organ = admCase.getOrgan();

        addEvent(
                PublicApiWebhookType.INVOICE,
                organ,
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeInvoiceDTO(invoice))
        );
    }

    @Override
    public void addPaymentEvent(Payment payment, Invoice invoice, Violator violator) {

        AdmCase admCase = admCaseService.getByViolator(violator);

        addEvent(
                PublicApiWebhookType.PAYMENT,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makePaymentDTO(payment, invoice, violator))
        );
    }

    @Override
    public void addAdmCaseStatusEvent(AdmCase admCase) {

        Organ organ = admCase.getOrgan();

        addEvent(
                PublicApiWebhookType.ADM_CASE_STATUS_CHANGE,
                organ,
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeAdmCaseStatusDTO(admCase))
        );
    }

    @Override
    public void addDecisionStatusEvent(Decision decision) {

        AdmCase admCase = decision.getResolution().getAdmCase();

        addEvent(
                PublicApiWebhookType.DECISION_STATUS_CHANGE,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeDecisionStatusDTO(decision))
        );
    }

    @Override
    public void addPunishmentStatusEvent(Punishment punishment, ExecutorType executorType) {

        AdmCase admCase = entityAdmCaseService.by(punishment);

        addEvent(
                PublicApiWebhookType.PUNISHMENT_STATUS_CHANGE,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makePunishmentStatusDTO(punishment, executorType))
        );
    }

    @Override
    public void addCompensationStatusEvent(Compensation compensation) {

        AdmCase admCase = entityAdmCaseService.by(compensation);

        addEvent(
                PublicApiWebhookType.COMPENSATION_STATUS_CHANGE,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeCompensationStatusDTO(compensation))
        );
    }

    @Override
    public void addDecisionMadeEvent(Decision decision) {

        AdmCase admCase = decision.getResolution().getAdmCase();

        addEvent(
                PublicApiWebhookType.DECISION_MADE,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeDecisionDTO(decision))
        );
    }

    @Override
    public void addDecisionsMadeEvent(CreatedResolutionDTO createdResolution) {

        AdmCase admCase = createdResolution.getResolution().getAdmCase();

        addEvents(
                PublicApiWebhookType.DECISION_MADE,
                admCase.getOrgan(),
                admCase,
                () -> createdResolution.getDecisions()
                        .stream()
                        .map(publicApiWebhookEventDataService::makeDecisionDTO)
                        .map(PublicApiWebhookEventDataDTO::new)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void addDecisionsMadeEvent(AdmCase admCase, Supplier<List<Decision>> decisionSupplier) {

        addEvents(
                PublicApiWebhookType.DECISION_MADE,
                admCase.getOrgan(),
                admCase,
                () -> decisionSupplier.get()
                        .stream()
                        .map(publicApiWebhookEventDataService::makeDecisionDTO)
                        .map(PublicApiWebhookEventDataDTO::new)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void addDecisionCancelEvent(Decision decision) {

        AdmCase admCase = decision.getResolution().getAdmCase();

        addEvent(
                PublicApiWebhookType.DECISION_CANCELED,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeDecisionDTO(decision))
        );
    }

    @Override
    public void addMibCancelExecutionEvent(MibCardMovement movement) {
        AdmCase admCase = entityAdmCaseService.by(movement);

        addEvent(
                PublicApiWebhookType.MIB_CANCEL_EXECUTION,
                admCase.getOrgan(),
                admCase,
                () -> new PublicApiWebhookEventDataDTO(publicApiWebhookEventDataService.makeMibExecutionDTO(movement))
        );

        addOldMibEvent(movement, admCase);
    }

    private void addEvent(PublicApiWebhookType type, Organ organ, AdmCase admCase, Supplier<PublicApiWebhookEventDataDTO> mainDTO) {

        if (!publicApiWebhookService.isOrganSubscribeToEvent(organ, type)) {
            return;
        }

        PublicApiWebhookEventDataDTO dto = mainDTO.get();

        JsonNode jsonData = publicApiWebhookEventDataService.convertToJson(dto);

        publicApiWebhookEventService.add(type, organ, admCase, jsonData);
    }

    private void addEvents(PublicApiWebhookType type, Organ organ, AdmCase admCase, Supplier<List<PublicApiWebhookEventDataDTO>> mainDTO) {

//        if (!publicApiWebhookService.isPublicApiWebhookOrgan(organ)) {
        if (!publicApiWebhookService.isOrganSubscribeToEvent(organ, type)) {
            return;
        }

        for (PublicApiWebhookEventDataDTO dto : mainDTO.get()) {

            JsonNode jsonData = publicApiWebhookEventDataService.convertToJson(dto);

            publicApiWebhookEventService.add(type, organ, admCase, jsonData);
        }
    }
}
