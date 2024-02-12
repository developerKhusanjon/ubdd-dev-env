package uz.ciasev.ubdd_service.repository.wanted;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.wanted.WantedProtocolClosed;

public interface WantedProtocolClosedRepository extends JpaRepository<WantedProtocolClosed, Long> {
}
