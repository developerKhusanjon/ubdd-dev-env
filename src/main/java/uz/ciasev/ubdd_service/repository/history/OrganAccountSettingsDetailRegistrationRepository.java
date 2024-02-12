package uz.ciasev.ubdd_service.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.history.OrganAccountSettingsDetailRegistration;

import java.util.UUID;

public interface OrganAccountSettingsDetailRegistrationRepository extends JpaRepository<OrganAccountSettingsDetailRegistration, UUID> {
}
