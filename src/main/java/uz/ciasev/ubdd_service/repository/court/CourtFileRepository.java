package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.court.CourtFile;

import java.util.List;

public interface CourtFileRepository extends JpaRepository<CourtFile, Long> {

    List<CourtFile> findByCaseId(Long caseId, Sort sort);

    List<CourtFile> findByCaseIdAndClaimId(Long caseId, Long climeId, Pageable pageable);
}
