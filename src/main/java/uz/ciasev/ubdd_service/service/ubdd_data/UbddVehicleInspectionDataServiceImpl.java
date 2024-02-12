package uz.ciasev.ubdd_service.service.ubdd_data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddVehicleInspectionDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddVehicleInspectionResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddVehicleInspectionData;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddVehicleInspectionDataRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UbddVehicleInspectionDataServiceImpl implements UbddVehicleInspectionDataService {

    private final UbddVehicleInspectionDataRepository repository;

    @Override
    public UbddVehicleInspectionData save(User user, UbddVehicleInspectionDTO dto) {

        UbddVehicleInspectionData newData = new UbddVehicleInspectionData(dto);

        newData.setUserId(user.getId());

        return repository.save(newData);
    }

    @Override
    public UbddVehicleInspectionResponseDTO saveAndGetDTO(User user, UbddVehicleInspectionDTO dto) {

        return Optional.of(save(user, dto)).map(UbddVehicleInspectionResponseDTO::new).get();
    }

    @Override
    public UbddVehicleInspectionData findById(User user, Long id) {

        return repository.findById(id).orElseThrow(() -> new EntityByIdNotFound(UbddVehicleInspectionData.class, id));
    }

    @Override
    public UbddVehicleInspectionResponseDTO findDTOById(User user, Long id) {

        return Optional.of(findById(user, id)).map(UbddVehicleInspectionResponseDTO::new).get();
    }
}
