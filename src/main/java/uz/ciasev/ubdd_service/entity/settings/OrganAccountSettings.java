package uz.ciasev.ubdd_service.entity.settings;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import uz.ciasev.ubdd_service.entity.dict.*;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.exception.notfound.OrganCompensationBankInfoNotFoundException;
import uz.ciasev.ubdd_service.service.settings.OrganAccountSettingsCreateRequest;
import uz.ciasev.ubdd_service.service.settings.OrganAccountSettingsUpdateRequest;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "organ_account_settings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class OrganAccountSettings {

    @Getter
    @Id
    @GeneratedValue
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "new_id", updatable = false, nullable = false)
    private UUID id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "penalty_account_id")
    private BankAccount penaltyAccount;

    @Getter
    @ManyToOne
    @JoinColumn(name = "compensation_account_id")
    private BankAccount compensationAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id")
    private Organ organ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_part_id")
    private ArticlePart articlePart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_type_id")
    private BankAccountType bankAccountType;


    // JPA AND CRITERIA FIELD ONLY

    @Column(name = "organ_id", insertable = false, updatable = false)
    private Long organId;

    @Column(name = "department_id", insertable = false, updatable = false)
    private Long departmentId;

    @Column(name = "region_id", insertable = false, updatable = false)
    private Long regionId;

    @Column(name = "district_id", insertable = false, updatable = false)
    private Long districtId;

    @Column(name = "article_part_id", insertable = false, updatable = false)
    private Long articlePartId;

    @Column(name = "bank_account_type_id", insertable = false, updatable = false)
    private Long bankAccountTypeId;

    public OrganAccountSettings(OrganAccountSettingsCreateRequest request) {
        this.organ = request.getOrgan();
        this.department = request.getDepartment();
        this.region = request.getRegion();
        this.district = request.getDistrict();
        this.articlePart = request.getArticlePart();
        this.bankAccountType = request.getBankAccountType();
        this.setBankAccounts(request);
    }

    public Long getOrganId() {
        if (organ == null) return null;
        return organ.getId();
    }

    public Long getDepartmentId() {
        if (department == null) return null;
        return department.getId();
    }

    public Long getRegionId() {
        if (region == null) return null;
        return region.getId();
    }

    public Long getDistrictId() {
        if (district == null) return null;
        return district.getId();
    }

    public Long getArticlePartId() {
        if (articlePart == null) return null;
        return articlePart.getId();
    }

    public Long getBankAccountTypeId() {
        if (bankAccountType == null) return null;
        return bankAccountType.getId();
    }

    public BankAccount getCompensationAccountOrThrow() {
        if (this.compensationAccount == null) {
            throw new OrganCompensationBankInfoNotFoundException();
        }

        return compensationAccount;
    }

    public static OrganAccountSettings getEmpty() {
        OrganAccountSettings e = new OrganAccountSettings();
        e.penaltyAccount = BankAccount.EMPTY_ACCOUNT;
        e.compensationAccount = BankAccount.EMPTY_ACCOUNT;
        return e;
    }

    public OrganAccountSettings setBankAccounts(OrganAccountSettingsUpdateRequest request) {
        this.penaltyAccount = request.getPenaltyAccount();
        this.compensationAccount = request.getCompensationAccount();
        return this;
    }

}
