package uz.ciasev.ubdd_service.entity.history;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import uz.ciasev.ubdd_service.entity.settings.BankAccount;
import uz.ciasev.ubdd_service.entity.settings.OrganAccountSettings;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "h_organ_account_settings_detail_registration")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class OrganAccountSettingsDetailRegistration {

    @Getter
    @Id
    @GeneratedValue
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_action_id")
    private OrganAccountSettingsActionRegistration historyAction;

    @Getter
    private Long organId;

    @Getter
    private Long departmentId;

    @Getter
    private Long regionId;

    @Getter
    private Long districtId;

    @Getter
    private Long articlePartId;

    @Getter
    private Long bankAccountTypeId;

    @Getter
    private Long penaltyDepId;

    @Getter
    private Long compensationDepId;


    // JPA AND CRITERIA FIELD ONLY

    @Column(name = "history_action_id", insertable = false, updatable = false)
    private Long historyActionId;

    public Long getHistoryActionId() {
        if (historyAction == null) return null;
        return historyAction.getId();
    }

    public OrganAccountSettingsDetailRegistration(OrganAccountSettingsActionRegistration actionRegistration,
                                                  OrganAccountSettings entity) {
        this.historyAction = actionRegistration;
        this.organId = entity.getOrganId();
        this.departmentId = entity.getDepartmentId();
        this.regionId = entity.getRegionId();
        this.districtId = entity.getDistrictId();
        this.articlePartId = entity.getArticlePartId();
        this.bankAccountTypeId = entity.getBankAccountTypeId();
        this.penaltyDepId = entity.getPenaltyAccount().getBillingId();
        this.compensationDepId = Optional.ofNullable(entity.getCompensationAccount()).map(BankAccount::getBillingId).orElse(null);
    }
}
