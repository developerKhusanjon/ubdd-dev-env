package uz.ciasev.ubdd_service.entity.dict.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.article.ArticlePartHardLevelCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "d_article_part_hard_level")
@NoArgsConstructor
@JsonDeserialize(using = ArticlePartHardLevelCacheDeserializer.class)
public class ArticlePartHardLevel extends AbstractEmiDict {
}
