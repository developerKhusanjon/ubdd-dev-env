package uz.ciasev.ubdd_service.service.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.CourtResolutionException;
import uz.ciasev.ubdd_service.exception.ResolutionNotActiveException;
import uz.ciasev.ubdd_service.repository.resolution.compensation.CompensationRepository;
import uz.ciasev.ubdd_service.repository.resolution.punishment.PunishmentRepository;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartDictionaryService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceActionService;
import uz.ciasev.ubdd_service.service.invoice.InvoiceService;
import uz.ciasev.ubdd_service.service.resolution.compensation.CompensationService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.settings.AccountCalculatingService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilsServiceImpl implements UtilsService {

    private final InvoiceActionService invoiceActionServiceService;
    private final DecisionService decisionService;
    private final CompensationService compensationService;
    private final AccountCalculatingService accountCalculatingService;
    private final PunishmentRepository punishmentRepository;
    private final CompensationRepository compensationRepository;
    private final InvoiceService invoiceServiceService;
    private final ArticlePartDictionaryService articlePartService;

    @Override
    public void generateInvoiceForOld(User user, Long decisionId) {
        Decision decision = decisionService.getById(decisionId);

        if (!decision.getResolution().isActive()) throw new ResolutionNotActiveException();
        if (decision.getResolution().getOrgan().isCourt()) throw new CourtResolutionException();
        // 2022-08-22 Бегзод сказал отключить эту валидацию
        //  if (decision.getCreatedTime().toLocalDate().isAfter(GlobalConstants.DAY_X)) throw new NotOldObjectException();

        // не обьяснимо, почему исопльзоватлся юзер(с меняющейся гегорафией). Возможно раньше расчет был реализован по пользователю
//        User consider = userService.getById(decision.getResolution().getUserId());
//
//        OrganAccountSettings bankAccount = accountCalculatingService.calculateForOrgan(consider, decision.getArticlePart(), 1L);

        Resolution resolution = decision.getResolution();
        ArticlePart articlePart = Optional.ofNullable(decision.getArticlePartId()).map(articlePartService::getById).orElse(null);

        OrganAccountSettings bankAccount = accountCalculatingService.calculateForOrgan(resolution, articlePart, resolution.getOrgan().getDefaultBankAccountTypeId());

        boolean hasPenalty = decision.getPenalty().isPresent();
        if (hasPenalty) {
            Optional<Invoice> invoiceOpt = invoiceServiceService.findPenaltyInvoiceByDecision(decision);
            if (invoiceOpt.isEmpty()) {
                PenaltyPunishment penalty = decision.getPenalty().get();
                penalty.setAccount(bankAccount.getPenaltyAccount());
                penalty.setDiscount(PenaltyPunishment.DiscountVersion.NO);
                punishmentRepository.save(decision.getMainPunishment());

                invoiceActionServiceService.createForPenalty(decision);
            }
        }

        Optional<Compensation> govCompensationOpt = compensationService.findGovByDecision(decision);
        if (govCompensationOpt.isPresent()) {
            Compensation compensation = govCompensationOpt.get();
            if (compensation.getInvoice() == null) {
                compensation.setAccount(bankAccount.getCompensationAccountOrThrow());
                compensationRepository.save(compensation);

                invoiceActionServiceService.createForCompensation(compensation);
            }
        }
    }
}
