package uz.ciasev.ubdd_service.repository.notification.mail.trans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;

import java.util.Optional;

public interface MailTransGeographyRepository extends JpaRepository<MailTransGeography, Long> {

    @Query("SELECT mtg " +
            " FROM MailTransGeography mtg " +
            "WHERE COALESCE(mtg.regionId, -1) = COALESCE(:regionId, -1) " +
            "  AND COALESCE(mtg.districtId, -1) = COALESCE(:districtId, -1)")
    Optional<MailTransGeography> findByRegionAndDistrict(@Param("regionId") Long regionId,
                                                         @Param("districtId") Long districtId);
}
