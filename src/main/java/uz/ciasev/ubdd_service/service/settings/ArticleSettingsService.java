package uz.ciasev.ubdd_service.service.settings;

import org.springframework.data.util.Pair;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.dto.internal.request.article.ConsideredByOrganArticlePartsRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.article.RegisteredByOrganArticlePartsRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.AccessibleArticlesDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartViolationTypeResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.OrganArticlePartsResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.PenaltyRangeForDateDTO;
import uz.ciasev.ubdd_service.entity.settings.OrganConsideredArticlePartSettings;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Validated
public interface ArticleSettingsService {

    AccessibleArticlesDTO getAccessibleRegisteredArticles(@Inspector User user);

    List<ArticlePartViolationTypeResponseDTO> getAccessibleRegisteredArticlePartsViolationTypes(User user);

    List<Article> getRegisteredArticles(User user);

    List<ArticlePart> getRegisteredArticleParts(User user);

    AccessibleArticlesDTO getAccessibleConsideredArticles(@Inspector User user);

    List<Article> getConsideredArticles(User user);

    List<ArticlePart> getConsideredArticleParts(User user);

    Boolean checkAccessibleRegisterArticleByUser(@Inspector User user, Long articlePart);

    Boolean checkAccessibleConsiderArticleByUser(@Inspector User user, ArticlePart articlePart);

    Boolean checkAccessibleConsiderArticleByOrgan(Organ organ, Department department, ArticlePart articlePart);

    List<OrganConsideredArticlePartSettings> getConsideredOrgans(ArticlePart articlePart);

    OrganArticlePartsResponseDTO getRegisteredByOrganArticlePartsDTO(Long organId, Long departmentId);

    OrganArticlePartsResponseDTO getConsideredByOrganArticlePartsDTO(Long organId, Long departmentId);

    void replaceRegisteredByOrgan(RegisteredByOrganArticlePartsRequestDTO requestDTO);

    void replaceConsideredByOrgan(ConsideredByOrganArticlePartsRequestDTO requestDTO);

    Pair<Optional<Long>, Optional<Long>> calculateRangeForDate(@NotNull LocalDate date, @NotNull Boolean isJuridic, @NotNull ArticlePart articlePart);

    Optional<Long> calculateMaxForDate(@NotNull LocalDate date, @NotNull Boolean isJuridic, @NotNull ArticlePart articlePart);

    PenaltyRangeForDateDTO calculateRangeForDate(@NotNull LocalDate date, @NotNull ArticlePart articlePart);
}
