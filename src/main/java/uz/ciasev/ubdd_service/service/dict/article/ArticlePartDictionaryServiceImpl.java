package uz.ciasev.ubdd_service.service.dict.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartPenaltyRangeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.*;
import uz.ciasev.ubdd_service.entity.history.DictAdminHistoricAction;
import uz.ciasev.ubdd_service.exception.dict.ArticlePartNumberAlreadyExistsException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartDetailRepository;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartRepository;
import uz.ciasev.ubdd_service.service.dict.DictionaryHelperFactory;
import uz.ciasev.ubdd_service.service.dict.DictionaryHelperForActivity;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;
import uz.ciasev.ubdd_service.service.history.DictionaryAdminHistoryService;
import uz.ciasev.ubdd_service.service.settings.ArticleSettingsService;
import uz.ciasev.ubdd_service.specifications.SpecificationsCombiner;
import uz.ciasev.ubdd_service.specifications.dict.ArticlePartSpecifications;
import uz.ciasev.ubdd_service.specifications.dict.DictionarySpecifications;
import uz.ciasev.ubdd_service.utils.ArticleNameUtils;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticlePartDictionaryServiceImpl implements ArticlePartDictionaryService, DictionaryServiceWithRepository<ArticlePart> {

    @Getter
    private final ArticlePartRepository repository;
    @Getter
    private final Class<ArticlePart> entityClass = ArticlePart.class;

    protected DictionaryHelperForActivity<ArticlePart> activityDictHelper;
    @Setter
    @Autowired
    protected DictionaryHelperFactory factory;

    private final ArticlePartDetailRepository detailRepository;
    private final ArticlePartPenaltyRangeService articlePartPenaltyRangeService;
    private final ArticleSettingsService articleSettingsService;
    private final FilterHelper<ArticlePart> filterHelper;
    private final ArticleDTOService dtoService;
    private final ArticlePartSpecifications specifications;
    private final DictionaryAdminHistoryService historyService;

    @PostConstruct
    public void init() {
        this.activityDictHelper = factory.constructHelperForActivity(this);
    }

    @Override
    public String getSubPath() {
        return null;
    }

    @Override
    public Page<ArticlePart> findAll(Map<String, String> filters, Pageable pageable) {
        return repository.findAll(filterHelper.getParamsSpecification(filters), pageable);
    }

    @Override
    public Object buildListResponseDTO(ArticlePart entity) {
        return dtoService.convertPartToListDTO(entity);
    }

    @Override
    public ArticlePart getById(Long id) {

        return repository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(ArticlePart.class, id));
    }

    @Override
    public ArticlePartPenaltyRangeResponseDTO getPenaltyRange(Long id, Boolean isJuridic, LocalDate toDate) {
        ArticlePart articlePart = getById(id);
        Pair<Optional<Long>, Optional<Long>> penaltyRange = articleSettingsService.calculateRangeForDate(toDate, isJuridic, articlePart);

        return new ArticlePartPenaltyRangeResponseDTO(
                penaltyRange.getFirst().orElse(null),
                penaltyRange.getSecond().orElse(null)
        );
    }

    @Override
    public List<Pair<ArticlePart, ArticlePartDetail>> findByArticleWithDetail(Article article) {
        List<ArticlePart> parts = repository.findAllByIsActiveTrueAndArticleId(article.getId(), Sort.by(ArticlePart_.NUMBER));

        Map<Long, ArticlePartDetail> detailMap = detailRepository.findByArticleId(article.getId()).stream()
                .collect(Collectors.toMap(
                        ArticlePartDetail::getArticlePartId,
                        d -> d
                ));

        ArticlePartDetail defaultDetail = getEmptyDetail();

        return parts.stream()
                .map(p -> Pair.of(
                        p,
                        detailMap.getOrDefault(p.getId(), defaultDetail)
                )).collect(Collectors.toList());
    }

    @Override
    public ArticlePartDetailResponseDTO findDetailDTOById(Long id) {

        ArticlePart articlePart = getById(id);
        ArticlePartDetail articlePartDetail = getOrCreateDetail(articlePart);
        List<ArticlePartPenaltyRange> penaltyRangeList = articlePartPenaltyRangeService.findByArticlePartId(id);

        return new ArticlePartDetailResponseDTO(articlePart, articlePartDetail, penaltyRangeList);
    }

    @Override
    @Transactional
    public ArticlePartDetailResponseDTO create(ArticlePartCreateRequestDTO requestDTO) {
        validateUniqueNumber(requestDTO.getArticle(), requestDTO.getNumber(), null);
        return saveData(DictAdminHistoricAction.CREATE, ArticlePart.createNew(), requestDTO);
    }

    @Override
    @Transactional
    public ArticlePartDetailResponseDTO update(Long id, ArticlePartRequestDTO requestDTO) {
        ArticlePart articlePart = getById(id);
        validateUniqueNumber(articlePart.getArticle(), requestDTO.getNumber(), articlePart);
        return saveData(DictAdminHistoricAction.UPDATE, articlePart, requestDTO);
    }

    @Override
    public void open(Long id) {
        activityDictHelper.open(id);
    }

    @Override
    public void close(Long id) {
        activityDictHelper.close(id);
    }

    private ArticlePartDetailResponseDTO saveData(DictAdminHistoricAction action, ArticlePart articlePart, ArticlePartRequestDTO requestDTO) {

        requestDTO.applyTo(articlePart);
        articlePart.setCode(ArticleNameUtils.buildCode(articlePart));
        articlePart.setName(ArticleNameUtils.buildName(articlePart));
        ArticlePart savedArticlePart = repository.saveAndFlush(articlePart);

        ArticlePartDetail articlePartDetail = getOrCreateDetail(savedArticlePart);
        requestDTO.applyTo(articlePartDetail);
        detailRepository.save(articlePartDetail);

        List<ArticlePartPenaltyRange> penaltyRangeList = articlePartPenaltyRangeService.replaceAll(savedArticlePart, requestDTO);

        historyService.register(articlePart, action, Map.of("articlePartDetail", articlePartDetail, "penaltyRangeList", penaltyRangeList));

        return new ArticlePartDetailResponseDTO(savedArticlePart, articlePartDetail, penaltyRangeList);
    }

    @Override
    public void articleUpdateCallback(Article article) {
        repository.findAll(specifications.withArticle(article))
                .forEach(articlePart -> {
                    articlePart.setCode(ArticleNameUtils.buildCode(articlePart));
                    articlePart.setName(ArticleNameUtils.buildName(articlePart));
                    repository.save(articlePart);
                });
    }

    private ArticlePartDetail getOrCreateDetail(ArticlePart articlePart) {

        return detailRepository.findByArticlePartId(articlePart.getId())
                .orElseGet(() -> detailRepository.saveAndFlush(getEmptyDetail(articlePart)));
    }

    private ArticlePartDetail getEmptyDetail(ArticlePart articlePart) {
        return getEmptyDetail().toBuilder()
                .articlePart(articlePart)
                .build();
    }

    private ArticlePartDetail getEmptyDetail() {
        return ArticlePartDetail.builder()
                .violationText(new MultiLanguage("~~~", "~~~", "~~~"))
                .punishmentText(new MultiLanguage("~~~", "~~~", "~~~"))
                .build();
    }

    private void validateUniqueNumber(Article article, Integer number, @Nullable ArticlePart articlePart) {
        if (articlePart != null && !articlePart.getIsActive()) {
            return;
        }

        if (articlePart != null && Objects.equals(articlePart.getNumber(), number)) {
            return;
        }

        Specification<ArticlePart> spec = SpecificationsCombiner.andAll(
                specifications.withArticle(article),
                specifications.withNumber(number),
                DictionarySpecifications.withIsActive(true)
//                Optional.ofNullable(articlePart).map(p -> Specification.not(specifications.withId(article.getId()))).orElseGet(SpecificationsHelper::getEmpty)
        );

        boolean numberUnique = repository.count(spec) == 0;

        if (!numberUnique) {
            throw new ArticlePartNumberAlreadyExistsException(article.getNumber(), article.getPrim(), number);
        }

    }
}
