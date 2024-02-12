package uz.ciasev.ubdd_service.mvd_core.api.ubdd;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.ciasev.ubdd_service.config.security.SecurityConstants;
import uz.ciasev.ubdd_service.dto.internal.ViolatorRequirementDTO;
import uz.ciasev.ubdd_service.service.document.generated.RequirementCreateServiceImpl;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "${external-systems.url-v0}/ubdd", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Profile("!publicapi")
public class UbddExternalApiProtocolController {

    private final RequirementCreateServiceImpl requirementService;

    @GetMapping(path = "/requirement")
    @RolesAllowed(SecurityConstants.EXTERNAL)
    public ResponseEntity<ViolatorRequirementDTO> requirement(@RequestParam("violator_pinpp") @Valid @NotBlank String violatorPinpp) {
        return ResponseEntity.ok(requirementService.groupProtocolsByViolator(violatorPinpp));
    }

    @GetMapping(path = "/pdf/requirement")
    @RolesAllowed(SecurityConstants.EXTERNAL)
    public ResponseEntity<?> ubddRequirement(@RequestParam(value = "violator_pinpp", required = false) @Valid @NotBlank String violatorPinpp,
                                             @RequestParam(value = "vehicle_number", required = false) @Valid @NotBlank String vehicleNumber) {
        return ResponseEntity.ok(
                Optional.ofNullable(violatorPinpp).map(requirementService::groupUbddProtocolsByViolator)
                        .orElse(Optional.ofNullable(vehicleNumber).map(requirementService::groupUbddProtocolsByVehicle)
                                .orElse(List.of())));
    }

    @GetMapping(path = "/wanted-cards-with-arrest")
    @RolesAllowed(SecurityConstants.EXTERNAL)
    public ResponseEntity<?> searchWantedCard(@RequestParam("vehicle_number") @Valid @NotBlank String vehicleNumber) {
        return ResponseEntity.ok(requirementService.groupWantedVehicleBy(vehicleNumber));
    }
}
