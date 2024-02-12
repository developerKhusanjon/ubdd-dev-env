package uz.ciasev.ubdd_service.service.protocol;

import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ProtocolUbddDataResponseTransportDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ProtocolUbddDataResponseUbddDTO;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.ProtocolUbddInsuranceData;
import uz.ciasev.ubdd_service.entity.ubdd_data.ProtocolUbddTexPassData;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;

import java.util.List;
import java.util.Optional;

public interface ProtocolUbddDataService extends ProtocolDataService<ProtocolUbddData> {

    Optional<ProtocolUbddData> findById(Long id);
    List<ProtocolUbddData> findAll();

    ProtocolUbddData save(ProtocolUbddData data);
    void delete(Long id);

    Optional<ProtocolUbddData> findByProtocolId(Long id);

    Optional<ProtocolUbddTexPassData> findTexPassByProtocolId(Long protocolId);

    Optional<ProtocolUbddTexPassData> findTexPassOfMainProtocolByViolatorId(Long violatorId);

    Optional<ProtocolUbddInsuranceData> findInsuranceByProtocolId(Long id);

    ProtocolUbddDataResponseUbddDTO getResponseUbddDTO(ProtocolUbddDataView data);
    ProtocolUbddDataResponseTransportDTO getResponseTransportDTO(ProtocolUbddDataView data);

}
