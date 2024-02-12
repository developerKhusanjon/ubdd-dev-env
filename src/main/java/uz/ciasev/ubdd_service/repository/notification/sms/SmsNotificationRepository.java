package uz.ciasev.ubdd_service.repository.notification.sms;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListExcelProjection;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsNotification;
import uz.ciasev.ubdd_service.entity.notification.sms.SmsFullListProjection;

import java.util.List;

public interface SmsNotificationRepository extends JpaRepository<SmsNotification, Long>, JpaSpecificationExecutor<SmsNotification>, SmsCustomRepository {

    @Query("SELECT s.messageId           as messageId " +
            ", s.id                      as id " +
            ", s.message                 as message " +
            ", s.sendTime                as sendTime " +
            ", s.receiveTime             as receiveTime " +
            ", s.phoneNumber             as phoneNumber " +
            ", s.notificationType.alias        as notificationType " +
            ", s.deliveryStatusId        as deliveryStatusId " +
            ", p.firstNameLat            as violatorFirstNameLat " +
            ", p.secondNameLat           as violatorSecondNameLat " +
            ", p.lastNameLat             as violatorLastNameLat " +
            ", p.birthDate               as violatorBirthDate " +
            ", ac.number                 as admCaseNumber " +
            ", ac.organId                as admCaseOrganId " +
            ", ac.regionId               as admCaseRegionId " +
            ", ac.districtId             as admCaseDistrictId " +
            ", ac.id                     as admCaseId " +
            "FROM SmsNotification s " +
            "LEFT JOIN AdmCase ac " +
            "ON s.admCaseId = ac.id " +
            "LEFT JOIN Person p " +
            "ON s.personId = p.id " +
            "WHERE s.id IN :ids")
    List<SmsFullListProjection> findAllFullListProjectionById(@Param("ids") Iterable<Long> ids, Sort sort);

    @Query("SELECT " +
            "  ac.number                                                as admCaseNumber " +
            ", jsonb_extract_path_text(o.name, 'lat')                   as organName " +
            ", jsonb_extract_path_text(r.name, 'lat')                   as regionName " +
            ", jsonb_extract_path_text(d.name, 'lat')                   as districtName " +
            ", p.firstNameLat                                           as violatorFirstNameLat " +
            ", p.secondNameLat                                          as violatorSecondNameLat " +
            ", p.lastNameLat                                            as violatorLastNameLat " +
            ", p.birthDate                                              as violatorBirthDate " +
            ", s.phoneNumber                                            as phoneNumber " +
            ", jsonb_extract_path_text(s.notificationType.name, 'lat')  as notificationTypeNameLat " +
            ", s.sendTime                                               as sendTime " +
            ", jsonb_extract_path_text(sds.name, 'lat')                 as deliveryStatus " +
            ", s.receiveTime                                            as statusUpdateTime " +
            ", s.messageId                                              as messageId " +
            "FROM SmsNotification s " +
            "LEFT JOIN AdmCase ac " +
            "ON s.admCaseId = ac.id " +
            "LEFT JOIN Organ o " +
            "ON o.id = ac.organId " +
            "LEFT JOIN Region r " +
            "ON r.id = ac.regionId " +
            "LEFT JOIN District d " +
            "ON d.id = ac.districtId " +
            "LEFT JOIN Person p " +
            "ON s.personId = p.id " +
            "LEFT JOIN SmsDeliveryStatus sds " +
            "ON s.deliveryStatusId = sds.id " +
            "WHERE s.id IN :ids")
    List<SmsFullListExcelProjection> findAllFullListExcelProjectionById(List<Long> ids, Sort sort);
}
