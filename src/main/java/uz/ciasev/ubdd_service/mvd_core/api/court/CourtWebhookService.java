package uz.ciasev.ubdd_service.mvd_core.api.court;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.eight.EightCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.fifth.FiveCourtMibDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.material.CourtMaterialRegistrationRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestExternalDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh.CourtDecisionFileRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;

public interface CourtWebhookService {

    CourtResponseDTO acceptSecondMethod(CourtRequestDTO<CourtRegistrationStatusRequestDTO> requestDTO);

    CourtResponseDTO acceptThirdMethod(CourtRequestDTO<ThirdCourtResolutionRequestDTO> requestDTO);

    CourtResponseDTO acceptFiveMethod(CourtRequestDTO<FiveCourtMibDTO> requestDTO);

    CourtResponseDTO acceptSevenMethod(CourtRequestDTO<CourtDecisionFileRequestDTO> requestDTO);

    CourtRequestDTO<EightCourtResolutionRequestDTO> acceptEightMethod(String series, String number);

    CourtResponseDTO acceptNineMethod(CourtRequestDTO<CourtVictimRequestExternalDTO> victim);

    CourtResponseDTO acceptSecondMaterialMethod(CourtRequestDTO<CourtMaterialRegistrationRequestDTO> requestDTO);
}
