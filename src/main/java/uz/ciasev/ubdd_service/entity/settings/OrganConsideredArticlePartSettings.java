package uz.ciasev.ubdd_service.entity.settings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import javax.persistence.*;

@Entity
@Table(name = "organ_considered_article_part_settings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganConsideredArticlePartSettings extends OrganAbstractArticlePartSettings {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "organ_id")
//    private Organ organ;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "department_id")
//    private Department department;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "article_part_id")
//    private ArticlePart articlePart;

    @Getter
    private Boolean isHeaderOnly;


//    // JPA AND CRITERIA FIELD ONLY
//
//    @Column(name = "organ_id", insertable = false, updatable = false)
//    private Long organId;
//
//    @Column(name = "department_id", insertable = false, updatable = false)
//    private Long departmentId;
//
//    @Column(name = "article_part_id", insertable = false, updatable = false)
//    private Long articlePartId;

    public OrganConsideredArticlePartSettings(Organ organ, Department department, ArticlePart articlePart, Boolean isHeaderOnly) {
        this.organ = organ;
        this.department = department;
        this.articlePart = articlePart;
        this.isHeaderOnly = isHeaderOnly;
    }

//    public Long getOrganId() {
//        if (organ == null) return null;
//        return organ.getId();
//    }
//
//    public Long getDepartmentId() {
//        if (department == null) return null;
//        return department.getId();
//    }
//
//    public Long getArticlePartId() {
//        if (articlePart == null) return null;
//        return articlePart.getId();
//    }
}
