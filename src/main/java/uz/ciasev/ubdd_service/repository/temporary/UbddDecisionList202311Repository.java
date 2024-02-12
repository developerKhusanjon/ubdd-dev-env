package uz.ciasev.ubdd_service.repository.temporary;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.temporary.UbddDecison202311;

import java.util.Optional;

public interface UbddDecisionList202311Repository extends JpaRepository<UbddDecison202311, Long> {

    Optional<UbddDecison202311> findByNumber(String number);
}
