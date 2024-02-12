package uz.ciasev.ubdd_service.service.manual_fix;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.controller_ubdd.SetClaimRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResultDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.court.CourtResult;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.CourtCaseFieldsService;
import uz.ciasev.ubdd_service.service.court.methods.CourtHandlingResponseService;


@Service
@RequiredArgsConstructor
public class CourtManualFixingService {

    private final HistoryService historyService;
    private final AdmCaseService admCaseService;
    private final CourtHandlingResponseService handlingResponseService;
    private final CourtCaseFieldsService caseFieldsService;
    private final AdmCaseAccessService admCaseAccessService;
    @Transactional
    public void handelRegistrationResponse(SetClaimRequestDTO requestDTO) {

        CourtResponseDTO response = new CourtResponseDTO(new CourtResultDTO(CourtResult.SUCCESSFULLY, "Dev endpoint set claim id"), requestDTO.getClaimId());

        AdmCase admCase = admCaseService.getById(requestDTO.getAdmCaseId());

        admCaseAccessService.checkPermitActionWithAdmCase(ActionAlias.FIX_COURT_SET_FIRST_METHOD_RESPONSE, admCase);

        if (admCase.getCourtConsideringBasisId() == null) {
            throw new ValidationException(ErrorCode.ADM_CASE_WAS_NOT_PREPARED_FOR_SEND_TO_COURT);
        }

        caseFieldsService
                .findByCaseId(requestDTO.getAdmCaseId())
                .ifPresent(fields -> {
                    if (fields.getStatus().notOneOf(CourtStatusAlias.RETURNED, CourtStatusAlias.CANCELLED)) {
                        throw new ValidationException(ErrorCode.COURT_STATUS_EXPECT_CASE_ALREADY_IN_COURT);
                    }
                });


        historyService.manualSetOfClimeId(requestDTO.getAdmCaseId(), requestDTO.getClaimId());
        handlingResponseService.handleResponse(response, requestDTO.getAdmCaseId(), admCase.getCourtConsideringBasisId().equals(5L), false);

    }
}
