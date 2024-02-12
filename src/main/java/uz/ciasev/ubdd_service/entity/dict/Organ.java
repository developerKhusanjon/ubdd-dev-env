package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import uz.ciasev.ubdd_service.entity.dict.requests.DictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.OrganDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.OrganCacheDeserializer;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "d_organ")
@NoArgsConstructor
@JsonDeserialize(using = OrganCacheDeserializer.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Organ extends AbstractAliasedDict<OrganAlias> {

    @Getter
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    protected MultiLanguage shortName;

    @Getter
    private String logoPath;

    @Getter
    @Column(name = "send_card_to_mib" ,nullable = false)

    private Long sendCardToMIB;

    @Getter
    private Boolean isMvd;

    @Getter
    private Long defaultBankAccountTypeId;

    @Getter
    private Boolean isArticleLevelMoneySeparation;

    // Орган получает деньги по штрафам вынесеным судом
    @Getter
    private Boolean isCourtPenaltyRecipient;

    // Орган получает деньги по компинсациям вынесеным судом
    @Getter
    private Boolean isCourtCompensationRecipient;


    // court transfer data
    @Getter
    private Long investigatingOrganization;

    @Getter
    private String investigatingOrganizationName;


    // Будут ли события органа генерить смс.
    @Getter
    private Boolean smsNotification;

    // Номер контракта с првайдером СМС с каторого будут сниматься деньги за отправку смс.
    @Getter
    private String smsContract;


    // Орган моет слать оповещения почтой для граждан
    @Getter
    private Boolean mailNotification;

    // Запрет администрирования (создания, удаления, редактирования) пользователей этого органа
    @Getter
    private Boolean isAdministrationBlocked;


    // Может ли любой орган передать свое дело для возбуждения уголовного дела в этот орган
    @Getter
    private Boolean isGlobalCriminalInvestigator;

    // У некоторых органов (министерств) есть отдел отвичающий за уголовное делопроизводство.
    // Если поле пустое, то орган не имеет такого отдела и не может сам возбуждать уголовные дела.
    // Нахрена это нужно?: потаму что когда все органы возбуждают уголовные дела, то в решение будет название их органа,
    // но когда ХОБ возбуждает уголовное дело, то в ршение должен быть Следственные отдел МВД.
    //  Так получилось изза не совсем правельной структуры справочника органов.
    @Getter
    private Long criminalInvestigatingDepartmentOrganId;

    @Getter
    private Long maxAccountsPerUser;

    @Override
    public void construct(DictCreateDTOI request) {
        super.construct(request);

        this.alias = OrganAlias.OTHER;
        this.defaultBankAccountTypeId = 1L;

        this.shortName = request.getName();
        this.logoPath = null;
        this.isMvd = true;
        this.isArticleLevelMoneySeparation = false;
        this.isCourtPenaltyRecipient = false;
        this.isCourtCompensationRecipient = false;
        this.investigatingOrganization = null;
        this.investigatingOrganizationName = null;
        this.smsNotification = false;
        this.smsContract = null;
        this.mailNotification = false;
        this.isAdministrationBlocked = true;
        this.isGlobalCriminalInvestigator = false;
        this.criminalInvestigatingDepartmentOrganId = null;
        this.maxAccountsPerUser = 0L;
    }

    public void construct(OrganDTOI request) {
        construct((DictCreateDTOI) request);
        set(request);
    }

    public void update(OrganDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(OrganDTOI dto) {
        this.shortName = dto.getShortName();
        this.logoPath = dto.getLogoPath();
        this.isMvd = dto.getIsMvd();
        this.isArticleLevelMoneySeparation = dto.getIsArticleLevelMoneySeparation();
        this.isCourtPenaltyRecipient = dto.getIsCourtPenaltyRecipient();
        this.isCourtCompensationRecipient = dto.getIsCourtCompensationRecipient();
        this.investigatingOrganization = dto.getInvestigatingOrganization();
        this.investigatingOrganizationName = dto.getInvestigatingOrganizationName();
        this.smsNotification = dto.getAllowSmsNotification();
        this.smsContract = dto.getSmsContract();
        this.mailNotification = dto.getAllowMailNotification();
        this.isAdministrationBlocked = dto.getIsAdministrationBlocked();
        this.isGlobalCriminalInvestigator = dto.getIsGlobalCriminalInvestigator();
        this.criminalInvestigatingDepartmentOrganId = Optional.ofNullable(dto.getCriminalInvestigatingDepartmentOrgan()).map(Organ::getId).orElse(null);
        this.maxAccountsPerUser = dto.getMaxAccountsPerUser();
    }

    public boolean isCourt() {
        return this.is(OrganAlias.COURT);
    }

    public boolean isMib() {
        return this.is(OrganAlias.MIB);
    }

    public boolean isGai() {
        return this.is(OrganAlias.UBDD);
    }

    public boolean isJuvenileCommission() {
        return this.is(OrganAlias.JUVENILE_COMMISSION);
    }

    @Override
    protected OrganAlias getDefaultAliasValue() {
        return OrganAlias.OTHER;
    }
}
