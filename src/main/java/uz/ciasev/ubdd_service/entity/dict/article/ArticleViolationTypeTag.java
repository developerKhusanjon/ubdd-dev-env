package uz.ciasev.ubdd_service.entity.dict.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.article.ArticleViolationTypeTagCacheDeserializer;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "d_article_violation_type_tag")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonDeserialize(using = ArticleViolationTypeTagCacheDeserializer.class)
public class ArticleViolationTypeTag extends AbstractBackendDict<ArticleViolationTypeTagAlias> {

    public ArticleViolationTypeTag(ArticleViolationTypeTagAlias alias) {
        this.alias = alias;
        this.name = new MultiLanguage(alias.toString(), alias.toString(), alias.toString());
        this.openedDate = LocalDate.now();
        this.isActive = true;
    }
}
