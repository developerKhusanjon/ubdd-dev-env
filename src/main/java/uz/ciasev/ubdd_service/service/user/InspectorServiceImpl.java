package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm.ExternalInspectorRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.criminal_case.TransferToCriminalCaseRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.manual_material.ManualCourtMaterialDTO;
import uz.ciasev.ubdd_service.entity.ExternalInspector;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.dict.OrganDictionaryService;

@Service
@RequiredArgsConstructor
public class InspectorServiceImpl implements InspectorService {

    private final OrganDictionaryService organService;

    @Override
    public ExternalInspector buildMibInspector(ExternalInspectorRequestDTO requestDTO) {
        return ExternalInspector.builder()
                .user(null)
                .fio(requestDTO.getFullName())
                .info(requestDTO.getPosition().getDefaultName() + " " + requestDTO.getFullName())
                .region(requestDTO.getRegion())
                .district(requestDTO.getDistrict())
                .organ(organService.getMibOrgan())
                .department(null)
                .position(requestDTO.getPosition())
                .rank(requestDTO.getRank())
                .workCertificate(requestDTO.getWorkCertificate())
                .build();
    }

    @Override
    public ExternalInspector buildCourtInspector(CourtResolutionRequestDTO requestDTO) {
        return ExternalInspector.builder()
                .user(null)
                .fio(requestDTO.getJudgeInfo())
                .info(requestDTO.getJudgeInfo())
                .region(requestDTO.getRegion())
                .district(requestDTO.getDistrict())
                .organ(organService.getCourtOrgan())
                .department(null)
                .position(null)
                .rank(null)
                .workCertificate(null)
                .build();
    }

    @Override
    public ExternalInspector buildCourtInspector(ManualCourtMaterialDTO requestDTO) {
        return ExternalInspector.builder()
                .user(null)
                .fio(requestDTO.getJudgeInfo())
                .info(requestDTO.getJudgeInfo())
                .region(requestDTO.getRegion())
                .district(requestDTO.getDistrict())
                .organ(organService.getCourtOrgan())
                .department(null)
                .position(null)
                .rank(null)
                .workCertificate(null)
                .build();
    }

    @Override
    public ExternalInspector buildCriminalInvestigatorInspector(User user, Organ organ, TransferToCriminalCaseRequestDTO requestDTO) {
        return ExternalInspector.builder()
                .user(user)
                .fio(requestDTO.getConsiderInfo())
                .info(requestDTO.getConsiderInfo())
                .region(requestDTO.getRegion())
                .district(requestDTO.getDistrict())
                .organ(organ)
                .department(null)
                .position(null)
                .rank(null)
                .workCertificate(null)
                .build();
    }
}
