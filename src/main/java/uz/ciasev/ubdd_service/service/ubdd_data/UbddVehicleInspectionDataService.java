package uz.ciasev.ubdd_service.service.ubdd_data;

import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddVehicleInspectionDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddVehicleInspectionResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddVehicleInspectionData;
import uz.ciasev.ubdd_service.entity.user.User;

public interface UbddVehicleInspectionDataService {

    UbddVehicleInspectionData save(User user, UbddVehicleInspectionDTO dto);

    UbddVehicleInspectionResponseDTO saveAndGetDTO(User user, UbddVehicleInspectionDTO dto);

    UbddVehicleInspectionData findById(User user, Long id);

    UbddVehicleInspectionResponseDTO findDTOById(User user, Long id);
}
