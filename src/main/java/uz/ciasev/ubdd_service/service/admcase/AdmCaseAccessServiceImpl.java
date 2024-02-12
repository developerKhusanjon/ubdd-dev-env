package uz.ciasev.ubdd_service.service.admcase;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.action.AdmStatusPermittedAction;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.AdmCaseStatusNoSuitableException;
import uz.ciasev.ubdd_service.exception.DeletedAdmCaseException;
import uz.ciasev.ubdd_service.exception.NoAccessOnObjectException;
import uz.ciasev.ubdd_service.exception.NotConsiderOfAdmCaseException;
import uz.ciasev.ubdd_service.service.status.AdmActionService;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import java.util.List;
import java.util.Optional;

//@Service
@RequiredArgsConstructor
public class AdmCaseAccessServiceImpl implements AdmCaseAccessService {

    private final ValidationService validationService;
    private final AdmActionService admActionService;

    @Override
    public void checkAccessOnAdmCase(User user, AdmCase admCase) {
        if (isHasNoAccessOnAdmCase(user, admCase)) {
            throw new NoAccessOnObjectException(user, admCase);
        }
    }

    @Override
    public void checkAccessOnAdmCaseNoOrgan(User user, AdmCase admCase) {
        if (isHasNoAccessOnAdmCaseNoOrgan(user, admCase)) {
            throw new NoAccessOnObjectException(user, admCase);
        }
    }

    @Override
    public void checkConsiderActionWithAdmCase(User user, ActionAlias actionAlias, AdmCase admCase) {

        if (admCase.isDeleted()) {
            throw new DeletedAdmCaseException();
        }

        checkAccessOnAdmCase(user, admCase);
        Optional<AdmStatusPermittedAction> permittedOpt = admActionService.getActionPermitForStatus(actionAlias, admCase.getStatus().getId());


        if (permittedOpt.isEmpty()) {
            throw new AdmCaseStatusNoSuitableException(admCase.getStatus().getAlias(), actionAlias);
        }

        if (user.isSuperConsider()) {
            return;
        }

        if (permittedOpt.get().getConsidererOnly()) {
            checkConsiderOfAdmCase(user, admCase);
        }

    }

    @Override
    public void checkAccessibleUserActionWithAdmCase(User user, ActionAlias actionAlias, AdmCase admCase) {
        checkConsiderActionWithAdmCase(user, actionAlias, admCase);
    }

    @Override
    public void checkPermitActionWithAdmCase(ActionAlias actionAlias, AdmCase admCase) {
        if (!admActionService.isCaseActionPermitForStatus(actionAlias, admCase.getStatus().getId())) {
            throw new AdmCaseStatusNoSuitableException(admCase.getStatus().getAlias(), actionAlias);
        }
    }

    @Override
    public List<ActionAlias> calculatePermittedActions(User user, AdmCase admCase) {
        if (isHasNoAccessOnAdmCase(user, admCase)) {
            return List.of();
        }

        if (user.isSuperConsider() || isConsiderOfAdmCase(user, admCase)) {
            return admActionService.findPermittedActionForStatus(admCase.getStatus().getId());
        }

        return admActionService.findNotConsiderActionForStatus(admCase.getStatus().getId());
    }

    @Override
    public void checkConsiderOfAdmCase(User user, AdmCase admCase) {
        if (!isConsiderOfAdmCase(user, admCase)) {
            throw new NotConsiderOfAdmCaseException(user, admCase);
        }
    }

    @Override
    public boolean isConsiderOfAdmCase(User user, AdmCase admCase) {
        return user.getId().equals(admCase.getConsiderUserId());
    }

    @Override
    public boolean isHasNoAccessOnAdmCase(User user, AdmCase admCase) {
        return validationService.checkUserHaveNoAccess(
                user,
                admCase.getRegionId(),
                admCase.getDistrictId(),
                admCase.getOrganId(),
                admCase.getDepartmentId());
    }

    public boolean isHasNoAccessOnAdmCaseNoOrgan(User user, AdmCase admCase) {
        return validationService.checkUserHaveNoAccess(
                user,
                admCase.getRegionId(),
                admCase.getDistrictId(),
                admCase.getDepartmentId());
    }
}