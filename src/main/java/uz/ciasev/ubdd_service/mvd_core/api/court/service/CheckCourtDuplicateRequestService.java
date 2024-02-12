package uz.ciasev.ubdd_service.mvd_core.api.court.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth.FiveCourtMibDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.material.CourtMaterialRegistrationRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh.CourtDecisionFileRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.court.CourtRequestHash;
import uz.ciasev.ubdd_service.exception.court.DuplicateCourtRequestException;
import uz.ciasev.ubdd_service.repository.court.CourtRequestHashRepository;

@Service
@RequiredArgsConstructor
public class CheckCourtDuplicateRequestService {

    private final CourtRequestHashRepository hashRepository;

    public void checkAndRemember(CourtRegistrationStatusRequestDTO requestDTO) {
        checkAndRemember(CourtMethod.COURT_SECOND, requestDTO);
    }

    public void checkAndRemember(CourtMaterialRegistrationRequestDTO requestDTO) {
        checkAndRemember(CourtMethod.COURT_SECOND_MATERIAL, requestDTO);
    }

    public void checkAndRemember(ThirdCourtResolutionRequestDTO requestDTO) {
        checkAndRemember(CourtMethod.COURT_THIRD, requestDTO);
    }

    public void checkAndRemember(ThirdCourtRequest requestDTO) {
        checkAndRemember(CourtMethod.COURT_THIRD_MATERIAL, requestDTO);
    }

    public void checkAndRemember(FiveCourtMibDTO requestDTO) {
        checkAndRemember(CourtMethod.COURT_FIFTH, requestDTO);
    }

    public void checkAndRemember(CourtVictimRequestDTO requestDTO) {
        checkAndRemember(CourtMethod.COURT_NINE, requestDTO);
    }

    public void checkAndRemember(CourtDecisionFileRequestDTO requestDTO) {
        checkAndRemember(CourtMethod.COURT_SEVEN, requestDTO);
    }

    private void checkAndRemember(CourtMethod courtMethod, CourtBaseDTO requestDTO) {
        int requestHashCode = requestDTO.hashCode();
        Long method = (long) courtMethod.getId();

        if (hashRepository.existsByMethodAndHashCode(method, requestHashCode)) {
            throw new DuplicateCourtRequestException(requestHashCode);
        }

        hashRepository.save(new CourtRequestHash(method, requestDTO.getCaseId(), requestDTO.getClaimId(), requestHashCode));
    }
}
