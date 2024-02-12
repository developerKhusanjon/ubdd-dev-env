package uz.ciasev.ubdd_service.entity.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReasonViolation;
import uz.ciasev.ubdd_service.entity.dict.statistic.StatisticReportType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "protocol_statistic_data")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProtocolStatisticData implements ProtocolData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdTime = LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime editedTime;

    private String organizationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "protocol_id")
    private Protocol protocol;

    @ManyToOne
    @JoinColumn(name = "statistic_report_type_id")
    private StatisticReportType reportType;

    @ManyToOne
    @JoinColumn(name = "statistic_reason_violation_id")
    private StatisticReasonViolation reasonViolation;
}
