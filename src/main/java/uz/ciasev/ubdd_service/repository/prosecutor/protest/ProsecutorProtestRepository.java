package uz.ciasev.ubdd_service.repository.prosecutor.protest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;

public interface ProsecutorProtestRepository extends JpaRepository<ProsecutorProtest, Long> {
    boolean existsByResolutionIdAndNumber(Long resolutionId, String number);

    Page<ProsecutorProtest> findAllByResolutionId(Long resolutionId, Pageable pageable);
}
