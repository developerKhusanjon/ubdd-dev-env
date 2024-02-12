package uz.ciasev.ubdd_service.service.violator;

import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorContactDataRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.actor.ViolatorDetailResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorDetail;

import java.util.List;
import java.util.Optional;

public interface ViolatorDetailService {

    ViolatorDetailResponseDTO findDTOById(Long id);

    ViolatorDetail save(ViolatorDetail violatorDetail);

    void setResidenceAddressForAll(Violator violator, Address address);

    ViolatorDetail save(User user, ViolatorDetail violatorDetail);

    ViolatorDetail findById(Long id);

    ViolatorDetail getMainByViolatorId(Long id);

    Optional<ViolatorDetail> findMainByViolatorId(Long id);

    List<ViolatorDetail> findByViolatorId(Long id);

    ViolatorDetail updateViolatorDetail(User user, Long violatorId, Long protocolId, ViolatorDetailRequestDTO violatorDetailRequestDTO);

    ViolatorDetailResponseDTO convertToDTO(ViolatorDetail violatorDetail);

    ViolatorDetail findByViolatorIdAndProtocolId(Long violatorId, Long protocolId);

    ViolatorDetail updateViolatorContactData(User user, Long violatorId, Long protocolId, ViolatorContactDataRequestDTO requestDTO);
}
