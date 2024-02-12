package uz.ciasev.ubdd_service.entity.report;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.AdmEntity;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "report_sql")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ReportSql implements AdmEntity, Serializable {

    @Transient
    private EntityNameAlias entityNameAlias = EntityNameAlias.EVIDENCE;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_sql_id_seq")
    @SequenceGenerator(name = "report_sql_id_seq", sequenceName = "report_sql_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String query;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private Map<String, String> paramNames;

    private String alias;
}
