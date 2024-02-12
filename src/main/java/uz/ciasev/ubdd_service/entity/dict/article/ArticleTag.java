package uz.ciasev.ubdd_service.entity.dict.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.article.ArticleTagCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name = "d_article_tag")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonDeserialize(using = ArticleTagCacheDeserializer.class)
public class ArticleTag extends AbstractEmiDict {
}
