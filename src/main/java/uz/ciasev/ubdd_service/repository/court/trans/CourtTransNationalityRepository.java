package uz.ciasev.ubdd_service.repository.court.trans;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransNationality;

import java.util.Optional;

public interface CourtTransNationalityRepository extends JpaRepository<CourtTransNationality, Long> {

    Optional<CourtTransNationality> findByExternalId(Long externalId);
    Optional<CourtTransNationality> findByInternalId(Long externalId);
}
