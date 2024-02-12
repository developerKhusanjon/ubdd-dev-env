package uz.ciasev.ubdd_service.service.violation_event.decision;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.RadarSingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.dict.BankAccountType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.exception.PenaltyNotAllowedByArticlePartException;
import uz.ciasev.ubdd_service.service.execution.BillingExecutionService;
import uz.ciasev.ubdd_service.service.generator.DecisionNumberGeneratorService;
import uz.ciasev.ubdd_service.service.generator.ResolutionNumberGeneratorService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceActionService;
import uz.ciasev.ubdd_service.service.main.resolution.DiscountService;
import uz.ciasev.ubdd_service.service.main.resolution.ResolutionHelpService;
import uz.ciasev.ubdd_service.service.main.resolution.SingleResolutionBuildService;
import uz.ciasev.ubdd_service.service.main.resolution.dto.CreatedSingleResolutionDTO;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentService;
import uz.ciasev.ubdd_service.service.settings.AccountCalculatingService;
import uz.ciasev.ubdd_service.service.settings.ArticleSettingsService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
class ResolutionForViolationEventCreateServiceImpl implements ResolutionForViolationEventCreateService {
    private final InvoiceActionService invoiceService;
    private final ViolatorService violatorService;
    private final PunishmentService punishmentService;
    private final AccountCalculatingService accountCalculatingService;
    private final AdmEventService notificatorService;
    private final BillingExecutionService billingExecutionService;
    private final ResolutionHelpService helpService;
    private final ResolutionNumberGeneratorService resolutionNumberGeneratorService;
    private final DecisionNumberGeneratorService decisionNumberGeneratorService;
    private final DiscountService discountService;
    private final SingleResolutionBuildService resolutionBuildService;
    private final ArticleSettingsService articleSettingsService;

    @Override
    // todo нужна подпись наверное
    public CreatedSingleResolutionDTO create(User user, ViolationEventApiDTO violationEvent, AdmCase admCase, Protocol protocol) {

        RadarSingleResolutionRequestDTO requestDTO = buildResolutionRequest(user, protocol);

        Violator violator = violatorService.getById(protocol.getViolatorDetail().getViolatorId());
        requestDTO.setViolatorId(violator.getId());


        //  вычисляем значения полей
        Place resolutionPlace = calculateResolutionPlace(user, requestDTO);
        PenaltyPunishment.DiscountVersion discount = discountService.calculateDiscount(requestDTO);
        Supplier<OrganAccountSettings> penaltyBankAccountSettingsSupplier = getBankAccountSettingsSupplier(violationEvent, resolutionPlace, requestDTO);

        //  собираем сущности
        ResolutionCreateRequest resolution = helpService.buildResolution(requestDTO);
        Decision decision = helpService.buildDecision(violator, requestDTO, penaltyBankAccountSettingsSupplier);

        decision.getPenalty().ifPresent(p -> p.setDiscount(discount));


        // начиныем сохранять решение
        admCase.setConsiderUser(user);
        admCase.setConsiderInfo(user.getInfo());

        CreatedSingleResolutionDTO data = helpService.resolve(
//                AdmEventType.ORGAN_RESOLUTION_CREATE,
                admCase,
                user,
                resolutionPlace,
                resolutionNumberGeneratorService,
                decisionNumberGeneratorService, // todo отдельная генерация номеров
                resolution,
                decision,
                List.of()
        );

        Decision savedDecision = data.getDecision();

        savedDecision.getPenalty().ifPresent(penaltyPunishment -> generateNewInvoiceForDecision(savedDecision));

        notificatorService.fireEvent(AdmEventType.ORGAN_RESOLUTION_CREATE, data);

        return data;
    }

    private RadarSingleResolutionRequestDTO buildResolutionRequest(User user, Protocol protocol) {
        Long penaltyAmount = articleSettingsService.calculateMaxForDate(protocol.getViolationTime().toLocalDate(),
                false,
                protocol.getArticlePart()).orElseThrow(PenaltyNotAllowedByArticlePartException::new);

        return resolutionBuildService.fillInResolutionForPenalty(
                new RadarSingleResolutionRequestDTO(),
                protocol,
                penaltyAmount,
                LocalDateTime.now(),
                null,
                user
        );
    }


    // todo особая генерация квитанции
    private void generateNewInvoiceForDecision(Decision decision) {
        var mainPunishment = decision.getMainPunishment();
        if (mainPunishment != null && mainPunishment.getPenalty() != null) {
            var invoice = invoiceService.createForPenalty(decision);

            punishmentService.setDiscount(mainPunishment, invoice);

            var penalty = mainPunishment.getPenalty();
//            penalty.setInvoice(invoice);

            // ВАЖНО. Должно вызываться только после сохананеия полей скидки punishmentService.setDiscount(mainPunishment, invoice)
            billingExecutionService.calculateAndSetExecution(mainPunishment);
        }
    }

    private Supplier<OrganAccountSettings> getBankAccountSettingsSupplier(ViolationEventApiDTO violationEvent, Place resolutionPlace, SingleResolutionRequestDTO decision) {
        return () -> {
            ArticlePart articlePart = decision.getArticlePart();
            Long bankAccountTypeId = Optional.ofNullable(decision.getBankAccountType()).map(BankAccountType::getId).orElseGet(() -> resolutionPlace.getOrgan().getDefaultBankAccountTypeId());

            return accountCalculatingService.calculateForOrgan(resolutionPlace, articlePart, bankAccountTypeId);
        };
    }

    private Place calculateResolutionPlace(User user, SingleResolutionRequestDTO requestDTO) {
        return user;
    }
}
