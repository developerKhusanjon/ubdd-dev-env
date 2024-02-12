package uz.ciasev.ubdd_service.entity.prosecutor.opinion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.prosecutor.AbstractProsecutor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prosecutor_opinion")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProsecutorOpinion extends AbstractProsecutor {

    @Transient
    @Getter
    private EntityNameAlias entityNameAlias = EntityNameAlias.PROSECUTOR_PROTEST;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prosecutor_opinion_id_seq")
    @SequenceGenerator(name = "prosecutor_opinion_id_seq", sequenceName = "prosecutor_opinion_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adm_case_id")
    @Setter
    private AdmCase admCase;

    @Getter
    @Setter
    private LocalDate opinionDate;


    // JPA AND CRITERIA FIELDS ONLY

    @Column(name = "adm_case_id", insertable = false, updatable = false)
    private Long admCaseId;

    public Long getAdmCaseId() {
        if (this.admCase == null) {
            return null;
        }
        return this.admCase.getId();
    }
}
