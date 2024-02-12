package uz.ciasev.ubdd_service.repository.prosecutor.opinion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinion;

import java.time.LocalDate;

public interface ProsecutorOpinionRepository extends JpaRepository<ProsecutorOpinion, Long> {

    boolean existsByAdmCaseIdAndProsecutorInfoAndOpinionDate(Long admCaseId, String prosecutorInfo, LocalDate opinionDate);

    Page<ProsecutorOpinion> findAllByAdmCaseId(Long admCaseId, Pageable pageable);
}
