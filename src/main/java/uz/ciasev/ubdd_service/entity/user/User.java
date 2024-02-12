package uz.ciasev.ubdd_service.entity.user;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.ciasev.ubdd_service.entity.CiasevEntity;
import uz.ciasev.ubdd_service.entity.Inspector;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.entity.dict.user.Rank;
import uz.ciasev.ubdd_service.entity.dict.user.UserStatus;
import uz.ciasev.ubdd_service.utils.FioUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Builder(toBuilder = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class User implements CiasevEntity, UserDetails, Inspector {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

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
    private LocalDateTime closedTime;

    @Getter
    @Setter
    private boolean isActive;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String mobile;

    @Getter
    @Setter
    private String landline;

    @Getter
    @Setter
    private String workCertificate;

    @Getter
    @Setter
    private String documentSeries;

    @Getter
    @Setter
    private String documentNumber;

    @Getter
    @Setter
    private String lastNameLat;

    @Getter
    @Setter
    private String firstNameLat;

    @Getter
    @Setter
    private String secondNameLat;

    @Getter
    @Setter
    private boolean isGod;

    @Getter
    @Setter
    private boolean isSuperuser;

    @Getter
    @Setter
    private boolean isOffline;

    @Getter
    @Setter
    private boolean isExternal;

    // Признак сситемных повещений. Означает что пользователю будут рассылаться оповещенния
    @Getter
    @Setter
    private boolean isSystemNotificationSubscriber;

    // Признак юриста. Означает что пользовател может рассматривать дела и выносить решения
    @Getter
    @Setter
    private boolean isConsider;

    // Токен для сервиса face id
    @Getter
    private String faceIdToken;

    // Признак старшего. Означает что может работать со статьями начальников
    @Getter
    @Setter
    private boolean isHeader;

    @Getter
    @Setter
    private String userPhotoUri;


    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "organ_id")
    private Organ organ;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "branch_region_id")
    private Region branchRegion;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "rank_id")
    private Rank rank;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_status_id")
    private UserStatus status;

    @Transient
    private final Set<String> apiAccess = new HashSet<>();

    @Transient
    private String organCode;

    @Getter
    @Setter
    private Boolean mustProvideDigitalSignature;

    @Column(updatable = false, insertable = false)
    private Boolean isSuperConsider;


    // поля для генерации пароля
    @Getter
    @Setter
    private Boolean needSetPassword;

    @Getter
    @Setter
    private Boolean needGeneratePassword;

    @Getter
    @Setter
    private String passwordForGeneration;

    @Getter
    @Setter
    private LocalDateTime passwordSetTime;



    // JPA AND CRITERIA FIELDS

    @Column(name = "user_status_id", insertable = false, updatable = false)
    private Long statusId;

    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    @Column(name = "organ_id", insertable = false, updatable = false)
    private Long organId;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;

    @Column(name = "branch_region_id", insertable = false, updatable = false)
    private Long branchRegionId;

    @Column(name = "position_id", insertable = false, updatable = false)
    private Long positionId;

    @Column(name = "rank_id", insertable = false, updatable = false)
    private Long rankId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return apiAccess.stream()
                .map(this::getGrantedAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public String getInfo() {
        return String.join(" ", this.lastNameLat, this.firstNameLat, this.secondNameLat, "(", this.position.getName().getLat(), ")");
    }

    @Override
    public String getFio() {
        return FioUtils.buildFullFio(this.firstNameLat, this.secondNameLat, this.lastNameLat);
    }

    private GrantedAuthority getGrantedAuthority(String apiAccess) {
        return () -> "ROLE_" + apiAccess.toUpperCase();
    }

    public void addApiAccess(String apiAccess) {
        this.apiAccess.add(apiAccess);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public User getUser() {
        return this;
    }

    public Long getStatusId() {
        if (status == null) return null;
        return status.getId();
    }

    public Long getPersonId() {
        if (person == null) return null;
        return person.getId();
    }

    public Long getRegionId() {
        if (region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (district == null) return null;
        return district.getId();
    }

    public Long getOrganId() {
        if (organ == null) return null;
        return organ.getId();
    }

    public Long getDepartmentId() {
        if (department == null) return null;
        return department.getId();
    }

    public Long getBranchRegionId() {
        if (branchRegion == null) return null;
        return branchRegion.getId();
    }

    public Long getPositionId() {
        if (position == null) return null;
        return position.getId();
    }

    public Long getRankId() {
        if (rank == null) return null;
        return rank.getId();
    }

    public void setStatus(UserStatus status) {
        this.status = status;
        this.setActive(this.status.getIsUserActive());
    }

    public String getOrganCode() {
        if (organCode != null) {
            return organCode;
        }
        return Optional.ofNullable(organ).map(Organ::getCode).orElse(null);
    }

    public boolean isSuperConsider() {
        return Boolean.TRUE.equals(this.isSuperConsider);
    }
}