package uz.ciasev.ubdd_service.service.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeclineRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseSendRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseMovementDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseMovementListResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseMovement;
import uz.ciasev.ubdd_service.entity.history.AdmCaseRegistrationType;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.admcase.AdmCaseMovementRepository;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.dict.*;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;
import uz.ciasev.ubdd_service.service.user.UserDTOService;
import uz.ciasev.ubdd_service.service.validation.AdmCaseValidationService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.*;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.SENT_TO_ORGAN;

@Service
@RequiredArgsConstructor
public class AdmCaseMovementServiceImpl implements AdmCaseMovementService {

    private final AdmCaseMovementRepository admCaseMovementRepository;
    private final AdmCaseAccessService admCaseAccessService;
    private final AdmCaseValidationService admCaseValidationService;
    private final AdmCaseService admCaseService;
    private final AdmCaseStatusService admStatusService;
    private final UserDTOService userDTOService;
    private final AdmEventService admEventService;
    private final HistoryService historyService;
    private final DictionaryService<Organ> organService;
    private final DepartmentDictionaryService departmentService;
    private final RegionDictionaryService regionService;
    private final DistrictDictionaryService districtService;

    @Override
    @Transactional
    public AdmCaseMovement sendAdmCase(User user, Long admCaseId, AdmCaseSendRequestDTO requestDTO) {

        AdmCase admCase = admCaseService.getById(admCaseId);

        admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, SEND_ADM_CASE_TO_ORGAN, admCase);

        admCaseValidationService.validateSend(user, admCase, requestDTO);

