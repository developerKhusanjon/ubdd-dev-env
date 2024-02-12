package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.court.CourtLog;

public interface CourtLogRepository extends JpaRepository<CourtLog, Long> {

//    @Query(value = "SELECT l.id " +
//            "FROM ",
//            nativeQuery = true)
//    List<Long> findCourtProxyLogForDecisionSearchRequest(String number);
}
