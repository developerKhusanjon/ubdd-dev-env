package uz.ciasev.ubdd_service.service.admcase;

import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseDeclineRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseSendRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseMovementDetailResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseMovementListResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseMovement;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface AdmCaseMovementService {

    AdmCaseMovement sendAdmCase(User user, Long admCaseId, AdmCaseSendRequestDTO requestDTO);

    AdmCaseMovement sendAdmCaseFixedOrgan(User user, Long admCaseId, AdmCaseSendRequestDTO requestDTO);

    void declineAdmCase(User user, Long admCaseId, AdmCaseDeclineRequestDTO requestDTO);

    void cancel(User user, Long admCaseId);

    List<AdmCaseMovementListResponseDTO> findAllByAdmCaseId(User user, Long admCaseId);

    AdmCaseMovementDetailResponseDTO findDetailById(User user, Long id);

}
