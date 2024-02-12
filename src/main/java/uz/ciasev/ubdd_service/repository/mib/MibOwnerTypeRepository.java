package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerType;
import uz.ciasev.ubdd_service.entity.mib.MibOwnerTypeAlias;

import java.util.Optional;

public interface MibOwnerTypeRepository extends JpaRepository<MibOwnerType, Long> {

    Optional<MibOwnerType> findById(Long id);

    Optional<MibOwnerType> findByAlias(MibOwnerTypeAlias alias);
}
