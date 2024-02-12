package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.evidence.Evidence;

import java.util.List;

public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    @Modifying
    @Query(value = "UPDATE Evidence SET admCaseId = :toAdmCaseId WHERE admCaseId = :fromAdmCaseId")
    void mergeAllTo(@Param("fromAdmCaseId") Long fromAdmCaseId, @Param("toAdmCaseId") Long toAdmCaseId);

    List<Evidence> findAllByAdmCaseId(Long admCaseId);

    @Query(value = "SELECT e.id FROM Evidence e WHERE admCaseId = :admCaseId")
    List<Long> findAllIdByAdmCaseId(Long admCaseId);

}
