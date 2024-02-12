package uz.ciasev.ubdd_service.repository.notification.mail;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.NotificationTypeAlias;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotification;
import uz.ciasev.ubdd_service.entity.notification.mail.MailNotificationListProjection;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;

public interface MailNotificationRepository extends MailNotificationCustomRepository, JpaRepository<MailNotification, Long>, JpaSpecificationExecutor<MailNotification> {

    boolean existsByDecisionAndNotificationTypeAlias(Decision decision, NotificationTypeAlias notificationTypeAlias);

    @Query("SELECT m.id as id, " +
            "    m.decision.id as decisionId, " +
            "    m.decision.status.id as decisionStatusId, " +
            "    m.decision.number as decisionNumber, " +
            "    m.decision.series as decisionSeries, " +
            "    m.decision.resolution.resolutionTime as resolutionTime, " +
            "    m.decision.resolution.organId as resolutionOrganId, " +
            "    m.decision.resolution.regionId as resolutionRegionId, " +
            "    m.decision.resolution.districtId as resolutionDistrictId, " +
            "    m.decision.violator.person.birthDate as violatorBirtDate, " +
            "    m.decision.violator.person.firstNameLat as violatorFirstNameLat, " +
            "    m.decision.violator.person.secondNameLat as violatorSecondNameLat, " +
            "    m.decision.violator.person.lastNameLat as violatorLastNameLat, " +
            "    m.address as address, " +
            "    m.decision.articlePartId as decisionArticlePartId, " +
            "    m.decision.articleViolationTypeId as decisionArticleViolationTypeId, " +
            "    mainPunishment.amountText as mainPunishmentAmountText, " +
            "    mainPunishment.punishmentTypeId as mainPunishmentTypeId, " +
            "    additionalPunishment.amountText as additionalPunishmentAmountText, " +
            "    additionalPunishment.punishmentTypeId as additionalPunishmentTypeId, " +
            "    m.notificationTypeAlias as notificationTypeAlias, " +
            "    m.deliveryStatusId as deliveryStatusId, " +
            "    m.changeStatusTime as changeStatusTime, " +
            "    m.messageNumber as messageNumber, " +
            "    m.sendTime as sendTime, " +
            "    m.receiveDate as performDate " +
            "FROM MailNotification m " +
            " LEFT JOIN Punishment mainPunishment ON mainPunishment.decisionId = m.decision.id AND mainPunishment.isMain = TRUE " +
            " LEFT JOIN Punishment additionalPunishment ON additionalPunishment.decisionId = m.decision.id AND additionalPunishment.isMain = FALSE " +
            "WHERE m.id IN :ids ")
    List<MailNotificationListProjection> findListProjectionById(@Param("ids") Iterable<Long> ids, Sort sort);

}
