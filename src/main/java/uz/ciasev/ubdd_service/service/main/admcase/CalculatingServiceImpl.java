package uz.ciasev.ubdd_service.service.main.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseCalculatedMovementResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseSendingResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseSimplifiedResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.settings.OrganConsideredArticlePartSettings;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.damage.DamageService;
import uz.ciasev.ubdd_service.service.evidence.EvidenceService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.protocol.RepeatabilityService;
import uz.ciasev.ubdd_service.service.settings.ArticleSettingsService;
import uz.ciasev.ubdd_service.service.victim.VictimDetailService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculatingServiceImpl implements CalculatingService {

    private final AdmCaseService admCaseService;
    private final ProtocolService protocolService;
    private final EvidenceService evidenceService;
    private final VictimDetailService victimDetailService;
    private final RepeatabilityService repeatabilityService;
    private final ArticleSettingsService articleSettingsService;
    private final DamageService damageService;

    @Override
    public AdmCaseCalculatedMovementResponseDTO calculateAdmCaseMovementsByOrgan(User user, Long admCaseId) {
        if (Optional.ofNullable(user.getOrgan()).map(Organ::isGai).orElse(false)) {
            return calculateUbddTabletAdmCaseMovements(user, admCaseId);
        }

        return calculateAdmCaseMovements(user, admCaseId);
    }



        @Override
    public AdmCaseCalculatedMovementResponseDTO calculateAdmCaseMovements(User user, Long admCaseId) {
        AdmCase admCase = admCaseService.getById(admCaseId);
        //        todo решить проблему что некоректно сработает для составных дел
        Protocol protocol = protocolService.findSingleMainByAdmCaseId(admCaseId);

        if (!articleSettingsService.checkAccessibleConsiderArticleByOrgan(user.getOrgan(), user.getDepartment(), protocol.getArticlePart())) {
            return AdmCaseCalculatedMovementResponseDTO.doSending(
                    admCaseId,
                    calculateSending(user, protocol.getArticlePart())
            );
        }

        //  Пользователь не юрист и ничего не может делать с делом
        if (!user.isConsider()) {
            return AdmCaseCalculatedMovementResponseDTO.doNothing(admCaseId);
        }

        //  В органе пользователя данную статью рассмтаривает только начальник
        if (!user.isHeader() && !isConsideredUser(user, protocol.getArticlePart())) {
            return AdmCaseCalculatedMovementResponseDTO.doNothing(admCaseId);
        }

        if (protocol.getArticlePart().isCourtOnly()) {
            return AdmCaseCalculatedMovementResponseDTO.doYourself(admCaseId);
        }

        if (isSimplified(user, admCase, protocol)) {

//            long minAmountPenalty = articleSettingsService.calculateMinForDate(protocol.getViolationTime().toLocalDate(), protocol.getIsJuridic(), protocol.getArticlePart()).orElse(0L);
//            long maxAmountPenalty = articleSettingsService.calculateMaxForDate(protocol.getViolationTime().toLocalDate(), protocol.getIsJuridic(), protocol.getArticlePart()).orElse(0L);

            Pair<Optional<Long>, Optional<Long>> penaltyRange = articleSettingsService.calculateRangeForDate(protocol.getViolationTime().toLocalDate(), protocol.getIsJuridic(), protocol.getArticlePart());
            long minAmountPenalty = penaltyRange.getFirst().orElse(0L);
            long maxAmountPenalty = penaltyRange.getSecond().orElse(0L);

            return AdmCaseCalculatedMovementResponseDTO.doSimplified(admCaseId, new AdmCaseSimplifiedResponseDTO(protocol, minAmountPenalty, maxAmountPenalty));
        }

        return AdmCaseCalculatedMovementResponseDTO.doYourself(admCaseId);
    }


    @Override
    public AdmCaseCalculatedMovementResponseDTO calculateUbddTabletAdmCaseMovements(User user, Long admCaseId) {
        AdmCase admCase = admCaseService.getById(admCaseId);
        //        todo решить проблему что некоректно сработает для составных дел
        Protocol protocol = protocolService.findSingleMainByAdmCaseId(admCaseId);

        //  Пользователь не юрист и ничего не может делать с делом
        if (!user.isConsider()) {
            return AdmCaseCalculatedMovementResponseDTO.doNothing(admCaseId);
        }

        if (protocol.getArticlePart().isCourtOnly()) {
            return AdmCaseCalculatedMovementResponseDTO.doNothing(admCaseId);
        }

        if (isSimplified(user, admCase, protocol)) {

            Pair<Optional<Long>, Optional<Long>> penaltyRange = articleSettingsService.calculateRangeForDate(protocol.getViolationTime().toLocalDate(), protocol.getIsJuridic(), protocol.getArticlePart());
            long minAmountPenalty = penaltyRange.getFirst().orElse(0L);
            long maxAmountPenalty = penaltyRange.getSecond().orElse(0L);

            return AdmCaseCalculatedMovementResponseDTO.doSimplified(admCaseId, new AdmCaseSimplifiedResponseDTO(protocol, minAmountPenalty, maxAmountPenalty));
        }

        return AdmCaseCalculatedMovementResponseDTO.doNothing(admCaseId);
    }

    /**
     * Упрощенка это:
     * 1.  статья (часть статьи) имеет наказание штраф и ее рассматривает не суд
     * 2.  нарушитель согласен с протоколом
     * 3.  статью (часть) рассматривает сам составивший орган + составивший пользователь
     * 4.  поиск на повторность нарушения дал отрицательный результат (т.е. нарушение первое в течение года)
     * 5.  при вводе протокола пункты вещдокам не вводятся
     * 6.  отсутствуют данные об ущербе (все три вида)
     **/
//    @Override
    public boolean isSimplified(User user, AdmCase admCase, Protocol protocol) {
        ArticlePart articlePart = protocol.getArticlePart();

        return (protocol.isAgree()
                && !protocol.getIsJuridic()
                && articlePart.isPenaltyOnly()
                && !protocolService.existsProtocolAdditionArticles(protocol)
                && canResolveAdmCase(user, admCase)
                && damageService.findAllByAdmCaseId(admCase.getId()).isEmpty()
//                && victimDetailService.findAllByProtocolId(protocol.getId()).isEmpty()
                && !repeatabilityService.hasProtocolRepeatability(protocol));
    }

    @Override
    public void checkSimplified(User user, AdmCase admCase, Protocol protocol) {
        ArticlePart articlePart = protocol.getArticlePart();

        checkCanResolveAdmCase(user, admCase);

        ValidationCollectingError error = new ValidationCollectingError();

        error.addIf(
                protocol.getIsJuridic(),
                ErrorCode.SIMPLIFIED_FOR_JURIDIC_PROTOCOL_IMPOSSIBLE
        );

        error.addIf(
                !protocol.isAgree(),
                ErrorCode.SIMPLIFIED_FOR_DISAGREE_PROTOCOL_IMPOSSIBLE
        );

        error.addIf(
                !articlePart.isPenaltyOnly(),
                ErrorCode.SIMPLIFIED_FOR_NOT_PENALTY_ONLY_ARTICLE_PART_IMPOSSIBLE
        );

        error.addIf(
                protocolService.existsProtocolAdditionArticles(protocol),
                ErrorCode.SIMPLIFIED_FOR_PROTOCOL_WITH_ADDITION_ARTICLE_PARTS_IMPOSSIBLE
        );

        error.addIf(
                !damageService.findAllByAdmCaseId(admCase.getId()).isEmpty(),
                ErrorCode.SIMPLIFIED_FOR_PROTOCOL_WITH_DAMAGE_IMPOSSIBLE
        );

//        error.addIf(
//                !victimDetailService.findAllByProtocolId(protocol.getId()).isEmpty(),
//                ErrorCode.SIMPLIFIED_FOR_PROTOCOL_WITH_VICTIM_IMPOSSIBLE
//        );

        error.addIf(
                repeatabilityService.hasProtocolRepeatability(protocol),
                ErrorCode.SIMPLIFIED_FOR_PROTOCOL_WITH_REPEATABILITY_IMPOSSIBLE
        );

        error.throwErrorIfNotEmpty();
    }

    @Override
    public boolean isConsideredOrgan(Organ organ, Department department, AdmCase admCase) {
        List<ArticlePart> articles = protocolService.findMainArticlePartsByAdmCase(admCase);
        return articles.stream().allMatch(a -> articleSettingsService.checkAccessibleConsiderArticleByOrgan(organ, department, a));
    }

    @Override
    public boolean isConsideredUser(User user, AdmCase admCase) {
        List<ArticlePart> articles = protocolService.findMainArticlePartsByAdmCase(admCase);
        return isConsideredUserForAll(user, articles);
    }

    @Override
    public boolean isConsideredUser(User user, ArticlePart articlePart) {
        return articleSettingsService.checkAccessibleConsiderArticleByUser(user, articlePart);
    }

    @Override
    public boolean isConsideredUserForAll(User user, List<ArticlePart> articleParts) {
        return articleParts
                .stream()
                .allMatch(a -> isConsideredUser(user, a));
    }

    /**
     * Пользователь может вынести решение по делу, если:
     * 1.  он являеться Старшим(Юристом);
     * 2.  орган и подразделение пользователя могут рассмотреть статью;
     * 3.  статья расматриваеться не только судом;
     * 4.  нет вещдоков;
     * 5.  ущерб меньше минималки.
     **/
//    @Override
    public boolean canResolveAdmCase(User user, AdmCase admCase) {
        List<ArticlePart> articles = protocolService.findMainArticlePartsByAdmCase(admCase);

        return (user.isConsider()
                && articles.stream().noneMatch(ArticlePart::isCourtOnly)
                && isConsideredUserForAll(user, articles)
                && !evidenceService.existsByAdmCaseId(admCase.getId())
                && !protocolService.existsJuvenileByAdmCaseId(admCase.getId())
                // todo Это узбакистан, так что не добавляйю добавить проверку что ущерб в деле меньше минималки, иначе только суд
        );
    }

    @Override
    public void checkCanResolveAdmCase(User user, AdmCase admCase) {

        ValidationCollectingError error = new ValidationCollectingError();

        error.addIf(!user.isConsider(), ErrorCode.NOT_CONSIDER_USER);

        if (user.getOrgan().isJuvenileCommission()) {
            error.throwErrorIfNotEmpty();
            return;
        }

        List<ArticlePart> articles = protocolService.findMainArticlePartsByAdmCase(admCase);

        error.addIf(articles.stream().anyMatch(ArticlePart::isCourtOnly), ErrorCode.COURT_ONLY_ARTICLE_PART);
        error.addIf(!isConsideredUserForAll(user, articles), ErrorCode.NOT_CONSIDER_OF_CASE_ARTICLE_PART);
        error.addIf(evidenceService.existsByAdmCaseId(admCase.getId()), ErrorCode.ADM_CASE_CONTAIN_EVIDENCE);
        error.addIf(protocolService.existsJuvenileByAdmCaseId(admCase.getId()), ErrorCode.ADM_CASE_CONTAIN_JUVENILE);

        error.throwErrorIfNotEmpty();
    }

    private AdmCaseSendingResponseDTO calculateSending(User user,
                                                       ArticlePart articlePart) {

        List<OrganConsideredArticlePartSettings> consideredSettings = articleSettingsService.getConsideredOrgans(articlePart);

        Set<Long> organs = consideredSettings.stream().map(OrganConsideredArticlePartSettings::getOrganId).collect(Collectors.toSet());
        Set<Long> departments = consideredSettings.stream().map(OrganConsideredArticlePartSettings::getDepartmentId).filter(Objects::nonNull).collect(Collectors.toSet());

        return new AdmCaseSendingResponseDTO(organs, departments);
    }
}
