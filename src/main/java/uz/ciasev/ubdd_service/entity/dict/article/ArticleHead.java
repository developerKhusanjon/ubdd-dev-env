package uz.ciasev.ubdd_service.entity.dict.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.article.ArticleHeadCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_article_head")
@NoArgsConstructor
@JsonDeserialize(using = ArticleHeadCacheDeserializer.class)
public class ArticleHead extends AbstractEmiDict {
}
