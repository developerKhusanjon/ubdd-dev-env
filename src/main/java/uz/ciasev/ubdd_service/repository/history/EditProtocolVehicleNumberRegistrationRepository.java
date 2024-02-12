package uz.ciasev.ubdd_service.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.history.EditProtocolVehicleNumberRegistration;

import java.util.List;

public interface EditProtocolVehicleNumberRegistrationRepository extends JpaRepository<EditProtocolVehicleNumberRegistration, Long> {

    List<EditProtocolVehicleNumberRegistration> findAllByProtocolIdOrderByCreatedTime(Long protocolId);
}
