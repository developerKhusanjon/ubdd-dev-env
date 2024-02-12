package uz.ciasev.ubdd_service.service.ubdd_data;

import uz.ciasev.ubdd_service.dto.internal.request.protocol.UbddInsuranceDataRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.protocol.UbddInsuranceDataResponseDTO;
import uz.ciasev.ubdd_service.entity.ubdd_data.insurance.UbddInsuranceData;
import uz.ciasev.ubdd_service.entity.user.User;

public interface UbddInsuranceDataService {

    UbddInsuranceData save(User user, UbddInsuranceDataRequestDTO dto);

    UbddInsuranceDataResponseDTO saveAndGetDTO(User user, UbddInsuranceDataRequestDTO dto);

    UbddInsuranceData findById(User user, Long id);

    UbddInsuranceDataResponseDTO findDTOById(User user, Long id);
}
