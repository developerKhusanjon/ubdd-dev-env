package uz.ciasev.ubdd_service.repository.ubdd_data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.ProtocolUbddInsuranceData;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.UbddInsuranceData;

import java.util.Optional;

public interface UbddInsuranceDataRepository extends JpaRepository<UbddInsuranceData, Long> {

//    @Query("SELECT i " +
//            "FROM UbddDataToProtocolBind b JOIN UbddInsuranceData i ON b.ubddInsuranceDataId = i.id " +
//            "WHERE pd.protocolId = :protocolId ")
//    Optional<UbddInsuranceData> getByProtocolId(Long protocolId);

    @Query("SELECT i " +
            "FROM ProtocolUbddInsuranceData i " +
            "WHERE i.protocolId = :protocolId ")
    Optional<ProtocolUbddInsuranceData> findByProtocolId(Long protocolId);
}
