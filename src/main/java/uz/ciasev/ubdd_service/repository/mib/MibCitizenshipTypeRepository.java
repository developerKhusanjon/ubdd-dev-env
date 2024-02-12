package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransCitizenshipType;

import java.util.Optional;

public interface MibCitizenshipTypeRepository extends JpaRepository<MibTransCitizenshipType, Long> {

    Optional<MibTransCitizenshipType> findByInternalId(Long internalId);
}
