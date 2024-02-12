package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution;

import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.CompensationRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtCompensationRequestDTO;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CompensationHelper {

    public void check(ThirdCourtRequest request) {
        if (request.getDefendants() == null || request.getDefendants().isEmpty()) return;

        for (DefendantRequest defendant : request.getDefendants()) {
            List<CompensationRequest> compensations = defendant.getCompensations();

            if (compensations == null || compensations.isEmpty()) return;

            for (CompensationRequest compensation : compensations) {
                if (compensation.getAmount() == null) {
                    throw new CourtValidationException(CourtValidationException.COMPENSATION_AMOUNT_REQUIRED);
                }

                if (compensation.getCurrency() == null) {
                    throw new CourtValidationException(CourtValidationException.COMPENSATION_CURRENCY_REQUIRED);
                }

                if (compensation.getPayerType() == null) {
                    throw new CourtValidationException(CourtValidationException.COMPENSATION_PAYER_TYPE_REQUIRED);
                }

                if (compensation.getVictimType() == null) {
                    throw new CourtValidationException(CourtValidationException.COMPENSATION_VICTIM_TYPE_REQUIRED);
                }

                //  2022-08-03 Бегзод разрешил сохранять ущербы потерпевшим без указания потерпевшего для суда. Суд упорно не хочет указывать их, а решения застревают.
//                if (compensation.getVictimType().is(VictimTypeAlias.VICTIM)) {
//                    if (compensation.getVictimId() == null) {
//                        throw new CourtValidationException(CourtValidationException.COMPENSATION_VICTIM_ID_REQUIRED);
//                    }
//                }
            }
        }
    }

    public List<CourtCompensationRequestDTO> build(DefendantRequest defendant) {
        List<CourtCompensationRequestDTO> compensations = new ArrayList<>();

            List<CompensationRequest> courtCompensations = defendant.getCompensations();
            if (courtCompensations != null) {

                for (CompensationRequest courtCompensation : defendant.getCompensations()) {

                    CourtCompensationRequestDTO compensation = CourtCompensationRequestDTO.builder()
                            .violatorId(defendant.getViolatorId())
                            .amount(courtCompensation.getAmount())
                            .currency(courtCompensation.getCurrency())
                            .payerTypeId(courtCompensation.getPayerType().getId())
                            .payerAdditionalInfo(Optional.ofNullable(courtCompensation.getPayerInn()).map(String::valueOf).orElse(null))
                            .victimType(courtCompensation.getVictimType())
                            .victimId(courtCompensation.getVictimId())
                            .build();

                    compensations.add(compensation);
                }

            }

        return compensations;
    }

}
