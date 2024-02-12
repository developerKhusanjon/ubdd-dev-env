package uz.ciasev.ubdd_service.service.main.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseConsiderRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.CONSIDER_ADM_CASE;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.CONSIDERING;


@Profile("!publicapi")
@Service
@RequiredArgsConstructor
public class AdmCaseConsiderActionCore implements AdmCaseConsiderActionHelper {

    private final AdmCaseAccessService admCaseAccessService;
    private final AdmCaseStatusService admStatusService;

    @Override
    public void apply(User user, AdmCase admCase, AdmCaseConsiderRequestDTO requestDTO) {
        admStatusService.setStatus(admCase, CONSIDERING);
        admCase.setConsideredTime(requestDTO.getConsideredTime());
    }

    @Override
    public ActionAlias getActionName(User user, AdmCase admCase, AdmCaseConsiderRequestDTO requestDTO) {
        return CONSIDER_ADM_CASE;
    }

}
