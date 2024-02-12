package uz.ciasev.ubdd_service.entity.dict.article;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "d_article_part_penalty_range")
@NoArgsConstructor
public class ArticlePartPenaltyRange implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdTime = LocalDateTime.now();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_id")
    private ArticlePart articlePart;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;


    // Числитель и знаменатель соответсвующей границы штрафа

    @Getter
    @Setter
    private Integer personMinNumerator;
    @Getter
    @Setter
    private Integer personMinDenominator;

    @Getter
    @Setter
    private Integer personMaxNumerator;
    @Getter
    @Setter
    private Integer personMaxDenominator;

    @Getter
    @Setter
    private Integer juridicMinNumerator;
    @Getter
    @Setter
    private Integer juridicMinDenominator;

    @Getter
    @Setter
    private Integer juridicMaxNumerator;
    @Getter
    @Setter
    private Integer juridicMaxDenominator;

    //  JPA AND CRITERIA ONLY FIELDS

    @Column(name = "article_part_id", insertable = false, updatable = false)
    private Long articlePartId;

}
