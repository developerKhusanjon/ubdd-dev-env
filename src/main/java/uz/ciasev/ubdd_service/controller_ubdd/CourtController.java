package uz.ciasev.ubdd_service.controller_ubdd;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.ciasev.ubdd_service.config.security.CurrentUser;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.UbddCourtRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.service.admcase.CourtPreparingService;
import uz.ciasev.ubdd_service.service.court.methods.UbddCourtService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${mvd-ciasev.url-v0}/ubdd/sent-to-court", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourtController {

    private final CourtPreparingService courtService;
    private final UbddCourtService ubddCourtService;

    @PostMapping("sent-to-court")
    public void postSendGeneral(@CurrentUser User user,
                                @RequestBody @Valid UbddCourtRequest request) {
        courtService.validateCourtSend(user, request.getCaseId(), ActionAlias.SEND_ADM_CASE_TO_COURT);
        ubddCourtService.sentCourt(request);
    }


    //@PostMapping("/resolution")
    public CourtResponseDTO executeResolution(@CurrentUser User user,
                                              @RequestBody @Valid CourtRequestDTO<ThirdCourtResolutionRequestDTO> requestDTO) {
        return ubddCourtService.courtResolution(requestDTO);
    }

}