        return sendAdmCase(user, admCase, requestDTO);
    }

    @Override
    @Transactional
    public AdmCaseMovement sendAdmCaseFixedOrgan(User user, Long admCaseId, AdmCaseSendRequestDTO requestDTO) {

        AdmCase admCase = admCaseService.getById(admCaseId);

        admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, SEND_ADM_CASE_INTER_ORGAN, admCase);

        admCaseValidationService.validateInterSend(user, admCase, requestDTO);

        return sendAdmCase(user, admCase, requestDTO);
    }

    @Override
    @Transactional
    public void declineAdmCase(User user, Long admCaseId, AdmCaseDeclineRequestDTO requestDTO) {
        AdmCase admCase = admCaseService.getById(admCaseId);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, DECLINE_ADM_CASE, admCase);

        AdmCaseMovement caseMovement = getCurrentMovementByAdmCaseId(admCaseId);

        caseMovement.declineMovement(user, requestDTO.getDeclineReason(), requestDTO.getDeclineComment());
        admCaseMovementRepository.save(caseMovement);

        transferAdmCaseToHomelandPlace(
                admCase,
                caseMovement,
                AdmCaseRegistrationType.DECLINE_ADM_CASE
        );


        admEventService.fireEvent(AdmEventType.RETURN_ADM_CASE_FROM_ORGAN, admCase);
    }

    @Override
    @Transactional
    public void cancel(User user, Long admCaseId) {
        AdmCase admCase = admCaseService.getById(admCaseId);
        AdmCaseMovement caseMovement = getCurrentMovementByAdmCaseId(admCaseId);
        validateCancelSend(user, admCase, caseMovement);

        caseMovement.cancelMovement(user);
        admCaseMovementRepository.save(caseMovement);

        transferAdmCaseToHomelandPlace(
                admCase,
                caseMovement,
                AdmCaseRegistrationType.CANCEL_SEND_TO_ORGAN
        );

    }

    @Override
    public List<AdmCaseMovementListResponseDTO> findAllByAdmCaseId(User user, Long admCaseId) {
        AdmCase admCase = admCaseService.getById(admCaseId);

        return admCaseMovementRepository
                .findByAdmCaseId(admCaseId)
                .stream()
                .map((movement) -> new AdmCaseMovementListResponseDTO(movement, checkIfCancelIsPossible(user, admCase, movement)))
                .collect(Collectors.toList());
    }

    @Override
    public AdmCaseMovementDetailResponseDTO findDetailById(User user, Long id) {
        AdmCaseMovement caseMovement = findById(id);
        AdmCase admCase = admCaseService.getById(caseMovement.getAdmCaseId());
        Boolean isDeclinePossible = checkIfCancelIsPossible(user, admCase, caseMovement);

        return new AdmCaseMovementDetailResponseDTO(
                caseMovement,
                isDeclinePossible,
                userDTOService.findInspectorById(caseMovement.getSendUserId()),
                Optional.ofNullable(caseMovement.getDeclineUserId()).map(userDTOService::findInspectorById).orElse(null),
                Optional.ofNullable(caseMovement.getCancelUserId()).map(userDTOService::findInspectorById).orElse(null)
        );
    }

    private AdmCaseMovement sendAdmCase(User user, AdmCase admCase, AdmCaseSendRequestDTO requestDTO) {

        //admCaseAccessService.checkAccessibleUserActionWithAdmCase(user, SEND_ADM_CASE_TO_ORGAN, admCase);

        Optional<AdmCaseMovement> lastOpenMovement = admCaseMovementRepository.findLastOpenByCaseId(admCase.getId());
        if (lastOpenMovement.isPresent())
            throw new AdmCaseAlreadySendException();

        AdmCaseMovement admCaseMovement = createSendAdmCaseMovement(user, admCase, requestDTO);
        transferAdmCaseTo(
                admCase,
                AdmCaseRegistrationType.SEND_TO_ORGAN,
                SENT_TO_ORGAN,
                requestDTO.getOrgan(),
                requestDTO.getDepartment(),
                requestDTO.getRegion(),
                requestDTO.getDistrict());

        admEventService.fireEvent(AdmEventType.SEND_ADM_CASE_TO_ORGAN, admCase);

        return admCaseMovement;
    }

    public AdmCaseMovement findById(Long id) {
        return admCaseMovementRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(AdmCaseMovement.class, id));
    }

    private AdmCaseMovement createSendAdmCaseMovement(User sendUser, AdmCase admCase, AdmCaseSendRequestDTO requestDTO) {
        AdmCaseMovement admCaseMovement = new AdmCaseMovement(
                admCase,
                sendUser,
                requestDTO.getOrgan(),
                requestDTO.getDepartment(),
                requestDTO.getRegion(),
                requestDTO.getDistrict());

        return admCaseMovementRepository.save(admCaseMovement);
    }

    private AdmCaseMovement getCurrentMovementByAdmCaseId(Long admCaseId) {
        return admCaseMovementRepository.findLastOpenByCaseId(admCaseId).orElseThrow(AdmCaseNotArrivedException::new);
    }

    private void transferAdmCaseToHomelandPlace(AdmCase admCase, AdmCaseMovement caseMovement, AdmCaseRegistrationType eventType) {
        Organ fromOrgan = organService.getById(caseMovement.getFromOrganId());
        Department fromDepartment = Optional.ofNullable(caseMovement.getFromDepartmentId()).map(departmentService::getById).orElse(null);
        Region fromRegion = Optional.ofNullable(caseMovement.getFromRegionId()).map(regionService::getById).orElse(null);
        District fromDistrict = Optional.ofNullable(caseMovement.getFromDistrictId()).map(districtService::getById).orElse(null);

        transferAdmCaseTo(
                admCase,
                eventType,
                AdmStatusAlias.RETURN_FROM_ORGAN,
                fromOrgan,
                fromDepartment,
                fromRegion,
                fromDistrict
        );
    }

    private void transferAdmCaseTo(AdmCase admCase,
                                   AdmCaseRegistrationType eventType,
                                   AdmStatusAlias admStatusAlias,
                                   Organ organ,
                                   Department department,
                                   Region region,
                                   District district) {
        AdmStatusAlias fromStatus = admCase.getStatus().getAlias();

        admStatusService.setStatus(admCase, admStatusAlias);
        admCase.setConsiderUser(null);
        admCase.setConsideredTime(null);
        admCase.setConsiderInfo(null);
        admCase.setOrgan(organ);
        admCase.setDepartment(department);
        admCase.setRegion(region);
        admCase.setDistrict(district);

        admCaseService.update(admCase.getId(), admCase);

        historyService.registerAdmCaseEvent(eventType, admCase, fromStatus);
    }

    private void validateCancelSend(User user, AdmCase admCase, AdmCaseMovement caseMovement) {
        admCaseAccessService.checkPermitActionWithAdmCase(CANCEL_SEND_ADM_CASE_TO_ORGAN, admCase);
        admCaseValidationService.validateMovementCancellation(caseMovement, user);
    }

    private boolean checkIfCancelIsPossible(User user, AdmCase admCase, AdmCaseMovement caseMovement) {
        try {
            validateCancelSend(user, admCase, caseMovement);
        } catch (
                AdmCaseStatusNoSuitableException |
                AdmCaseMovementStatusNoSuitableException |
                ValidationCollectingError e) {
            return false;
        }

        return true;
    }
}
