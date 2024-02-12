package uz.ciasev.ubdd_service.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.system.MessageLocalization;

public interface MessageLocalizationRepository extends JpaRepository<MessageLocalization, Long> {
}
