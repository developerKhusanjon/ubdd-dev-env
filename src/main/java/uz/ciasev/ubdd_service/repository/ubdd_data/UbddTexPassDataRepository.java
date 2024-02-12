package uz.ciasev.ubdd_service.repository.ubdd_data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.ubdd_data.ProtocolUbddTexPassData;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddTexPassData;
import java.util.Optional;

public interface UbddTexPassDataRepository extends JpaRepository<UbddTexPassData, Long> {

//    @Query("SELECT tp " +
//            "FROM Decision de " +
//            "INNER JOIN Protocol pr " +
//            "   ON de.id = :decisionId " +
//            "   AND pr.violatorDetail.violator.admCaseId = de.resolution.admCaseId " +
//            "INNER JOIN UbddDataToProtocolBind bi " +
//            "   ON bi.protocolId = pr.id " +
//            "INNER JOIN UbddTexPassData tp " +
//            "   ON tp.id = bi.ubddTexPassDataId ")
//    List<UbddTexPassData> getByDecisionId(Long decisionId);

    @Query("SELECT i " +
            "FROM ProtocolUbddTexPassData i " +
            "WHERE i.protocolId = :protocolId ")
    Optional<ProtocolUbddTexPassData> findByProtocolId(Long protocolId);

    @Query("SELECT i " +
            "FROM ProtocolUbddTexPassData i " +
            "WHERE " +
            "   i.protocol.violatorDetail.violatorId = :violatorId " +
            "   AND i.protocol.isMain = TRUE ")
    Optional<ProtocolUbddTexPassData> findMainByViolatorId(Long violatorId);
}
