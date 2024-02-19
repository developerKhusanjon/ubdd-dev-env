package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatus;
import uz.ciasev.ubdd_service.entity.mib.MibCardDocument;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.repository.mib.MibCardDocumentRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionAccessService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias.*;

@Service
@RequiredArgsConstructor
public class MibValidationServiceImpl implements MibValidationService {

    private final MibCardMovementService cardMovementService;
    private final DecisionAccessService decisionAccessService;
    private final MibCardDocumentRepository documentRepository;
    private final AddressService addressService;

    @Override
    public void validateSend(MibExecutionCard card) {
        Optional<MibCardMovement> lastMovement = cardMovementService.findCurrentByCard(card);

        Optional<LocalDate> lastReturnDate = lastMovement
                .filter(m -> Optional.ofNullable(m.getMibCaseStatus()).map(MibCaseStatus::getIsSuspensionArticle).orElse(false))
                .map(MibCardMovement::getReturnTime)
                .map(LocalDateTime::toLocalDate);
        LocalDate validationDate = lastReturnDate.orElse(card.getDecision().getExecutionFromDate());
        validateEndSendPeriod(validationDate);


        ValidationCollectingError errors = new ValidationCollectingError();
        Long organId = card.getDecision().getResolution().getAdmCase().getOrganId();

        if (organId != 12) {
            errors.addIf(
                    Objects.isNull(card.getNotificationId()),
                    ErrorCode.NOTIFICATION_REQUIRED
            );

            if (lastMovement.isPresent()) {
                List<DocumentTypeAlias> documentTypes = documentRepository.findByCardId(card.getId()).stream()
                        .map(MibCardDocument::getDocumentType)
                        .map(DocumentType::getAlias)
                        .collect(Collectors.toList());


                MibCardMovement movement = lastMovement.get();
                errors.addIf(
                        "50".equals(movement.getMibCaseStatusCode()) && !documentTypes.stream().filter(ADDRESS_VERIFICATION::equals).findFirst().isPresent(),
                        ErrorCode.ADDRESS_VERIFICATION_DOCUMENT_REQUIRED
                );
                errors.addIf(
                        "51".equals(movement.getMibCaseStatusCode()) && !documentTypes.stream().filter(PROPERTY_FOR_RECOVERY::equals).findFirst().isPresent(),
                        ErrorCode.PROPERTY_FOR_RECOVERY_DOCUMENT_REQUIRED
                );
            }

            Address actualAddress = addressService.findById(card.getDecision().getViolator().getActualAddressId());
            errors.addIf(actualAddress.getRegionId() == null, ErrorCode.ACTUAL_ADDRESS_INVALID);

            errors.throwErrorIfNotEmpty();
        }
    }

    @Override
    public void checkCreateAvailable(User user, Decision decision) {
        decisionAccessService.checkUserActionPermit(user, ActionAlias.CREATE_MIB_CARD, decision);
        decisionAccessService.checkIsNotCourtOrMaterial(decision);

        if (decision.getPenalty().isEmpty()) {
            throw new MainPunishmentNotPenalty();
        }
//        if (decision.getExecutionFromDate().plusDays(MIB_SEND_MIN_DAYS).isAfter(LocalDate.now())) {throw new EarlyMibSendException();}
//        if (decision.getExecutionFromDate().plusDays(MIB_SEND_MAX_DAYS).isBefore(LocalDate.now())) {throw new LateMibSendException();}
        validateSendPeriod(decision);
    }

    @Override
    public void checkEditAvailable(User user, MibExecutionCard card) {

        if (!card.getOwnerTypeAlias().equals(MibOwnerTypeAlias.DECISION)) {
            throw new ValidationException(
                    ErrorCode.MIB_OWNER_TYPE_NOT_IMPLEMENTED,
                    String.format("Send '%s' to mib not implemented yet. Now you can send only penalty!", card.getOwnerTypeAlias())
            );
        }

        Decision decision = card.getDecision();
        decisionAccessService.checkUserActionPermit(user, ActionAlias.EDIT_MIB_CARD, decision);

//        if (decision.getExecutionFromDate().plusDays(MIB_SEND_MAX_DAYS).isBefore(LocalDate.now())) {throw new LateMibSendException();}
        validateSendPeriod(decision);
    }

    @Override
    public void checkSendAvailable(User user, MibExecutionCard card) {

        if (!card.getOwnerTypeAlias().equals(MibOwnerTypeAlias.DECISION)) {
            throw new ValidationException(
                    ErrorCode.MIB_OWNER_TYPE_NOT_IMPLEMENTED,
                    String.format("Send '%s' to mib not implemented yet. Now you can send only penalty!", card.getOwnerTypeAlias())
            );
        }

        Decision decision = card.getDecision();
        decisionAccessService.checkUserActionPermit(user, ActionAlias.SEND_TO_MIB, decision);
    }

    @Override
    public void checkAutoSendPermitOnDecision(User user, Decision decision) {

        if (user == null || user.getId() == null) {
            decisionAccessService.checkSystemActionPermit(ActionAlias.SEND_TO_MIB, decision);
        }
    }

//    @Override
//    public LocalDate minExecutionDateForNotification() {
//         return LocalDate.now().minusDays(MIB_SEND_MAX_DAYS - 1);
//    }
//
//    @Override
//    public LocalDate maxExecutionDateForNotification() {
//        return LocalDate.now().minusDays(MIB_SEND_MIN_DAYS - 5);
//    }
//
//    @Override
//    public int getMibSendMaxDays() {
//        return MIB_SEND_MAX_DAYS;
//    }
//
//    @Override
//    public int getMibSendMinDays() {
//        return MIB_SEND_MIN_DAYS;
//    }

    public void validateSendPeriod(Decision decision) {
        if (decision.getExecutionFromDate().plusDays(MIB_SEND_MIN_DAYS).isAfter(LocalDate.now())) {
            throw new EarlyMibSendException();
        }
        validateEndSendPeriod(decision.getExecutionFromDate());
    }

    public void validateEndSendPeriod(LocalDate fromDate) {
//        if (decision.getExecutionFromDate().plusDays(MIB_SEND_MIN_DAYS).isAfter(LocalDate.now())) {throw new EarlyMibSendException();}
        //         2022-05-10 Бегзод сказал вообше убрать проверку на слишком позднюю отправку в миб
//        if (fromDate.plusDays(MIB_SEND_MAX_DAYS).isBefore(LocalDate.now())) {throw new LateMibSendException();}
    }
}