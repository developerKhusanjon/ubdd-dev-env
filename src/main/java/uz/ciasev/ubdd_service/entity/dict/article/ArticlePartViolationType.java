package uz.ciasev.ubdd_service.entity.dict.article;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "article_part_violation_type")
public class ArticlePartViolationType {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_id")
    private ArticlePart articlePart;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_violation_type_id")
    private ArticleViolationType articleViolationType;

    @Getter
    @Setter
    private boolean isDiscount;

    //    JPA AND CRITERIA FIELDS

    @Column(name = "article_part_id", insertable = false, updatable = false)
    private Long articlePartId;

    @Column(name = "article_violation_type_id", insertable = false, updatable = false)
    private Long articleViolationTypeId;

    public Long getArticleViolationTypeId() {
        return articleViolationType.getId();
    }

    public Long getArticlePartId() {
        return articlePart.getId();
    }
}
