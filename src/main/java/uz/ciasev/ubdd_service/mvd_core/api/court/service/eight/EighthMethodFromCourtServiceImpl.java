package uz.ciasev.ubdd_service.mvd_core.api.court.service.eight;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.MaterialHelpCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.eight.EightCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.exception.court.CourtDecisionNotFoundException;
import uz.ciasev.ubdd_service.repository.invoice.InvoiceRepository;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.methods.CourtGeneralSendingService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.specifications.DecisionSpecifications;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EighthMethodFromCourtServiceImpl implements EighthMethodFromCourtService {

    private final AdmCaseService admCaseService;
    private final DecisionService decisionService;
    private final CourtGeneralSendingService courtGeneralSendingService;
    private final InvoiceRepository invoiceRepository;
    private final DecisionRepository decisionRepository;
    private final DecisionSpecifications decisionSpecifications;
    private final MaterialHelpCourtService materialHelpCourtService;


    @Override
    @Transactional
    public CourtRequestDTO<EightCourtResolutionRequestDTO> sendResolution(String seriesInput, String number) {
//        String series = Objects.requireNonNullElse(seriesInput, "");
//
//        if (number == null) {
//            throw new CourtValidationException("Number for search required");
//        }
//
//
////        Specification<Decision> searchParam;
////
////        if ("KV".equals(series)) {
////            searchParam = decisionSpecifications.withInvoiceSerial(series + number);
////        } else if (series.startsWith("P")) {
////            searchParam = decisionSpecifications.withProtocolSeriesAndNumber(series, number);
////        } else {
////            searchParam = decisionSpecifications.withNumber(number).and(decisionSpecifications.withSeries(series));
////        }
////
////        List<Decision> decision = decisionRepository.findAll(searchParam)
////                .stream().filter(d -> d.getResolution().isActive())
//
//
//        Optional<Decision> otherParamSearch = Optional.empty();
//
//        if ("KV".equals(series)) {
//            otherParamSearch = invoiceRepository.findDecisionByPenaltyInvoiceSerial(series + number);
//        } else if (series.startsWith("P")) {
//            otherParamSearch = decisionRepository.findByProtocolSeriesAndNumber(series, number);
//        }
//
//        Decision decision = otherParamSearch.orElseGet(() ->
//                decisionService
//                .findBySeriesAndNumber(series, number)
//                .orElseThrow(() -> new CourtDecisionNotFoundException(series, number))
//        );

        Decision decision = materialHelpCourtService.findDecisionByCourtSearchParam(seriesInput, number)
                .orElseThrow(() -> new CourtDecisionNotFoundException(seriesInput, number));

        Resolution resolution = decision.getResolution();

        Long caseId = resolution.getAdmCaseId();
        AdmCase admCase = resolution.getAdmCase();

        EightCourtResolutionRequestDTO response = new EightCourtResolutionRequestDTO();
        response.setCaseId(caseId);
        response.setInvestigatorName(resolution.getConsiderInfo());
        response.setInvestigatingOrg(resolution.getOrgan().getInvestigatingOrganization());
        response.setInvestigatedOrgId(resolution.getOrgan().getId());
        response.setInvestigatedOrgName(resolution.getOrgan().getInvestigatingOrganizationName());
        response.setHasDecision(true);
        response.setDecisionIsActive(resolution.isActive());

        response.setDefendant(courtGeneralSendingService.buildDefendants(admCase, resolution));
        response.setClaimant(courtGeneralSendingService.buildClaimants(caseId));
        response.setParticipants(courtGeneralSendingService.buildParticipants(caseId));
        response.setEvidenceList(courtGeneralSendingService.buildEvidence(caseId));
        response.setFiles(courtGeneralSendingService.buildFiles(caseId));

        if (!resolution.isActive()) {
            response.getDefendant().forEach(d -> {
                d.setFirstName("Қарор бекор қилинган");
                d.setDefendantLastName("Қарор бекор қилинган");
                d.setDefendantFirstName("Қарор бекор қилинган");
                d.setDefendantMiddleName("Қарор бекор қилинган");
                d.setDefendantLastNameKir("Қарор бекор қилинган");
                d.setDefendantFirstNameKir("Қарор бекор қилинган");
                d.setDefendantMiddleNameKir("Қарор бекор қилинган");
                d.setArticles(List.of());
                d.setConvictedBeforeArticles(List.of());
            });
        }

        return new CourtRequestDTO<>(response);
    }
}
