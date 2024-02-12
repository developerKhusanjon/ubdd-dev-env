package uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.ciasev.ubdd_service.mvd_core.api.prosecutor_integration.service.ProsecutorIntegrateService;
import uz.ciasev.ubdd_service.config.security.CurrentUser;
import uz.ciasev.ubdd_service.entity.user.User;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${external-systems.url-v0}/prosecutor-integration/")
public class ProsecutorIntegrateController {

    private final ProsecutorIntegrateService prosecutorIntegrateService;

    @GetMapping(path = "search-violations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchViolations(
            @CurrentUser User user,
            @RequestParam(value = "pRequestID", required = false) Long pRequestID,
            @RequestParam(value = "violatorPinpp", required = false) String violatorPinpp,
            @RequestParam(value = "violatorDocSeries", required = false) String violatorDocSeries,
            @RequestParam(value = "violatorDocNumber", required = false) String violatorDocNumber
    ) {

        return ResponseEntity.ok(prosecutorIntegrateService.findViolations(
                pRequestID,
                violatorPinpp,
                violatorDocSeries,
                violatorDocNumber
        ));
    }

}
