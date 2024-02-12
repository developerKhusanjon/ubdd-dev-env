package uz.ciasev.ubdd_service.service.manual;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.ManualCourtMaterialDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.ManualMaterialDecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.PunishmentManualMaterialDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.TerminationManualMaterialDTO;
import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.service.court.CourtInvoiceSendingOrderService;
import uz.ciasev.ubdd_service.service.generator.ValueNumberGeneratorService;
import uz.ciasev.ubdd_service.service.main.resolution.ResolutionHelpService;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedResolutionDTO;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;
import uz.ciasev.ubdd_service.service.user.InspectorService;

import java.util.List;

@Service
@RequiredArgsConstructor
class ManualMaterialResolutionService {

    private final InspectorService inspectorService;
    private final CourtInvoiceSendingOrderService courtInvoiceSendingService;
    private final AdmEventService notificatorService;
    private final ResolutionHelpService helpService;


    Decision editPunishment(Decision decision, PunishmentManualMaterialDTO requestDTO) {
        ManualMaterialDecisionRequestDTO decisionDTO = ManualMaterialDecisionRequestDTO.builder()
                .decisionType(DecisionTypeAlias.PUNISHMENT)
                .mainPunishment(requestDTO.getPunishment())
                .build();

        ManualCourtMaterialDTO materialDTO = requestDTO.getMaterial();
        materialDTO.setDecision(decisionDTO);

        return createCourtResolutionInner(decision, materialDTO);
    }

    Decision terminate(Decision decision, TerminationManualMaterialDTO requestDTO) {
        ManualMaterialDecisionRequestDTO decisionDTO = ManualMaterialDecisionRequestDTO.builder()
                .decisionType(DecisionTypeAlias.TERMINATION)
                .terminationReason(requestDTO.getTerminationReason())
                .build();

        ManualCourtMaterialDTO materialDTO = requestDTO.getMaterial();
        materialDTO.setDecision(decisionDTO);

        return createCourtResolutionInner(decision, materialDTO);
    }


    private Decision createCourtResolutionInner(Decision oldDecision, ManualCourtMaterialDTO requestDTO) {
        AdmCase admCase = oldDecision.getResolution().getAdmCase();
        Violator violator = oldDecision.getViolator();

        // вычисляем поля
        Inspector inspector = inspectorService.buildCourtInspector(requestDTO);
        Place resolutionPlace = inspector;
        ValueNumberGeneratorService numberGeneratorService = buildNumberGeneratorService(requestDTO);

        ResolutionCreateRequest resolution = helpService.buildResolution(requestDTO);

        Decision decision = helpService.buildDecision(
                violator,
                requestDTO.getDecisions().get(0),
                OrganAccountSettings::getEmpty
        );
        decision.setIsSavedPdf(true);

        CreatedResolutionDTO savedData = helpService.resolve(
//                AdmEventType.COURT_RESOLUTION_CREATE,
                admCase,
                inspector,
                resolutionPlace,
                numberGeneratorService,
                numberGeneratorService,
                resolution,
                List.of(Pair.of(decision, List.of())),
                List.of()
        );

        Resolution savedResolution = savedData.getResolution();
        Decision newDecision = savedData.getDecisions().get(0);
        guaranteeInvoices(oldDecision, newDecision);

        notificatorService.fireEvent(AdmEventType.COURT_RESOLUTION_CREATE, savedData);

        return newDecision;
    }

    private ValueNumberGeneratorService buildNumberGeneratorService(ManualCourtMaterialDTO requestDTO) {
        return new ValueNumberGeneratorService(requestDTO.getCaseNumber(), false);
    }

    private void guaranteeInvoices(Decision oldDecision, Decision newDecision) {

        if (newDecision.getPenalty().isPresent()) {
            courtInvoiceSendingService.create(oldDecision.getResolutionId(), newDecision);
        }
    }
}
