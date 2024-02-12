package uz.ciasev.ubdd_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.LastEmploymentInfo;

public interface LastEmploymentInfoRepository extends JpaRepository<LastEmploymentInfo, Long> {
}
