package uz.ciasev.ubdd_service.entity.dict.article;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.article.ArticlePartCacheDeserializer;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "d_article_part")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonDeserialize(using = ArticlePartCacheDeserializer.class)
public class ArticlePart extends AbstractEmiDict {

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Getter
    @Setter
    private int number;

    @Getter
    @Setter
    private boolean isVictim;

    @Getter
    @Setter
    private boolean isDiscount;

    // Для физического лица по этой статье применим только штраф
    @Getter
    @Setter
    private boolean isPenaltyOnly;

    @Getter
    @Setter
    private boolean isCourtOnly;

    @Deprecated
    @Getter
    private boolean isRepeat;


    // равна нэйму. автогенерить по формату.
    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage shortName;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_participant_type_id")
    private ArticleParticipantType articleParticipantType;

    @Getter
    @Setter
    private Boolean isViolationTypeRequired;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_hard_level_id")
    private ArticlePartHardLevel hardLevel;

    @Getter
    @Setter
    private Double penaltyPoint;


    //    JPA AND CRITERIA FIELDS

    @OneToOne(mappedBy = "articlePart", fetch = FetchType.LAZY)
    private ArticlePartDetail articleParticipantDetail;

    @Column(name = "article_id", insertable = false, updatable = false)
    private Long articleId;

    @Column(name = "article_participant_type_id", insertable = false, updatable = false)
    private Long articleParticipantTypeId;

    @OneToMany(mappedBy = "articlePart", fetch = FetchType.LAZY)
    private List<ArticlePartArticleTag> articlePartArticleTags = new ArrayList<>();

    public static ArticlePart createNew() {
        ArticlePart articlePart = new ArticlePart();
        articlePart.isActive=true;
        articlePart.openedDate= LocalDate.now();
        return articlePart;
    }

    public void setName(MultiLanguage name) {
        this.shortName = name;
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getArticleId() {
        return article.getId();
    }

    public Long getArticleParticipantTypeId() {
        return articleParticipantType.getId();
    }

    public Long getHardLevelId() {
        if (hardLevel == null) return null;
        return hardLevel.getId();
    }
}
