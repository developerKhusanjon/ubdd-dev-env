package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolFullListProjection;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProtocolFullListResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final String series;
    private final String number;
    private final LocalDateTime registrationTime;
    private final Long registeredOrganId;
    private final Long registeredDepartmentId;
    private final Long registeredDistrictId;
    private final Long registeredRegionId;
    private final LocalDateTime violationTime;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;
    private final String pinpp;
    private final String fio;
    private final LocalDate birthDate;
    private final Long nationalityId;
    private final Long genderId;
    private final Long personDocumentTypeId;
    private final String documentSeriesNumber;
    private final boolean isJuridic;
    private final Long consideredOrganId;
    private final Long consideredDepartmentId;
    private final boolean isMain;
    private final Long violatorId;
    private final Long statusId;
    private final Long decisionId;
    private final String decisionSeries;
    private final String decisionNumber;
    private final Long resolutionOrganId;
    private final LocalDateTime resolutionTime;
    private final Long decisionTypeId;
    private final Long terminationReasonId;
    private final Long punishmentTypeId;
    private final String punishmentAmount;
    private final Long decisionStatusId;
    private final String vehicleNumber;
    private final String vehicleBrand;
    private final Long govCompensationAmount;
    private final Long govCompensationPaidAmount;

    public ProtocolFullListResponseDTO(ProtocolFullListProjection tuple) {
        this.id = tuple.getId();
        this.createdTime = tuple.getCreatedTime();
        this.series = tuple.getSeries();
        this.number = tuple.getNumber();
        this.registrationTime = tuple.getRegistrationTime();
        this.registeredOrganId = tuple.getRegistrationOrganId();
        this.registeredDepartmentId = tuple.getRegistrationDepartmentId();
        this.registeredDistrictId = tuple.getRegistrationDistrictId();
        this.registeredRegionId = tuple.getRegistrationRegionId();
        this.violationTime = tuple.getViolationTime();
        this.articleId = tuple.getArticleId();
        this.articlePartId = tuple.getArticlePartId();
        this.articleViolationTypeId = tuple.getArticleViolationTypeId();
        this.pinpp = tuple.getViolatorPinpp();
        this.fio = tuple.getViolatorFio();
        this.birthDate = tuple.getViolatorBirthDate();
        this.nationalityId  = tuple.getViolatorNationalityId();
        this.genderId  = tuple.getViolatorGenderId();
        this.personDocumentTypeId = tuple.getViolatorDocumentTypeId();
        this.isJuridic = tuple.getIsJuridic();
        this.documentSeriesNumber = tuple.getViolatorDocumentSeries() + tuple.getViolatorDocumentNumber();
        this.consideredOrganId = tuple.getConsiderOrganId();
        this.consideredDepartmentId = tuple.getConsiderDepartmentId();
        this.statusId = tuple.getAdmCaseStatusId();
        this.isMain = tuple.getIsMain();
        this.violatorId = tuple.getViolatorId();
        this.decisionId = tuple.getDecisionId();
        this.decisionSeries = tuple.getDecisionSeries();
        this.decisionNumber = tuple.getDecisionNumber();
        this.resolutionOrganId = tuple.getResolutionOrganId();
        this.resolutionTime = tuple.getResolutionTime();
        this.decisionTypeId = tuple.getDecisionTypeId();
        this.terminationReasonId = tuple.getTerminationReasonId();
        this.punishmentTypeId = tuple.getPunishmentTypeId();
        this.punishmentAmount = tuple.getPunishmentAmount();
        this.decisionStatusId = tuple.getDecisionStatusId();
        this.vehicleNumber = tuple.getVehicleNumber();
        this.vehicleBrand = tuple.getVehicleBrand();
        this.govCompensationAmount = tuple.getGovCompensationAmount();
        this.govCompensationPaidAmount = tuple.getGovCompensationPaidAmount();
    }
}
