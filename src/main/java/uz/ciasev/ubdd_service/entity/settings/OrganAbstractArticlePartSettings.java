package uz.ciasev.ubdd_service.entity.settings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.CiasevEntity;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class OrganAbstractArticlePartSettings implements CiasevEntity {

//    @Getter
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id")
    protected Organ organ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    protected Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_id")
    protected ArticlePart articlePart;


    // JPA AND CRITERIA FIELD ONLY

    @Column(name = "organ_id", insertable = false, updatable = false)
    private Long organId;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;

    @Column(name = "article_part_id", insertable = false, updatable = false)
    private Long articlePartId;

    public OrganAbstractArticlePartSettings(Organ organ, Department department, ArticlePart articlePart) {
        this.organ = organ;
        this.department = department;
        this.articlePart = articlePart;
    }

    public Long getOrganId() {
        if (organ == null) return null;
        return organ.getId();
    }

    public Long getDepartmentId() {
        if (department == null) return null;
        return department.getId();
    }

    public Long getArticlePartId() {
        if (articlePart == null) return null;
        return articlePart.getId();
    }
}
