package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.repository.court.CourtMaterialDecisionRepository;
import uz.ciasev.ubdd_service.repository.court.CourtMaterialRepository;
import uz.ciasev.ubdd_service.service.court.CourtInvoiceSendingOrderService;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialFieldsService;
import uz.ciasev.ubdd_service.service.court.material.CourtMaterialService;
import uz.ciasev.ubdd_service.service.dict.resolution.ReasonCancellationService;
import uz.ciasev.ubdd_service.service.execution.CourtExecutionService;
import uz.ciasev.ubdd_service.service.main.resolution.CourtResolutionMainService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionActionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;

@Getter
@Component
@RequiredArgsConstructor
public class CourtActionRequirementServices {
    private final CourtMaterialDecisionRepository courtMaterialDecisionRepository;
    private final CourtMaterialRepository courtMaterialRepository;
    private final ResolutionService resolutionService;
    private final ResolutionActionService resolutionActionService;
    private final AdmCaseStatusService statusService;
    private final CourtResolutionMainService resolutionMainService;
    private final CourtExecutionService courtExecutionService;
    private final ReasonCancellationService reasonCancellationService;
    private final CourtMaterialFieldsService courtMaterialFieldsService;
    private final CourtMaterialService courtMaterialService;
    private final CourtInvoiceSendingOrderService courtInvoiceSendingService;
}
