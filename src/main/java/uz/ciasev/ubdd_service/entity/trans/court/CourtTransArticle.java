package uz.ciasev.ubdd_service.entity.trans.court;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.CourtTransArticleCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

import javax.persistence.*;

@Entity
@Table(name = "court_trans_article")
@AllArgsConstructor
@NoArgsConstructor
public class CourtTransArticle extends AbstractTransEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internal_article_part_id")
    private ArticlePart articlePart;

    @Getter
    private Long externalArticleId;

    @Getter
    private Long externalArticlePartId;


    //    JPA AND CRITERIA FIELDS

    @Column(name = "internal_article_part_id", updatable = false, insertable = false)
    private Long articlePartId;

    public Long getArticlePartId() {
        if (articlePart == null) return null;
        return articlePart.getId();
    }

    public void construct(CourtTransArticleCreateDTOI request) {
        this.articlePart = request.getArticlePart();
        this.externalArticleId = request.getExternalArticleId();
        this.externalArticlePartId = request.getExternalArticlePartId();
    }
}