package uz.ciasev.ubdd_service.service.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.article.ConsideredByOrganArticlePartsRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.article.RegisteredByOrganArticlePartsRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.*;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.*;
import uz.ciasev.ubdd_service.entity.settings.OrganArticleSettingsProjection;
import uz.ciasev.ubdd_service.entity.settings.OrganConsideredArticlePartSettings;
import uz.ciasev.ubdd_service.entity.settings.OrganRegisteredArticlePartSettings;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartViolationTypeRepository;
import uz.ciasev.ubdd_service.repository.settings.OrganConsideredArticlePartSettingsRepository;
import uz.ciasev.ubdd_service.repository.settings.OrganRegisteredArticlePartSettingsRepository;
import uz.ciasev.ubdd_service.service.dict.article.ArticlePartPenaltyRangeService;
import uz.ciasev.ubdd_service.specifications.OrganConsideredPartSettingsSpecifications;
import uz.ciasev.ubdd_service.specifications.OrganRegisteredPartSettingsSpecifications;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.utils.MoneyFormatter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ArticleSettingsServiceImpl implements ArticleSettingsService {

    private final OrganRegisteredArticlePartSettingsRepository registeredPartsRepository;
    private final OrganConsideredArticlePartSettingsRepository consideredPartsRepository;
    private final BrvService brvService;
    private final ArticlePartPenaltyRangeService articlePartPenaltyRangeService;
    private final ArticlePartViolationTypeRepository articlePartViolationTypeRepository;
    private final OrganRegisteredPartSettingsSpecifications registeredPartSpecifications;
    private final OrganConsideredPartSettingsSpecifications consideredPartSpecifications;

    @Override
    public AccessibleArticlesDTO getAccessibleRegisteredArticles(User user) {
        return getAccessibleArticlesDTO(getOrganRegisteredArticlePartProjection(user));
    }

    @Override
    public List<ArticlePartViolationTypeResponseDTO> getAccessibleRegisteredArticlePartsViolationTypes(User user) {
        List<OrganArticleSettingsProjection> registered = getOrganRegisteredArticlePartProjection(user);

        List<Long> registeredArticlePartsId = registered.stream()
                .filter(OrganArticleSettingsProjection::getIsActive)
                .map(OrganArticleSettingsProjection::getArticlePartId)
                .collect(Collectors.toList());

        List<ArticlePartViolationType> registeredArticlePartViolationType = articlePartViolationTypeRepository.findAllActiveByArticlePartsId(registeredArticlePartsId);


        // Список частей статаей, каторые не имеют типа нарушения
        Set<Long> articlePartsWithoutViolationType = new HashSet<>(registeredArticlePartsId);
        registeredArticlePartViolationType.forEach(e -> articlePartsWithoutViolationType.remove(e.getArticlePartId()));


        Stream<ArticlePartViolationTypeResponseDTO> resultWithTypes = registeredArticlePartViolationType.stream()
                .map(ArticlePartViolationTypeResponseDTO::new);

        Stream<ArticlePartViolationTypeResponseDTO> resultWithoutTypes = registered.stream()
                .filter(e -> articlePartsWithoutViolationType.contains(e.getArticlePartId()))
                .map(ArticlePartViolationTypeResponseDTO::new);

        return Stream.of(
                        resultWithoutTypes,
                        resultWithTypes
                )
                .flatMap(l -> l)
                .collect(Collectors.toList());


    }

    @Override
    public List<Article> getRegisteredArticles(User user) {
        return registeredPartsRepository.findArticle(SpecificationsCombiner.andAll(getRegisteredSpecificationForUser(user)));
    }

    @Override
    public List<ArticlePart> getRegisteredArticleParts(User user) {
        return registeredPartsRepository.findArticlePart(SpecificationsCombiner.andAll(getRegisteredSpecificationForUser(user)));
    }

    @Override
    public AccessibleArticlesDTO getAccessibleConsideredArticles(User user) {
        return getAccessibleArticlesDTO(getOrganConsideredArticlePartProjection(user));
    }

    @Override
    public List<Article> getConsideredArticles(User user) {
        return consideredPartsRepository.findArticle(SpecificationsCombiner.andAll(getConsideredSpecificationForUser(user)));
    }

    @Override
    public List<ArticlePart> getConsideredArticleParts(User user) {
        return consideredPartsRepository.findArticlePart(SpecificationsCombiner.andAll(getConsideredSpecificationForUser(user)));
    }

    private AccessibleArticlesDTO getAccessibleArticlesDTO(List<OrganArticleSettingsProjection> projections) {

        Map<Long, Set<Long>> articleIds = projections.stream()
                .collect(Collectors.groupingBy(
                        OrganArticleSettingsProjection::getArticleId,
                        Collectors.mapping(OrganArticleSettingsProjection::getArticlePartId, toSet())
                ));

        AccessibleArticlesDTO accessibleArticlesDTO = new AccessibleArticlesDTO();
        articleIds.forEach((k, v) -> accessibleArticlesDTO.getArticles().add(new AccessibleArticle(k, v)));

        return accessibleArticlesDTO;
    }

    @Override
    public Boolean checkAccessibleRegisterArticleByUser(User user, Long articlePart) {
        List<Specification<OrganRegisteredArticlePartSettings>> specifList = getRegisteredSpecificationForUser(user);
        specifList.add(registeredPartSpecifications.withArticlePartExactly(articlePart));

        return registeredPartsRepository.exists(SpecificationsCombiner.andAll(specifList));
    }

    @Override
    public Boolean checkAccessibleConsiderArticleByUser(User user, ArticlePart articlePart) {
        List<Specification<OrganConsideredArticlePartSettings>> specifList = getConsideredSpecificationForUser(user);
        specifList.add(consideredPartSpecifications.withArticlePartExactly(articlePart));

        return consideredPartsRepository.exists(SpecificationsCombiner.andAll(specifList));

    }

    @Override
    public Boolean checkAccessibleConsiderArticleByOrgan(Organ organ, Department department, ArticlePart articlePart) {

        return consideredPartsRepository.exists(SpecificationsCombiner.andAll(
                consideredPartSpecifications.withOrganExactly(organ),
                consideredPartSpecifications.withDepartmentExactly(department),
                consideredPartSpecifications.withArticlePartExactly(articlePart)
        ));

//        return consideredPartsRepository.existsByArticlePartAndOrganAndDepartment(articlePart, organ, department);
    }

    @Override
    public List<OrganConsideredArticlePartSettings> getConsideredOrgans(ArticlePart articlePart) {
        return consideredPartsRepository.findAll(consideredPartSpecifications.withArticlePartExactly(articlePart));
//        return consideredPartsRepository.findByArticlePart(articlePart);
    }

    @Override
    public OrganArticlePartsResponseDTO getRegisteredByOrganArticlePartsDTO(Long organId, Long departmentId) {

//        List<OrganRegisteredArticlePartSettings> accessibleArticles = registeredPartsRepository.findAllByOrganIdAndDepartmentId(organId, departmentId);
        List<OrganRegisteredArticlePartSettings> accessibleArticles = registeredPartsRepository.findAll(SpecificationsCombiner.andAll(
                registeredPartSpecifications.withOrganExactly(organId),
                registeredPartSpecifications.withDepartmentExactly(departmentId)));

        OrganArticlePartsResponseDTO rsl = new OrganArticlePartsResponseDTO();
        rsl.setArticleParts(
                accessibleArticles.stream().map(OrganRegisteredArticlePartSettings::getArticlePartId).map(OrganArticlePartResponseDTO::new).collect(toSet())
        );
        return rsl;
    }

    @Override
    public OrganArticlePartsResponseDTO getConsideredByOrganArticlePartsDTO(Long organId, Long departmentId) {

        List<OrganConsideredArticlePartSettings> parts = consideredPartsRepository.
//                findAllByOrganIdAndDepartmentId(organId, departmentId);
                findAll(SpecificationsCombiner.andAll(
                        consideredPartSpecifications.withOrganExactly(organId),
                        consideredPartSpecifications.withDepartmentExactly(departmentId)));

        OrganArticlePartsResponseDTO rsl = new OrganArticlePartsResponseDTO();
        rsl.setArticleParts(
                parts.stream().map((ap) -> new OrganArticlePartResponseDTO(ap.getArticlePartId(),
                        ap.getIsHeaderOnly())).collect(toSet())
        );
        return rsl;
    }

    @Override
    @Transactional
    public void replaceRegisteredByOrgan(RegisteredByOrganArticlePartsRequestDTO requestDTO) {

        if (requestDTO.getDepartment() == null)
            registeredPartsRepository.deleteAllByOrganAndEmptyDepartment(requestDTO.getOrgan());
        else
            registeredPartsRepository.deleteAllByOrganAndDepartment(requestDTO.getOrgan(), requestDTO.getDepartment());

        Set<OrganRegisteredArticlePartSettings> parts = requestDTO.getArticleParts().stream().map((ap) -> {
            return new OrganRegisteredArticlePartSettings(requestDTO.getOrgan(), requestDTO.getDepartment(),
                    ap.getArticlePart());
        }).collect(toSet());

        registeredPartsRepository.saveAll(parts);
    }

    @Override
    @Transactional
    public void replaceConsideredByOrgan(ConsideredByOrganArticlePartsRequestDTO requestDTO) {

        if (requestDTO.getDepartment() == null)
            consideredPartsRepository.deleteAllByOrganAndEmptyDepartment(requestDTO.getOrgan());
        else
            consideredPartsRepository.deleteAllByOrganAndDepartment(requestDTO.getOrgan(), requestDTO.getDepartment());

        Set<OrganConsideredArticlePartSettings> parts = requestDTO.getArticleParts().stream().map((ap) -> {
            return new OrganConsideredArticlePartSettings(requestDTO.getOrgan(), requestDTO.getDepartment(),
                    ap.getArticlePart(), ap.getIsHeaderOnly() == null ? false : ap.getIsHeaderOnly());
        }).collect(toSet());

        consideredPartsRepository.saveAll(parts);
    }

    @Override
    public Pair<Optional<Long>, Optional<Long>> calculateRangeForDate(@NotNull LocalDate date, @NotNull Boolean isJuridic, @NotNull ArticlePart articlePart) {
        ArticlePartPenaltyRange penaltyRange = articlePartPenaltyRangeService.findLast(articlePart.getId(), date);
        Long brv = brvService.findByDate(date);

        return Pair.of(
                calculateMinForDate(brv, isJuridic, penaltyRange),
                calculateMaxForDate(brv, isJuridic, penaltyRange)
        );
    }

    @Override
    public Optional<Long> calculateMaxForDate(@NotNull LocalDate date, @NotNull Boolean isJuridic, @NotNull ArticlePart articlePart) {
        ArticlePartPenaltyRange penaltyRange = articlePartPenaltyRangeService.findLast(articlePart.getId(), date);
        Long brv = brvService.findByDate(date);

        return calculateMaxForDate(brv, isJuridic, penaltyRange);
    }

    @Override
    public PenaltyRangeForDateDTO calculateRangeForDate(@NotNull LocalDate date, @NotNull ArticlePart articlePart) {
        ArticlePartPenaltyRange penaltyRange = articlePartPenaltyRangeService.findLast(articlePart.getId(), date);
        Long brv = brvService.findByDate(date);

        return PenaltyRangeForDateDTO.builder()
                .personMin(calculateMinForDate(brv, false, penaltyRange))
                .personMax(calculateMaxForDate(brv, false, penaltyRange))
                .juridicMin(calculateMinForDate(brv, true, penaltyRange))
                .juridicMax(calculateMaxForDate(brv, true, penaltyRange))
                .build();
    }

    private Optional<Long> calculateMinForDate(@NotNull Long brv, @NotNull Boolean isJuridic, ArticlePartPenaltyRange penaltyRange) {

        Integer numerator;
        Integer denominator;

        if (isJuridic) {
            numerator = penaltyRange.getJuridicMinNumerator();
            denominator = penaltyRange.getJuridicMinDenominator();
        } else {
            numerator = penaltyRange.getPersonMinNumerator();
            denominator = penaltyRange.getPersonMinDenominator();
        }

        return calculateMoneyAmount(brv, numerator, denominator);
    }

    private Optional<Long> calculateMaxForDate(Long brv, Boolean isJuridic, ArticlePartPenaltyRange penaltyRange) {

        Integer numerator;
        Integer denominator;

        if (isJuridic) {
            numerator = penaltyRange.getJuridicMaxNumerator();
            denominator = penaltyRange.getJuridicMaxDenominator();
        } else {
            numerator = penaltyRange.getPersonMaxNumerator();
            denominator = penaltyRange.getPersonMaxDenominator();
        }

        return calculateMoneyAmount(brv, numerator, denominator);

    }

    private Optional<Long> calculateMoneyAmount(@NotNull Long brv, Integer numerator, Integer denominator) {

        if (numerator == null || denominator == null) {
            return Optional.empty();
        }

        double valueInCoin = Math.ceil((double) (brv * numerator) / denominator);

        double roundValueInCurrency = Math.ceil(MoneyFormatter.coinToCurrency(valueInCoin));

        long roundValueInCoin = MoneyFormatter.currencyToCoin(roundValueInCurrency);

        return Optional.of(roundValueInCoin);
    }

    private List<OrganArticleSettingsProjection> getOrganRegisteredArticlePartProjection(User user) {
        return registeredPartsRepository.findOrganArticleSettingsProjection(SpecificationsCombiner.andAll(getRegisteredSpecificationForUser(user)));
    }

    private List<OrganArticleSettingsProjection> getOrganConsideredArticlePartProjection(User user) {

        Specification<OrganConsideredArticlePartSettings> spec = SpecificationsCombiner.andAll(getConsideredSpecificationForUser(user));
        return consideredPartsRepository.findOrganArticleSettingsProjection(spec);

    }

    private List<Specification<OrganConsideredArticlePartSettings>> getConsideredSpecificationForUser(User user) {

        List<Specification<OrganConsideredArticlePartSettings>> spec = new ArrayList<>();
        spec.add(consideredPartSpecifications.withOrganExactly(user.getOrgan()));
        spec.add(consideredPartSpecifications.withDepartmentExactly(user.getDepartment()));
        if (!user.isHeader()) {
            spec.add(consideredPartSpecifications.withIsHeaderOnly(user.isHeader()));
        }
        return spec;
    }

    private List<Specification<OrganRegisteredArticlePartSettings>> getRegisteredSpecificationForUser(User user) {
        List<Specification<OrganRegisteredArticlePartSettings>> spec = new ArrayList<>();
        spec.add(registeredPartSpecifications.withOrganExactly(user.getOrgan()));
        spec.add(registeredPartSpecifications.withDepartmentExactly(user.getDepartment()));
        return spec;
    }
}
