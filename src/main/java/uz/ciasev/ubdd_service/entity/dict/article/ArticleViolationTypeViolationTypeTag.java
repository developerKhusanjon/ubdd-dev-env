package uz.ciasev.ubdd_service.entity.dict.article;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "article_violation_type_violation_type_tag")
public class ArticleViolationTypeViolationTypeTag {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_violation_type_id")
    private ArticleViolationType articleViolationType;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_violation_type_tag_id")
    private ArticleViolationTypeTag articleViolationTypeTag;


    //    JPA AND CRITERIA FIELDS

    @Column(name = "article_violation_type_id", insertable = false, updatable = false)
    private Long articleViolationTypeId;

    @Column(name = "article_violation_type_tag_id", insertable = false, updatable = false)
    private Long articleViolationTypeTagId;

    public Long getArticleViolationTypeId() {
        if (this.articleViolationType == null) {
            return null;
        }
        return articleViolationType.getId();
    }

    public Long getArticleViolationTypeTagId() {
        if (this.articleViolationTypeTag == null) {
            return null;
        }
        return articleViolationTypeTag.getId();
    }
}
