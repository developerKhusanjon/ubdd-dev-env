package uz.ciasev.ubdd_service.entity.dict.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.requests.ArticleViolationTypeDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.article.ArticleViolationTypeCacheDeserializer;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "d_article_violation_type")
@NoArgsConstructor
@JsonDeserialize(using = ArticleViolationTypeCacheDeserializer.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ArticleViolationType extends AbstractEmiDict {

    @Getter
    private int number;

    @Getter
    private Boolean dontCheckUniqueness = false;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private MultiLanguage shortName;

    @Getter
    private String trafficRulesClause;

    @Getter
    private String radarFabulaDescription;


    //    JPA AND CRITERIA FIELDS

    @OneToMany(mappedBy = "articleViolationType")
    private List<ArticleViolationTypeViolationTypeTag> articleViolationTypeViolationTypeTags = new ArrayList<>();


    public void construct(ArticleViolationTypeDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(ArticleViolationTypeDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(ArticleViolationTypeDTOI request) {
        this.number = request.getNumber();
        this.dontCheckUniqueness = request.getDontCheckUniqueness();
        this.shortName = request.getShortName();
        this.trafficRulesClause = request.getTrafficRulesClause();
        this.radarFabulaDescription = request.getRadarFabulaDescription();
    }
}
