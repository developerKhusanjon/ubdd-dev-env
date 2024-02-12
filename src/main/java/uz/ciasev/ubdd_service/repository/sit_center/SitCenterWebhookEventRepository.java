package uz.ciasev.ubdd_service.repository.sit_center;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.sit_center.SitCenterWebhookEvent;
import uz.ciasev.ubdd_service.entity.sit_center.SitCenterWebhookProtocolDecisionProjection;

import java.util.List;

public interface SitCenterWebhookEventRepository extends JpaRepository<SitCenterWebhookEvent, Long> {

    @Query("SELECT " +
            "protocol.id AS protocolId, " +
            "protocol.createdTime AS createdTime, " +
            "protocol.editedTime AS updatedTime, " +
            "protocol.regionId AS regionId, " +
            "protocol.districtId AS districtId, " +
            "protocol.mtpId AS mtpId, " +
            "protocol.organId AS organId, " +
            "jsonb_extract_path_text(articlePart.name, 'lat') AS protocolArticlePart, " +
            "inspectorPerson.pinpp AS inspectorPinpp, " +
            "inspectorPerson.isRealPinpp AS isRealInspectorPinpp, " +
            "protocol.latitude AS latitude, " +
            "protocol.longitude AS longitude, " +
            "protocol.series AS series, " +
            "protocol.number AS number, " +
            "admCase.statusId AS caseStatus, " +
            "jsonb_extract_path_text(caseStatus.name, 'lat') AS caseStatusName, " +
            "violatorPerson.lastNameLat AS lastName, " +
            "violatorPerson.firstNameLat AS firstName, " +
            "violatorPerson.secondNameLat AS secondName, " +
            "violatorDetail.documentSeries AS documentSeries, " +
            "violatorDetail.documentNumber AS documentNumber, " +
            "violatorPerson.pinpp AS pinpp, " +
            "violatorPerson.isRealPinpp AS isRealPinpp, " +
            "violatorPerson.birthDate AS birthDate, " +
            "decision.statusId AS decisionStatus, " +
            "jsonb_extract_path_text(decisionStatus.name, 'lat') AS decisionStatusName, " +
            "decision.decisionTypeId AS decisionTypeId, " +
            "jsonb_extract_path_text(decisionType.name, 'lat') AS decisionTypeName, " +
            "decision.executedDate AS executionDate, " +
            "jsonb_extract_path_text(punishmentType.name, 'lat') AS mainPunishmentType, " +
            "punishment.amountText AS mainPunishmentAmount, " +
            "resolution.organId AS resolutionOrganId, " +
            "admCase.organId AS admCaseOrganId, " +
            "jsonb_extract_path_text(acOrgan.name, 'lat') AS admCaseOrgan, " +
            "jsonb_extract_path_text(resolutionOrgan.name, 'lat') AS resolutionOrgan, " +
            "resolution.considerInfo AS resolutionConsiderInfo, " +
            "punishmentType.id as punishmentTypeId, " +
            "terminationReason.id as terminationReasonId, " +

            "protocol.articlePartId AS protocolArticlePartId, " +
            "victim.id AS victimId, " +
            "victimPerson.pinpp AS victimPinpp, " +
            "penaltyPunishment.amount AS mainPunishmentAmountSumm, " +
            "penaltyPunishment.paidAmount AS mainPunishmentPaidAmount, " +
            "penaltyPunishment.lastPayTime AS mainPunishmentLastPayTime, " +
            "penaltyPunishment.discount70ForDate AS discountForDate70, " +
            "penaltyPunishment.discount70Amount AS discountAmount70, " +
            "penaltyPunishment.discount50ForDate AS discountForDate50, " +
            "penaltyPunishment.discount50Amount AS discountAmount50, " +
            "damageDetail.amount AS damageAmount," +
            "damageType.id AS damageTypeId, " +
            "jsonb_extract_path_text(damageType.name, 'lat') AS damageTypeName, " +
            "compensationDamage.amount as compensationAmount, " +
            "compensationDamage.paidAmount as compensationPaidAmount " +

            "FROM Protocol AS protocol " +
            "JOIN ArticlePart AS articlePart ON protocol.articlePartId = articlePart.id " +
            "JOIN ViolatorDetail AS violatorDetail ON protocol.violatorDetailId = violatorDetail.id " +
            "JOIN Violator AS violator ON violatorDetail.violatorId = violator.id " +
            "JOIN AdmCase AS admCase ON violator.admCaseId = admCase.id " +
            "JOIN AdmStatus AS caseStatus ON admCase.statusId = caseStatus.id " +
            "JOIN Organ AS acOrgan ON admCase.organId = acOrgan.id " +
            "JOIN Person AS violatorPerson ON violator.personId = violatorPerson.id " +
            "LEFT JOIN User AS user ON protocol.userId = user.id " +
            "LEFT JOIN Person AS inspectorPerson ON user.personId = inspectorPerson.id " +
            "LEFT JOIN Resolution AS resolution ON resolution.admCaseId = admCase.id AND resolution.isActive = TRUE " +
            "LEFT JOIN Organ AS resolutionOrgan ON resolution.organId = resolutionOrgan.id " +
            "LEFT JOIN Decision AS decision ON resolution.id = decision.resolutionId " +
            "LEFT JOIN AdmStatus AS decisionStatus ON decision.statusId = decisionStatus.id " +
            "LEFT JOIN DecisionType AS decisionType ON decision.decisionTypeId = decisionType.id " +
            "LEFT JOIN Punishment AS punishment  ON decision.id = punishment.decisionId AND punishment.isMain = true " +
            "LEFT JOIN PunishmentType AS punishmentType ON punishment.punishmentTypeId = punishmentType.id " +
            "LEFT JOIN TerminationReason AS terminationReason ON terminationReason.id=decision.terminationReasonId " +

//            Нужно добавить эти таблицы n
            "LEFT JOIN PenaltyPunishment AS penaltyPunishment ON punishment.id=penaltyPunishment.punishmentId " +
            "LEFT JOIN VictimDetail AS victimDetail ON protocol.id=victimDetail.protocolId " +
            "LEFT JOIN Victim AS victim ON victim.admCaseId= admCase.id AND victimDetail.victimId=victim.id " +
            "LEFT JOIN Person AS victimPerson ON victim.personId = victimPerson.id  " +
            "LEFT JOIN DamageDetail AS damageDetail ON protocol.id=damageDetail.protocolId " +
            "LEFT JOIN DamageType AS damageType ON damageDetail.damageTypeId=damageType.id " +
            "LEFT JOIN Compensation AS compensationDamage ON decision.id=compensationDamage.decisionId and compensationDamage.victimTypeId=2 " +
            "WHERE protocol.id in :ids")
    List<SitCenterWebhookProtocolDecisionProjection> getProjectionsByProtocolIds(@Param("ids") Iterable<Long> ids);
}