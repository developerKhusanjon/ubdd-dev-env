package uz.ciasev.ubdd_service.repository.publicapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.publicapi.PublicApiWebhookEvent;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicApiWebhookEventRepository extends JpaRepository<PublicApiWebhookEvent, Long> {

    @Modifying
    @Query(value = "UPDATE PublicApiWebhookEvent e SET " +
            "e.isReceived = TRUE, " +
            "e.receivedTime = now() " +
            "WHERE e = :event ")
    void setReceivedFields(PublicApiWebhookEvent event);

    @Modifying
    @Query(value = "UPDATE PublicApiWebhookEvent e SET " +
            "e.orderId = e.orderId + :orderIncrement, " +
            "e.dontSendBefore = :dontSendBefore " +
            "WHERE e = :event ")
    void prorogueOn(PublicApiWebhookEvent event, LocalDateTime dontSendBefore, int orderIncrement);


    @Query(value = "SELECT e.* " +
            "FROM {h-schema}public_api_webhooks_events e " +
            "JOIN ( " +
            "       SELECT min(s.id) as id" +
            "       FROM {h-schema}public_api_webhooks_events s " +
            "       WHERE s.is_received = FALSE " +
            "       GROUP BY s.adm_case_id" +
            ") as m ON m.id = e.id " +
            "WHERE " +
            " (e.dont_send_before IS NULL OR e.dont_send_before < now()) " +
            " AND e.order_id <= :maxOrderId " +
            " AND NOT EXISTS (SELECT 1 FROM {h-schema}public_api_webhooks_events_lock l WHERE l.event_id = e.id) " +
            "ORDER BY e.order_id " +
            "LIMIT :listSize ", nativeQuery = true)
    List<PublicApiWebhookEvent> findNextForSending(int maxOrderId, int listSize);


    @Query(value = "SELECT e.* " +
            "FROM {h-schema}public_api_webhooks_events e " +
            "JOIN ( " +
            "       SELECT min(s.id) as id" +
            "       FROM {h-schema}public_api_webhooks_events s " +
            "       WHERE s.is_received = FALSE " +
            "       GROUP BY s.adm_case_id" +
            ") as m ON m.id = e.id " +
            "WHERE " +
            " (e.dont_send_before IS NULL OR e.dont_send_before < now()) " +
            " AND NOT EXISTS (SELECT 1 FROM {h-schema}public_api_webhooks_events_lock l WHERE l.event_id = e.id) " +
            "ORDER BY e.order_id " +
            "LIMIT :listSize ", nativeQuery = true)
    List<PublicApiWebhookEvent> findNextForSending(int listSize);


//    @Query("SELECT e FROM PublicApiWebhookEvent e " +
//            "WHERE NOT e.id IN (SELECT l.event.id FROM PublicApiWebhookEventLog l WHERE l.isSent = true ) " + // GROUP BY l.event.id
//            "AND (e.dontSendBefore IS NULL OR e.dontSendBefore < :now) ")
//
//    @Query(value = "" +
//            "SELECT * FROM core_v0.public_api_webhooks_events e " +
//            " WHERE NOT EXISTS (SELECT 1 FROM core_v0.public_api_webhooks_events_log l WHERE l.event_id = e.id AND l.is_sent) " +
//            "AND EXISTS (SELECT 1 FROM core_v0.public_api_webhooks_event_subscription s WHERE s.organ_id = e.organ_id AND s.type = e.type) " +
//            "AND (e.dont_send_before IS NULL OR e.dont_send_before < :now) " +
//            "and (SELECT count(id) FROM core_v0.public_api_webhooks_events_log l WHERE l.event_id = e.id) < 10 " +
//            "LIMIT 1"
//            , nativeQuery = true)
//    List<PublicApiWebhookEvent> findAnyNotSent(@Param("now") LocalDateTime now);
//
//    @Query("SELECT e FROM PublicApiWebhookEvent e " +
//            "WHERE NOT e.id IN (SELECT l.event.id FROM PublicApiWebhookEventLog l WHERE l.isSent = true ) " + // GROUP BY l.event.id
//            "AND (e.dontSendBefore IS NULL OR e.dontSendBefore < :now) " +
//            "AND e.id > :id ")
//
//    @Query(value = "" +
//            "SELECT * FROM core_v0.public_api_webhooks_events e " +
//            " WHERE NOT EXISTS (SELECT 1 FROM core_v0.public_api_webhooks_events_log l WHERE l.event_id = e.id AND l.is_sent) " +
//            "AND EXISTS (SELECT 1 FROM core_v0.public_api_webhooks_event_subscription s WHERE s.organ_id = e.organ_id AND s.type = e.type) " +
//            "AND (e.dont_send_before IS NULL OR e.dont_send_before < :now) " +
//            "AND e.id > :id " +
//            "and (SELECT count(id) FROM core_v0.public_api_webhooks_events_log l WHERE l.event_id = e.id) < 10 " +
//            "LIMIT 1"
//            , nativeQuery = true)
//    List<PublicApiWebhookEvent> findNextNotSent(@Param("id") Long id, @Param("now") LocalDateTime now);
}
