package uz.ciasev.ubdd_service.entity.report;

import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.service.report.ReportAlias;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "report_template")
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
@EntityListeners(AuditingEntityListener.class)
public class ReportTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdTime;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime editedTime;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] file;

    @Enumerated(EnumType.STRING)
    private ReportAlias alias;
}
