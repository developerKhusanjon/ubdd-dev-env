package uz.ciasev.ubdd_service.entity.dict.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.requests.ArticleUpdateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.ArticleCreateDTOI;
import uz.ciasev.ubdd_service.utils.ArticleNameUtils;
import uz.ciasev.ubdd_service.utils.deserializer.dict.article.ArticleCacheDeserializer;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;

@Entity
@Table(name = "d_article")
@NoArgsConstructor
@JsonDeserialize(using = ArticleCacheDeserializer.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Article extends AbstractEmiDict {

    @Getter
    private int number;

    @Getter
    private int prim;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage shortName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_head_id")
    private ArticleHead articleHead;

    public Long getArticleHeadId() {
        if (articleHead == null) return null;
        return articleHead.getId();
    }

    public void setName(MultiLanguage name) {
        this.name = name;
    }

    public void construct(ArticleCreateDTOI request) {
        super.construct(request);
        set(request);
        this.articleHead = request.getArticleHead();
    }

    public void update(ArticleUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    public void set(ArticleUpdateDTOI request) {
        this.number = request.getNumber();
        this.prim = request.getPrim();
//        this.hasParts = request.getHasParts();

        this.code = ArticleNameUtils.buildCode(this);
        this.shortName = ArticleNameUtils.buildShortName(this);
    }

    public boolean isWithoutPrim() {
        return prim == 0;
    }
}
