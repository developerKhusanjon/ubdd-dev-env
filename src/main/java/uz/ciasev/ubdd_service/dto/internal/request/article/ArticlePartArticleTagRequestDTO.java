package uz.ciasev.ubdd_service.dto.internal.request.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartArticleTag;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleTag;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

public class ArticlePartArticleTagRequestDTO {

    @NotNull(message = ErrorCode.ARTICLE_PART_REQUIRED)
    @JsonProperty(value = "articlePartId")
    private ArticlePart articlePart;

    @NotNull(message = ErrorCode.ARTICLE_TAG_REQUIRED)
    @JsonProperty(value = "articleTagId")
    private ArticleTag articleTag;

    public ArticlePartArticleTag apply(ArticlePartArticleTag apat) {
        apat.setArticlePart(this.articlePart);
        apat.setArticleTag(this.articleTag);
        return apat;
    }

    public ArticlePartArticleTag build() {
        return apply(new ArticlePartArticleTag());
    }

    public Long getArticlePartId(){
        if (articlePart == null) {
            return null;
        }
        return articlePart.getId();
    }

    public Long getArticleTagId(){
        if (articleTag == null) {
            return null;
        }
        return articleTag.getId();
    }
}
