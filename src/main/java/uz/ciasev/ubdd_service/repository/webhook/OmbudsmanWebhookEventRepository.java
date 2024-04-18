package uz.ciasev.ubdd_service.repository.webhook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.webhook.ombudsman.OmbudsmanWebhookEvent;
import uz.ciasev.ubdd_service.entity.webhook.ombudsman.OmbudsmanWebhookProtocolDecisionProjection;

import java.util.List;

public interface OmbudsmanWebhookEventRepository extends JpaRepository<OmbudsmanWebhookEvent, Long> {


    @Query("SELECT " +
            "protocol.id AS protocolId, " +

            "protocol.organId as organId," +
            "protocol.mtpId as mtpId," +
            "inspectorPerson.pinpp as inspectorPinpp," +
            "concat(inspectorPerson.firstNameLat,' ',inspectorPerson.secondNameLat,' ', inspectorPerson.lastNameLat) as inspectorFio," +

            "protocol.createdTime AS createdTime, " +
            "protocol.regionId AS regionId, " +
            "protocol.districtId AS districtId, " +
            "jsonb_extract_path_text(articlePart.name, 'lat') AS protocolArticlePart, " +
            "inspectorPerson.isRealPinpp AS isRealInspectorPinpp, " +
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

            "jsonb_extract_path_text(organ.name, 'lat')  AS organValue, " +
            "jsonb_extract_path_text(region.name, 'lat')  AS regionValue, " +
            "jsonb_extract_path_text(district.name, 'lat')  AS districtValue, " +
            "jsonb_extract_path_text(mtp.name, 'lat')  AS mtpValue, " +

            "jsonb_extract_path_text(decisionStatus.name, 'lat') AS decisionStatusName, " +
            "decision.decisionTypeId AS decisionTypeId, " +
            "jsonb_extract_path_text(decisionType.name, 'lat') AS decisionTypeName, " +
            "decision.executedDate AS sendDateTime, " +
            "jsonb_extract_path_text(punishmentType.name, 'lat') AS mainPunishmentType, " +
            "punishment.amountText AS mainPunishmentAmount, " +
            "resolution.organId AS resolutionOrganId, " +
            "admCase.organId AS admCaseOrganId, " +
            "jsonb_extract_path_text(acOrgan.name, 'lat') AS admCaseOrgan, " +
            "jsonb_extract_path_text(resolutionOrgan.name, 'lat') AS resolutionOrgan, " +
            "resolution.considerInfo AS resolutionConsiderInfo, " +
            "admMovement.fromOrganId AS fromOrganId, " +
            "admMovement.toOrganId AS toOrganId " +
            "FROM Protocol AS protocol " +

            "JOIN Region AS region ON protocol.regionId = region.id " +
            "JOIN District AS district ON protocol.districtId = district.id " +
            "JOIN Organ AS organ ON protocol.organId = organ.id " +
            "JOIN Mtp AS mtp ON protocol.mtpId = mtp.id " +

            "JOIN ArticlePart AS articlePart ON protocol.articlePartId = articlePart.id " +
            "JOIN ViolatorDetail AS violatorDetail ON protocol.violatorDetailId = violatorDetail.id " +
            "JOIN Violator AS violator ON violatorDetail.violatorId = violator.id " +
            "JOIN AdmCase AS admCase ON violator.admCaseId = admCase.id " +
            "JOIN AdmStatus AS caseStatus ON admCase.statusId = caseStatus.id " +
            "JOIN Organ AS acOrgan ON admCase.organId = acOrgan.id " +
            "JOIN Person AS violatorPerson ON violator.personId = violatorPerson.id " +
            "LEFT JOIN User AS user ON protocol.userId = user.id " +
            "LEFT JOIN AdmCaseMovement AS admMovement ON admMovement.admCaseId = admCase.id " +
            "LEFT JOIN Person AS inspectorPerson ON user.personId = inspectorPerson.id " +
            "LEFT JOIN Resolution AS resolution ON resolution.admCaseId = admCase.id AND resolution.isActive = TRUE " +
            "LEFT JOIN Organ AS resolutionOrgan ON resolution.organId = resolutionOrgan.id " +
            "LEFT JOIN Decision AS decision ON resolution.id = decision.resolutionId " +
            "LEFT JOIN AdmStatus AS decisionStatus ON decision.statusId = decisionStatus.id " +
            "LEFT JOIN DecisionType AS decisionType ON decision.decisionTypeId = decisionType.id " +
            "LEFT JOIN Punishment AS punishment  ON decision.id = punishment.decisionId AND punishment.isMain = true " +
            "LEFT JOIN PunishmentType AS punishmentType ON punishment.punishmentTypeId = punishmentType.id " +
            "WHERE protocol.id in :ids ")
    List<OmbudsmanWebhookProtocolDecisionProjection> getProjectionsByProtocolId(@Param("ids") Iterable<Long> ids);
}
