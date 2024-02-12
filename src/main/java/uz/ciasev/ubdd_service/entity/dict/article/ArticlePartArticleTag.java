package uz.ciasev.ubdd_service.entity.dict.article;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "article_part_article_tag")
public class ArticlePartArticleTag {

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
    @JoinColumn(name = "article_tag_id")
    private ArticleTag articleTag;


    //    JPA AND CRITERIA FIELDS

    @Column(name = "article_part_id", insertable = false, updatable = false)
    private Long articlePartId;

    @Column(name = "article_tag_id", insertable = false, updatable = false)
    private Long articleTagId;

    public Long getArticlePartId() {
        if (this.articlePart == null) {
            return null;
        }
        return articlePart.getId();
    }

    public Long getArticleTagId() {
        if (this.articleTag == null) {
            return null;
        }
        return articleTag.getId();
    }
}
