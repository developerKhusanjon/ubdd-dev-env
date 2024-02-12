package uz.ciasev.ubdd_service.entity.dict.article;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;
import java.io.Serializable;

@Builder(toBuilder = true)
@Entity
@Table(name = "d_article_part_detail")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@EqualsAndHashCode(of = "id")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ArticlePartDetail implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private MultiLanguage violationText;

    @Getter
    @Setter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private MultiLanguage punishmentText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_id")
    private ArticlePart articlePart;

    //    JPA AND CRITERIA FIELDS

    @Column(name = "article_part_id", insertable = false, updatable = false)
    private Long articlePartId;

    public Long getArticlePartId() {
        return articlePart.getId();
    }
}
