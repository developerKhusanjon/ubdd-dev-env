package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.DecisionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.PunishmentRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Objects;

import static uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias.TERMINATION;


public abstract class DecisionValidator {

    public boolean isValid(DecisionRequestDTO decisionRequestDTO, ConstraintValidatorContext context) {

        if (decisionRequestDTO.getDecisionType().is(TERMINATION)) {
            return validateTerminationDecision(decisionRequestDTO, context);
        } else {
            return validatePunishmentDecision(decisionRequestDTO, context);
        }
    }

    private boolean validateTerminationDecision(DecisionRequestDTO decisionRequestDTO, ConstraintValidatorContext context) {
        ValidationHelper helper = new ValidationHelper(context);

        if (Objects.isNull(decisionRequestDTO.getTerminationReason())) {
            helper.accept(ErrorCode.TERMINATION_REASON_REQUIRED);
        }

        if (Objects.nonNull(decisionRequestDTO.getMainPunishment())) {
            helper.accept(ErrorCode.MAIN_PUNISHMENT_NOT_ALLOW);
        }

        if (Objects.nonNull(decisionRequestDTO.getAdditionPunishment())) {
            helper.accept(ErrorCode.ADDITION_PUNISHMENT_NOT_ALLOW);
        }

        if (LocalDate.now().isBefore(decisionRequestDTO.getExecutionFromDate())) {
            helper.accept(ErrorCode.EXECUTION_FROM_DATE_IN_FUTURE);
        }

        return helper.isValid();
    }

    private boolean validatePunishmentDecision(DecisionRequestDTO decisionRequestDTO, ConstraintValidatorContext context) {
        ValidationHelper helper = new ValidationHelper(context);

        if (Objects.nonNull(decisionRequestDTO.getTerminationReason())) {
            helper.accept(ErrorCode.TERMINATION_REASON_NOT_ALLOW);
        }

        PunishmentRequestDTO mainPunishment = decisionRequestDTO.getMainPunishment();
        if (Objects.isNull(mainPunishment)) {
            helper.accept(ErrorCode.MAIN_PUNISHMENT_REQUIRED);
        }

        validateAdditionType(decisionRequestDTO, context, helper);

        return helper.isValid();
    }


    private void validateAdditionType(DecisionRequestDTO decision, ConstraintValidatorContext context, ValidationHelper helper) {

        if (decision.getAdditionPunishment() == null
                || decision.getAdditionPunishment() == null
                || decision.getAdditionPunishment().getPunishmentType() == null
                || decision.getMainPunishment().getPunishmentType() == null) {
            return;
        }

        PunishmentTypeAlias additionTypeAlias = decision.getAdditionPunishment().getPunishmentType().getAlias();
/*
  Logic was changed.
  If court send resolution include decision with both same typed main and additional punishment, the only main punishment can be accepted and saved.

     if(Objects.equals(decision.getMainPunishment().getPunishmentType().getAlias(), additionTypeAlias)) {
            helper.accept(ErrorCode.MAIN_AND_ADDITION_PUNISHMENT_TYPE_EQUALS);
        }
*/
        if (Objects.equals(additionTypeAlias, PunishmentTypeAlias.PENALTY)
                || Objects.equals(additionTypeAlias, PunishmentTypeAlias.ARREST)
                || Objects.equals(additionTypeAlias, PunishmentTypeAlias.DEPORTATION)
                || Objects.equals(additionTypeAlias, PunishmentTypeAlias.COMMUNITY_WORK)
//                || Objects.equals(additionTypeAlias, PunishmentTypeAlias.MEDICAL_PENALTY) не факт
        ) {
            helper.accept(ErrorCode.UNACCEPTABLE_ADDITION_PUNISHMENT_TYPE);
        }

        return;
    }
}
