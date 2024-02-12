package uz.ciasev.ubdd_service.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseConsiderRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseCourtDeclarationRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseMoveConsiderTimeRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseSendRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseMovement;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseMovementStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.AdmCaseMovementStatusNoSuitableException;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;
import uz.ciasev.ubdd_service.service.main.admcase.CalculatingService;
import uz.ciasev.ubdd_service.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AdmCaseValidationServiceImpl implements AdmCaseValidationService {

    private final ValidationService validationService;
    private final CalculatingService calculatingService;
    private final UserService userService;

    @Override
    public void validateSend(User user,
                             AdmCase admCase,
                             AdmCaseSendRequestDTO sendRequestDTO) {
        validateBaseSend(
                user,
                admCase,
                sendRequestDTO,
                errors -> {
                    errors.addIf(
                            Objects.equals(user.getOrgan(), sendRequestDTO.getOrgan()) && Objects.equals(user.getDepartment(), sendRequestDTO.getDepartment()),
                            ErrorCode.SEND_CASE_WITHOUT_CHANGING_ORGAN_UNACCEPTABLE
                    );
                }
        );
    }

    @Override
    public void validateInterSend(User user,
                                  AdmCase admCase,
                                  AdmCaseSendRequestDTO sendRequestDTO) {

        validateBaseSend(
                user,
                admCase,
                sendRequestDTO,
                errors -> {
                    errors.addIf(
                            validationService.checkNotSameOrgan(admCase, sendRequestDTO.getOrgan()),
                            ErrorCode.ORGAN_MUST_NOT_BE_CHANGED
                    );
                }
        );
    }

    @Override
    public void validateConsider(User user, AdmCase admCase, AdmCaseConsiderRequestDTO requestDTO) {
        ValidationCollectingError error = new ValidationCollectingError();


        validateConsiderTime(admCase, requestDTO.getConsideredTime(), error);

        error.addIf(
                !user.isConsider(),
                ErrorCode.NOT_CONSIDER_USER
        );

        error.addIf(
                !calculatingService.isConsideredUser(user, admCase),
                ErrorCode.NOT_CONSIDER_OF_ARTICLE_PART
        );

        error.throwErrorIfNotEmpty();
    }

    @Override
    public void validateCourtDeclaration(User user, AdmCase admCase, AdmCaseCourtDeclarationRequestDTO requestDTO) {
        ValidationCollectingError error = new ValidationCollectingError();
        error.addIf(
                validationService.checkCourtConsideringAdditionNotInBasis(requestDTO.getCourtConsideringBasis(), requestDTO.getCourtConsideringAddition()),
                ErrorCode.COURT_CONSIDERING_ADDITION_AND_BASIS_NOT_CONSIST
        );
        error.throwErrorIfNotEmpty();
    }

    @Override
    public void validateMovementCancellation(AdmCaseMovement movement, User user) {
        if (!movement.isSent())
            throw new AdmCaseMovementStatusNoSuitableException(AdmCaseMovementStatusAlias.SENT);

        ValidationCollectingError error = new ValidationCollectingError();
        error.addIf(
                validationService.checkUserHaveNoAccess(user,
                        movement.getFromRegionId(),
                        movement.getFromDistrictId(),
                        movement.getFromOrganId(),
                        movement.getFromDepartmentId()),
                ErrorCode.NO_ACCESS_ON_MOVEMENT_FROM_PLACE
        );
        error.throwErrorIfNotEmpty();
    }

    @Override
    public void validateMoveConsiderTime(User user, AdmCase admCase, AdmCaseMoveConsiderTimeRequestDTO requestDTO) {
        ValidationCollectingError error = new ValidationCollectingError();
        validateConsiderTime(admCase, requestDTO.getConsideredTime(), error);
        error.throwErrorIfNotEmpty();
    }

    private void validateConsiderTime(AdmCase admCase, LocalDateTime considerTime, ValidationCollectingError error) {
        //  todo добавить проверку времени (не позже 15 дней с момента реистрации)
        error.addIf(
                considerTime.isBefore(LocalDateTime.now().minusMinutes(15)),
                ErrorCode.ADM_CASE_CONSIDER_TIME_IN_PAST
        );
    }

    private void validateBaseSend(User user,
                                  AdmCase admCase,
                                  AdmCaseSendRequestDTO sendRequestDTO,
                                  Consumer<ValidationCollectingError> validator) {
        ValidationCollectingError errors = new ValidationCollectingError();

        errors.addIf(
                !calculatingService.isConsideredOrgan(sendRequestDTO.getOrgan(), sendRequestDTO.getDepartment(), admCase),
                ErrorCode.ORGAN_AND_DEPARTMENT_NOT_CONSIDER_ARTICLE_PART
        );

        errors.addIf(
                !userService.isUsersExistIn(sendRequestDTO.getOrgan(), sendRequestDTO.getDepartment(), sendRequestDTO.getRegion(), sendRequestDTO.getDistrict()),
                ErrorCode.NO_USER_IN_GIVEN_PLACE
        );

        validator.accept(errors);

        errors.throwErrorIfNotEmpty();
    }
}