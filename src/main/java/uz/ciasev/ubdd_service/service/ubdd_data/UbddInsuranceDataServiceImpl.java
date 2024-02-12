package uz.ciasev.ubdd_service.service.ubdd_data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.UbddInsuranceDataRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.UbddInsuranceDataResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.UbddInsuranceData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddInsuranceDataRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbddInsuranceDataServiceImpl implements UbddInsuranceDataService {

    private final UbddInsuranceDataRepository repository;

    @Override
    public UbddInsuranceData save(User user, UbddInsuranceDataRequestDTO dto) {

        UbddInsuranceData ubddInsuranceData = new UbddInsuranceData(user);

        ubddInsuranceData.apply(dto);

        return repository.save(ubddInsuranceData);
    }

    @Override
    public UbddInsuranceDataResponseDTO saveAndGetDTO(User user, UbddInsuranceDataRequestDTO dto) {

        return Optional.of(save(user, dto)).map(UbddInsuranceDataResponseDTO::new).get();
    }

    @Override
    public UbddInsuranceData findById(User user, Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(UbddInsuranceData.class, id));
    }

    @Override
    public UbddInsuranceDataResponseDTO findDTOById(User user, Long id) {

        return Optional.of(findById(user, id)).map(UbddInsuranceDataResponseDTO::new).get();
    }
}
