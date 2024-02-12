package uz.ciasev.ubdd_service.service.ubdd_data;

import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDriverLicenseDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDriverLicenseResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDrivingLicenseData;
import uz.ciasev.ubdd_service.entity.user.User;

public interface UbddDrivingLicenseDataService {

    UbddDrivingLicenseData save(User user, UbddDriverLicenseDTO dto);

    UbddDriverLicenseResponseDTO saveAndGetDTO(User user, UbddDriverLicenseDTO dto);

    UbddDrivingLicenseData findById(User user, Long id);

    UbddDriverLicenseResponseDTO findDTOById(User user, Long id);

    UbddDrivingLicenseData update(User user, Long id, UbddDriverLicenseDTO dto);
}
