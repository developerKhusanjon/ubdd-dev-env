package uz.ciasev.ubdd_service.service.user;

import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.ExternalInspectorRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case.TransferToCriminalCaseRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.ManualCourtMaterialDTO;
import uz.ciasev.ubdd_service.entity.ExternalInspector;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.user.User;

public interface InspectorService {

    ExternalInspector buildMibInspector(ExternalInspectorRequestDTO inspectorRequestDTO);

    ExternalInspector buildCourtInspector(CourtResolutionRequestDTO courtResolutionRequestDTO);

    ExternalInspector buildCourtInspector(ManualCourtMaterialDTO requestDTO);

    ExternalInspector buildCriminalInvestigatorInspector(User user, Organ organ, TransferToCriminalCaseRequestDTO requestDTO);
}