package uz.ciasev.ubdd_service.repository.admcase;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.AdmCaseChangeReason;

public interface AdmCaseChangeReasonRepository extends JpaRepository<AdmCaseChangeReason, Long> {
}
