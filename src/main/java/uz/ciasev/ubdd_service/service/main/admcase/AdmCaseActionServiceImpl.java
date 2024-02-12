package uz.ciasev.ubdd_service.service.main.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseConsiderRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseMoveConsiderTimeRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseTransferResponsibilityRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseMergeResponseDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.history.AdmCaseRegistrationType;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.main.migration.MainService;
import uz.ciasev.ubdd_service.service.user.UserAdminService;
import uz.ciasev.ubdd_service.service.validation.AdmCaseValidationService;

import java.util.List;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.MOVE_ADM_CASE_CONSIDER_TIME;
import static uz.ciasev.ubdd_service.entity.action.ActionAlias.TRANSFER_ADM_CASE_RESPONSIBILITY;

@Service
@RequiredArgsConstructor
public class AdmCaseActionServiceImpl implements AdmCaseActionService {

    private final AdmCaseService admCaseService;
    private final AdmCaseValidationService admCaseValidationService;
    private final AdmCaseAccessService admCaseAccessService;
    private final UserAdminService userService;
    private final HistoryService historyService;
    private final MainService mainService;
    private final AdmCaseConsiderActionHelper admCaseConsiderActionHelper;
    private final AdmEventService replica;

    @Override
    @Transactional
//    @ReplicaFireEvent(type = AdmEventType.PROTOCOL_CREATE)
    public void considerAdmCase(User user, Long admCaseId, AdmCaseConsiderRequestDTO requestDTO) {
        AdmCase admCase = admCaseService.getById(admCaseId);

        AdmStatusAlias fromStatus = admCase.getStatus().getAlias();

        ActionAlias actionAlias = admCaseConsiderActionHelper.getActionName(user, admCase, requestDTO);
        admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, actionAlias, admCase);
        admCaseValidationService.validateConsider(user, admCase, requestDTO);

        admCaseConsiderActionHelper.apply(user, admCase, requestDTO);
        setUserAsConsiderOfAdmCase(user, admCase, AdmCaseRegistrationType.CONSIDER, fromStatus);
        replica.fireEvent(AdmEventType.PROTOCOL_CONSIDER, admCase);
    }

    @Override
    @Transactional
    public void transferResponsibility(User user, Long admCaseId, AdmCaseTransferResponsibilityRequestDTO requestDTO) {
        AdmCase admCase = admCaseService.getById(admCaseId);
        User newConsider = userService.getByIdForAdmin(user, requestDTO.getConsiderUserId());

        admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, TRANSFER_ADM_CASE_RESPONSIBILITY, admCase);

        setUserAsConsiderOfAdmCase(newConsider, admCase, AdmCaseRegistrationType.TRANSFER, admCase.getStatus().getAlias());
    }

    @Override
    @Transactional
    public void moveConsiderTime(User user, Long admCaseId, AdmCaseMoveConsiderTimeRequestDTO requestDTO) {
        AdmCase admCase = admCaseService.getById(admCaseId);

        admCaseAccessService.checkConsiderActionWithAdmCase(user, MOVE_ADM_CASE_CONSIDER_TIME, admCase);
        admCaseValidationService.validateMoveConsiderTime(user, admCase, requestDTO);

        admCase.setConsideredTime(requestDTO.getConsideredTime());
        admCaseService.update(admCaseId, admCase);
    }

    protected AdmCase setUserAsConsiderOfAdmCase(User user, AdmCase admCase, AdmCaseRegistrationType event, AdmStatusAlias admStatusAlias) {

        admCase.setConsiderUser(user);
        admCase.setConsiderInfo(user.getInfo());

        admCase.setRegion(user.getRegion());
        admCase.setDistrict(user.getDistrict());
        admCase.setOrgan(user.getOrgan());
        admCase.setDepartment(user.getDepartment());

        admCaseService.update(admCase.getId(), admCase);

        historyService.registerAdmCaseEvent(event, admCase, admStatusAlias);

        return admCase;
    }

    @Override
    public AdmCaseMergeResponseDTO mergeAdmCases(User user, Long fromCaseId, Long toCaseId) {
        List<Violator> violators = mainService.organMergeAdmCases(user, fromCaseId, toCaseId);
        Long violatorId = violators.stream().map(Violator::getId).findFirst().orElse(null);
        return new AdmCaseMergeResponseDTO(violatorId);
    }
}
