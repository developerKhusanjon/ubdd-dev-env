package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;

import java.util.Optional;

public interface CourtCaseFieldsRepository extends JpaRepository<CourtCaseFields, Long> {

    @Query("SELECT cf " +
            " FROM CourtCaseFields cf " +
            "WHERE cf.caseId = :caseId")
    Optional<CourtCaseFields> findByCaseId(@Param("caseId") Long caseId);

    boolean existsByCaseNumber(String caseNumber);
}
