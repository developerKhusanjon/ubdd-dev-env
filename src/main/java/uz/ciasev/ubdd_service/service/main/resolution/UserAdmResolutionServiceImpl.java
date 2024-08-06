package uz.ciasev.ubdd_service.service.main.resolution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SimplifiedResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SimplifiedSingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;
import uz.ciasev.ubdd_service.service.execution.BillingExecutionService;
import uz.ciasev.ubdd_service.service.generator.DecisionNumberGeneratorService;
import uz.ciasev.ubdd_service.service.generator.ResolutionNumberGeneratorService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceActionService;
import uz.ciasev.ubdd_service.service.main.admcase.CalculatingService;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedDecisionDTO;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedSingleResolutionDTO;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.protocol.RepeatabilityService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;
import uz.ciasev.ubdd_service.service.settings.AccountCalculatingService;
import uz.ciasev.ubdd_service.service.validation.ResolutionValidationService;
import uz.ciasev.ubdd_service.service.validation.ValidationService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.CREATE_RESOLUTION;
import static uz.ciasev.ubdd_service.entity.action.ActionAlias.CREATE_SIMPLIFIED_RESOLUTION;

@Service
@RequiredArgsConstructor
public class UserAdmResolutionServiceImpl implements UserAdmResolutionService {

    private final AdmCaseService admCaseService;
    private final ViolatorService violatorService;
    private final CalculatingService calculatingService;
    private final ResolutionValidationService resolutionValidationService;
    private final AdmEventService notificatorService;
    private final ResolutionHelpService helpService;
    private final ResolutionNumberGeneratorService resolutionNumberGeneratorService;
    private final DecisionNumberGeneratorService decisionNumberGeneratorService;
    private final RepeatabilityService repeatabilityService;
    private final DiscountService discountService;
    private final ResolutionService resolutionService;
    private final CompensationService compensationService;
    private final ProtocolService protocolService;



    @Override
    @DigitalSignatureCheck(event = SignatureEvent.RESOLUTION)
    public CreatedSingleResolutionDTO createSingle(User user, Long externalId, SingleResolutionRequestDTO requestDTO) {

        Protocol protocol = protocolService.findByExternalId(user, String.valueOf(externalId));
        AdmCase admCase = admCaseService.getByProtocolId(protocol.getId());

        if (admCase.getOrgan().getId() == 12) {
            Optional<Decision> optionalDecision = resolutionService.getDecisionOfResolutionById(admCase.getId());
            if (optionalDecision.isPresent() &&
                    checkAdmCaseStatusIsSuitable(admCase) &&
                    checkResolutionIsSame(optionalDecision.get(), requestDTO)) {
                return getDtoOfResolutionAlreadyMade(optionalDecision.get());
            }
        }

        calculatingService.checkCanResolveAdmCase(user, admCase);

        return createSingle(user, admCase, requestDTO);
    }

    private CreatedSingleResolutionDTO createSingle(User user, AdmCase admCase, SingleResolutionRequestDTO requestDTO) {

        resolutionValidationService.validateConsider(user, admCase, requestDTO);

        Violator violator = violatorService.findSingleByAdmCaseId(admCase.getId());
        requestDTO.setViolatorId(violator.getId());

        resolutionValidationService.validateDecisionByProtocol(violator, requestDTO);

        Place resolutionPlace = calculateResolutionPlace(user, requestDTO);

        PenaltyPunishment.DiscountVersion discount = discountService.calculateDiscount(requestDTO);

        ResolutionCreateRequest resolution = helpService.buildResolution(requestDTO);
        Decision decision = helpService.buildDecision(violator, requestDTO, null /*penaltyBankAccountSettingsSupplier*/);

        decision.getPenalty().ifPresent(p -> p.setDiscount(discount));

        admCase.setConsiderUser(user);
        admCase.setConsiderInfo(user.getInfo());

        CreatedSingleResolutionDTO data = helpService.resolve(admCase, user, resolutionPlace, resolutionNumberGeneratorService, decisionNumberGeneratorService, resolution, decision);

        Decision savedDecision = data.getCreatedDecision().getDecision();

        repeatabilityService.create(user, savedDecision, requestDTO.getRepeatabilityProtocolsId());

        notificatorService.fireEvent(AdmEventType.ORGAN_RESOLUTION_CREATE, data);
        return data;
    }


    private Place calculateResolutionPlace(User user, SingleResolutionRequestDTO requestDTO) {
        return new Place() {
            @Override
            public Region getRegion() {
                return requestDTO.getRegion();
            }

            @Override
            public District getDistrict() {
                return requestDTO.getDistrict();
            }

            @Override
            public Organ getOrgan() {
                return user.getOrgan();
            }

            @Override
            public Department getDepartment() {
                return requestDTO.getDepartment();
            }
        };
    }


    private CreatedSingleResolutionDTO getDtoOfResolutionAlreadyMade(Decision decision) {
        return new CreatedSingleResolutionDTO(
                decision.getResolution(),
                List.of(new CreatedDecisionDTO(decision, compensationService.findAllByDecisionId(decision.getId()))));
    }

    private boolean checkResolutionIsSame(Decision decision, SingleResolutionRequestDTO requestDTO) {
        return (decision.isActive() &&
                !decision.getPunishments().isEmpty() &&
                ((decision.getArticleViolationTypeId() == null && requestDTO.getArticleViolationType() == null)
                        || (requestDTO.getArticleViolationType() != null
                        && decision.getArticleViolationTypeId().equals(requestDTO.getArticleViolationType().getId()))) &&
                decision.getDecisionTypeId().equals(requestDTO.getDecisionType().getId()) &&
                ((decision.getArticlePartId() == null && requestDTO.getArticlePart() == null)
                        || (requestDTO.getArticlePart() != null && decision.getArticlePartId().equals(requestDTO.getArticlePart().getId()))) &&
                decision.getPunishments().get(0).isActive() &&
                decision.getPunishments().get(0).getPenalty().getAmount().equals(requestDTO.getMainPunishment().getAmount()));
    }

    private boolean checkAdmCaseStatusIsSuitable(AdmCase admCase) {
        switch (admCase.getStatus().getAlias()) {
            case DECISION_MADE:
            case IN_EXECUTION_PROCESS:
            case EXECUTED:
                return true;
            default:
                break;
        }
        return false;
    }

}
