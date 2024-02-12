package uz.ciasev.ubdd_service.service.court.methods;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.FirstCourtAdmCaseRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransferDictionaryService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;

@Service
@RequiredArgsConstructor
public class CourtSendDTOServiceImpl implements CourtSendDTOService {
    private final CourtTransferDictionaryService courtTransferService;
    private final CourtGeneralSendingService courtGeneralSendingService;
    private final ResolutionService resolutionService;

    @Override
    public CourtRequestDTO<FirstCourtAdmCaseRequestDTO> buildFirstMethod(AdmCase admCase) {
        FirstCourtAdmCaseRequestDTO firstCourtAdmCaseRequestDTO = buildAdmCase(admCase);
        return new CourtRequestDTO<>(firstCourtAdmCaseRequestDTO);
    }

    private FirstCourtAdmCaseRequestDTO buildAdmCase(AdmCase admCase) {
        Long admCaseId = admCase.getId();
        Resolution resolution = resolutionService.findActiveByAdmCaseId(admCase.getId()).orElse(null);

        FirstCourtAdmCaseRequestDTO admCaseRequestDTO = new FirstCourtAdmCaseRequestDTO();
        admCaseRequestDTO.setCaseId(admCase.getId());
        admCaseRequestDTO.setAdmissionType(admCase.getCourtConsideringBasisId());
        admCaseRequestDTO.setAdmissionSubType(admCase.getCourtConsideringAdditionId());
        admCaseRequestDTO.setCourt(calcCourtId(admCase.getCourtRegionId(), admCase.getCourtDistrictId()));
        admCaseRequestDTO.setCrimeCaseNumber(admCase.getCourtOutNumber());
        admCaseRequestDTO.setCrimeCaseDate(admCase.getCourtOutDate());
        admCaseRequestDTO.setInvestigatingOrg(admCase.getOrgan().getInvestigatingOrganization());
        admCaseRequestDTO.setInvestigatedOrgName(admCase.getOrgan().getInvestigatingOrganizationName());
        admCaseRequestDTO.setInvestigatorName(admCase.getConsiderInfo());
        admCaseRequestDTO.setInvestigatedOrgId(admCase.getOrgan().getId());
        admCaseRequestDTO.setClaimPlaceType(admCase.getViolationPlaceTypeId());
        admCaseRequestDTO.setLocation(admCase.getViolationPlaceAddress());
        admCaseRequestDTO.setComments(admCase.getFabula());
        admCaseRequestDTO.setHasDecision(resolution != null);

        admCaseRequestDTO.setEvidenceList(courtGeneralSendingService.buildEvidence(admCaseId));
        admCaseRequestDTO.setDefendant(courtGeneralSendingService.buildDefendants(admCase, resolution));
        admCaseRequestDTO.setClaimant(courtGeneralSendingService.buildClaimants(admCaseId));
        admCaseRequestDTO.setParticipants(courtGeneralSendingService.buildParticipants(admCaseId));
        admCaseRequestDTO.setFiles(courtGeneralSendingService.buildFiles(admCaseId));

        return admCaseRequestDTO;
    }

    private Long calcCourtId(Long regionId, Long districtId) {
        CourtTransfer court = courtTransferService.findByRegionAndDistrictIds(regionId, districtId);
        return court.getExternalId();
    }
}
