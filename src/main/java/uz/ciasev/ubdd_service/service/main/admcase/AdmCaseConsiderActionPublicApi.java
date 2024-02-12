package uz.ciasev.ubdd_service.service.main.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseConsiderRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.CONSIDER_ADM_CASE;
import static uz.ciasev.ubdd_service.entity.action.ActionAlias.TRANSFER_ADM_CASE_RESPONSIBILITY;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.*;

@Profile("publicapi")
@Service
@RequiredArgsConstructor
public class AdmCaseConsiderActionPublicApi implements AdmCaseConsiderActionHelper {

    private final AdmCaseStatusService admStatusService;

    @Override
    public void apply(User user, AdmCase admCase, AdmCaseConsiderRequestDTO requestDTO) {
        if (CONSIDER_ADM_CASE.equals(getActionName(user, admCase, requestDTO))) {
            admStatusService.setStatus(admCase, CONSIDERING);
            admCase.setConsideredTime(requestDTO.getConsideredTime());
        } else {
            // do nothing
        }
    }

    @Override
    public ActionAlias getActionName(User user, AdmCase admCase, AdmCaseConsiderRequestDTO requestDTO) {
        if (admCase.getStatus().is(REGISTERED) ||
                admCase.getStatus().is(SENT_TO_ORGAN) ||
                admCase.getStatus().is(RETURN_FROM_ORGAN) ||
                admCase.getStatus().is(PREPARE_FOR_COURT) ||
                admCase.getStatus().is(SENT_TO_COURT) ||
                admCase.getStatus().is(RETURN_FROM_COURT)) {
            return CONSIDER_ADM_CASE;
        } else {
            return TRANSFER_ADM_CASE_RESPONSIBILITY;
        }
    }

}
