package uz.ciasev.ubdd_service.controller_ubdd;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.UbddCourtRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.service.court.methods.UbddCourtService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${mvd-ciasev.url-v0}/ubdd", produces = MediaType.APPLICATION_JSON_VALUE)
public class UbddCourtController {

    private final UbddCourtService ubddCourtService;

    @PostMapping("/sent-to-court")
    public void postSendGeneral(@RequestBody @Valid UbddCourtRequest request) {
        ubddCourtService.sentCourt(request);
    }


    @PostMapping("/court-resolution")
    public void acceptCourtResolution(@RequestBody @Valid ThirdCourtResolutionRequestDTO request) {
        ubddCourtService.acceptUbddCourtResolution(request);
    }

}