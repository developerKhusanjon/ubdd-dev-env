package uz.ciasev.ubdd_service.repository.webhook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.webhook.egov.EgovWebhookArticleProjection;
import uz.ciasev.ubdd_service.entity.webhook.egov.EgovWebhookEvent;
import uz.ciasev.ubdd_service.entity.webhook.egov.EgovWebhookProjection;

import java.util.List;

public interface EgovWebhookEventRepository extends JpaRepository<EgovWebhookEvent, Long> {


    @Query(value = "select " +
            "p.id as protocolId, " +
            "p.region_id as regionId, " +
            "p.district_id as districtId , " +
            "p.violation_time as violationTime, " +
            "ac.edited_time as editedTime, " +
            "p.vehicle_number as vehicleNumber, " +
            "pp.amount as punishmentAmount, " +
            "pp.paid_amount as paidPunishmentAmount, " +
            "pp.discount50_for_date as discount50Date, " +
            "pp.discount50_amount as discount50PunishmentAmount, " +
            "pp.discount_for_date as discount70Date, " +
            "pp.discount_amount as discount70PunishmentAmount, " +
            "ac.adm_status_id as statusId, " +
            "p.address as violationPlaceAddress " +
            "from core_v0.protocol p " +
            "join core_v0.violator_detail vd on vd.id = p.violator_detail_id " +
            "join core_v0.violator v on v.id = vd.violator_id " +
            "join core_v0.adm_case ac on ac.id = v.adm_case_id " +
            "left join core_v0.resolution r on ac.id = r.adm_case_id " +
            "left join core_v0.decision d on r.id = d.resolution_id " +
            "left join core_v0.punishment p2 on d.id = p2.decision_id " +
            "left join core_v0.penalty_punishment pp on p2.id = pp.punishment_id " +
            "WHERE p.organ_id = 12 and p.id in :ids ", nativeQuery = true)
    List<EgovWebhookProjection> getProjectionsByProtocolId(@Param("ids") Iterable<Long> ids);



    @Query(value = "SELECT pa.protocol_id as protocolId, " +
            "pa.is_main as isMain, " +
            "pa.article_id as articleId, " +
            "pa.article_part_id as articlePartId, " +
            "pa.article_violation_type_id as articleViolationTypeId " +
            "FROM core_v0.protocol_article pa " +
            "WHERE pa.protocol_id IN :ids " +
            "ORDER BY isMain DESC", nativeQuery = true)
    List<EgovWebhookArticleProjection> getProtocolArticlesByProtocolIds(@Param("ids") List<Long> ids);

}