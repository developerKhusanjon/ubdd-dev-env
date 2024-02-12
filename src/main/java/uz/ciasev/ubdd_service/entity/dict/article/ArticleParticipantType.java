package uz.ciasev.ubdd_service.entity.dict.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.article.ArticleParticipantTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_article_participant_type")
@NoArgsConstructor
@JsonDeserialize(using = ArticleParticipantTypeCacheDeserializer.class)
public class ArticleParticipantType extends AbstractBackendDict<ArticleParticipantTypeAlias> {
}
