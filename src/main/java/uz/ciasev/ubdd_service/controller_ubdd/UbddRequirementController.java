package uz.ciasev.ubdd_service.controller_ubdd;

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
@RequestMapping(path = "${mvd-ciasev.url-v0}", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UbddRequirementController {

    private final RequirementCreateServiceImpl requirementService;

    @GetMapping(path = "/requirement")
    public ResponseEntity<ViolatorRequirementDTO> requirement(@RequestParam("violator_pinpp") @Valid @NotBlank String violatorPinpp) {
        return ResponseEntity.ok(requirementService.groupProtocolsByViolator(violatorPinpp));
    }

    @GetMapping(path = "/pdf/requirement")
    public ResponseEntity<?> ubddRequirement(@RequestParam(value = "violator_pinpp", required = false) @Valid @NotBlank String violatorPinpp,
                                             @RequestParam(value = "vehicle_number", required = false) @Valid @NotBlank String vehicleNumber) {
        return ResponseEntity.ok(
                Optional.ofNullable(violatorPinpp).map(requirementService::groupUbddProtocolsByViolator)
                        .orElse(Optional.ofNullable(vehicleNumber).map(requirementService::groupUbddProtocolsByVehicle)
                                .orElse(List.of())));
    }

    @GetMapping(path = "/wanted-cards-with-arrest")
    public ResponseEntity<?> searchWantedCard(@RequestParam("vehicle_number") @Valid @NotBlank String vehicleNumber) {
        return ResponseEntity.ok(requirementService.groupWantedVehicleBy(vehicleNumber));
    }
}
