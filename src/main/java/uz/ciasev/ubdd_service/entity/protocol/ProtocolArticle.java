package uz.ciasev.ubdd_service.entity.protocol;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.utils.types.ArticlePair;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "protocol_article")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class ProtocolArticle implements ArticlePair, Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "protocol_article_id_seq")
    @SequenceGenerator(name = "protocol_article_id_seq", sequenceName = "protocol_article_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    Protocol protocol;

    @Getter
    @Setter
    private Boolean isMain = false;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    Article article;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_id")
    ArticlePart articlePart;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_violation_type_id")
    ArticleViolationType articleViolationType;


    // JPA AND CRITERIA FIELDS

    @Column(name = "article_id", insertable = false, updatable = false)
    private Long articleId;

    @Column(name = "article_part_id", insertable = false, updatable = false)
    private Long articlePartId;

    @Column(name = "article_violation_type_id", insertable = false, updatable = false)
    private Long articleViolationTypeId;

    @Column(name = "protocol_id", insertable = false, updatable = false)
    private Long protocolId;


    public Long getProtocolId() {
        if (protocol == null) return null;
        return protocol.getId();
    }

    public Long getArticleId() {
        if (article == null) return null;
        return article.getId();
    }

    public Long getArticlePartId() {
        if (articlePart == null) return null;
        return articlePart.getId();
    }

    public Long getArticleViolationTypeId() {
        if (articleViolationType == null) return null;
        return articleViolationType.getId();
    }
}
