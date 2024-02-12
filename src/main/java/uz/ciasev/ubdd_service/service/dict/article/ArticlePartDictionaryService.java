package uz.ciasev.ubdd_service.service.dict.article;

import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.article.ArticlePartPenaltyRangeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartDetail;
import uz.ciasev.ubdd_service.service.dict.ActivityDictionaryService;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;

import java.time.LocalDate;
import java.util.List;

public interface ArticlePartDictionaryService extends DictionaryService<ArticlePart>, ActivityDictionaryService<ArticlePart> {

    ArticlePartPenaltyRangeResponseDTO getPenaltyRange(Long id, Boolean isJuridic, LocalDate toDate);

    List<Pair<ArticlePart, ArticlePartDetail>> findByArticleWithDetail(Article article);

    ArticlePartDetailResponseDTO findDetailDTOById(Long id);

    ArticlePartDetailResponseDTO create(ArticlePartCreateRequestDTO requestDTO);

    ArticlePartDetailResponseDTO update(Long id, ArticlePartRequestDTO requestDTO);

    void close(Long id);

    void open(Long id);

    void articleUpdateCallback(Article article);
}
