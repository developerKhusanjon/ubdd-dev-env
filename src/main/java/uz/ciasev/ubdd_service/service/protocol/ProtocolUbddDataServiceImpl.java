package uz.ciasev.ubdd_service.service.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ProtocolUbddDataResponseTransportDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.ProtocolUbddDataResponseUbddDTO;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolUbddData;
import uz.ciasev.ubdd_service.entity.ubdd_data.ProtocolUbddTexPassData;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.ProtocolUbddInsuranceData;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;
import uz.ciasev.ubdd_service.repository.protocol.ProtocolUbddDataRepository;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddInsuranceDataRepository;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddTexPassDataRepository;
import uz.ciasev.ubdd_service.service.address.AddressService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProtocolUbddDataServiceImpl implements ProtocolUbddDataService {

    private final ProtocolUbddDataRepository repository;
    private final AddressService addressService;
    private final UbddTexPassDataRepository texPassDataRepository;
    private final UbddInsuranceDataRepository insuranceDataRepository;

    @Override
    public ProtocolUbddData save(ProtocolUbddData data) {
//        Optional.ofNullable(data.getVehicleOwnerAddress()).map(addressService::create);
//        Optional.ofNullable(data.getRegistrationAddress()).map(addressService::create);
        return repository.save(data);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<ProtocolUbddData> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ProtocolUbddData> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ProtocolUbddData> findByProtocolId(Long id) {
        return repository.findByProtocolId(id);
    }

    @Override
    public Optional<ProtocolUbddTexPassData> findTexPassByProtocolId(Long protocolId) {
        return texPassDataRepository.findByProtocolId(protocolId);
    }

    @Override
    public Optional<ProtocolUbddTexPassData> findTexPassOfMainProtocolByViolatorId(Long violatorId) {
        return texPassDataRepository.findMainByViolatorId(violatorId);
    }

    @Override
    public Optional<ProtocolUbddInsuranceData> findInsuranceByProtocolId(Long protocolId) {
        return insuranceDataRepository.findByProtocolId(protocolId);
    }

    @Override
    public ProtocolUbddDataResponseUbddDTO getResponseUbddDTO(ProtocolUbddDataView data) {
        ProtocolUbddDataResponseUbddDTO response = null;
        if (data != null) {
            AddressResponseDTO addressDTO = Optional.ofNullable(data.getRegistrationAddressId()).map(addressService::findDTOById).orElse(null);
            response = new ProtocolUbddDataResponseUbddDTO(data, addressDTO);
        }
        return response;
    }

    @Override
    public ProtocolUbddDataResponseTransportDTO getResponseTransportDTO(ProtocolUbddDataView data) {
        ProtocolUbddDataResponseTransportDTO response = null;
        if (data != null) {
            AddressResponseDTO addressDTO = Optional.ofNullable(data.getVehicleOwnerAddressId()).map(addressService::findDTOById).orElse(null);
            response = new ProtocolUbddDataResponseTransportDTO(data, addressDTO);
        }
        return response;
    }
}
