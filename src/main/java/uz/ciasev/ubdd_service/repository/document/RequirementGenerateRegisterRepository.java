package uz.ciasev.ubdd_service.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.document.RequirementGeneration;

public interface RequirementGenerateRegisterRepository extends JpaRepository<RequirementGeneration, Long> {
}
