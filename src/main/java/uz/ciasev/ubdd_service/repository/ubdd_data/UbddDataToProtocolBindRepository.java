package uz.ciasev.ubdd_service.repository.ubdd_data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UbddDataToProtocolBindRepository extends JpaRepository<UbddDataToProtocolBind, Long> {

    Optional<UbddDataToProtocolBind> findByProtocolId(Long protocolId);

    @Query(
            value = "select wanted_vehicle.save_vechicle_arrest(" +
                    "   :vehicleNumber, " +
                    "   :vehicleArrestPlaceId, " +
                    "   :userId, " +
                    "   :arrestTime, " +
                    "   :vehicleBrand, " +
                    "   :vehicleOwnerName, " +
                    "   :vehicleColor " +
                    ")",
            nativeQuery = true
    )
    Integer saveArrest(String vehicleNumber,
                    Long vehicleArrestPlaceId,
                    Long userId,
                    LocalDateTime arrestTime,
                    String vehicleBrand,
                    String vehicleOwnerName,
                    String vehicleColor);
}
