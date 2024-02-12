package uz.ciasev.ubdd_service.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.notification.SystemUserNotification;

import java.util.Collection;
import java.util.List;

public interface SystemNotificationRepository extends JpaRepository<SystemUserNotification, Long>, JpaSpecificationExecutor<SystemUserNotification> {

    @Modifying
    @Query("UPDATE SystemUserNotification n " +
            " SET isRead = TRUE, " +
            " readTime = now() " +
            "WHERE n.id IN :ids " +
            " AND n.userId = :userId ")
    void makeReadByUser(@Param("userId") Long userId, @Param("ids") Collection<Long> ids);


    @Modifying
    @Query(value = "INSERT INTO {h-schema}system_user_notification(created_time, user_id, notification_type, adm_case_id, decision_id, text, is_read) " +
            "SELECT now(), u.id, :type, :admCaseId, :decisionId, :text, FALSE " +
            "FROM {h-schema}users u " +
            "WHERE u.id IN :usersId ",
            nativeQuery = true)
    void createForUsers(List<Long> usersId, Long admCaseId, Long decisionId, String type, String text);


    @Modifying
    @Query(value = "INSERT INTO {h-schema}system_user_notification(created_time, user_id, notification_type, adm_case_id, decision_id, text, is_read) " +
            "SELECT now(), u.id, :type, :admCaseId, null, :text, FALSE " +
            "FROM {h-schema}users u " +
            "WHERE u.id IN :usersId ",
            nativeQuery = true)
    void createForUsers(List<Long> usersId, Long admCaseId, String type, String text);

    @Query("SELECT count(n.id) " +
            " FROM SystemUserNotification n " +
            "WHERE n.userId = :userId " +
            "  AND n.isRead = FALSE ")
    Long getUnreadCountByUser(@Param("userId") Long userId);
}
