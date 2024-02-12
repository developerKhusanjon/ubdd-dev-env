package uz.ciasev.ubdd_service.entity.ubdd_data.insurance;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class UbddInsuranceDataAbstract implements Serializable {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Getter
    @Column(nullable = false, updatable = false)
    @CreatedDate
    protected LocalDateTime createdTime;

    @Getter
    @Column(nullable = false)
    @LastModifiedDate
    protected LocalDateTime editedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    protected User user;

    @Getter
    protected String applicantName;

    @Getter
    protected String applicantType;

    @Getter
    protected String vehicleNumber;

    @Getter
    protected String vehicleBrand;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    protected List<UbddInsuranceDriver> drivers;

    @Getter
    protected String insuranceOrganization;

    @Getter
    @Column(nullable = false)
    protected String policySeries;

    @Getter
    @Column(nullable = false)
    protected String policyNumber;

    @Getter
    @Column(name = "insurance_policy_type")
    protected String policyType;

    @Getter
    @Column(nullable = false)
    protected LocalDate fromDate;

    @Getter
    @Column(nullable = false)
    protected LocalDate toDate;

    @Getter
    protected Long insurancePremium;

    @Getter
    protected Long insuranceSum;

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    protected List<UbddInsuranceAccident> accidents;


    //  JPA AND CRITERIA FIELD ONLY

    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    protected Long userId;

    public Long getUserId() {
        if (user == null) return null;
        return user.getId();
    }
}
