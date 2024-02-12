package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.helper.resolution.ResolutionHelper;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.DefendantRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResultAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.exception.court.CourtStatusAndActionNotConsistentException;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ActionCheckUtils {

    private final ResolutionHelper resolutionHelper;

    public void checkResolution(ThirdCourtRequest request) {
        resolutionHelper.check(request);
        ActionCheckUtils.checkNoOneViolatorHasFinalResult(request, CourtFinalResultAlias.NOT_RELATE);
    }

    public static void checkAllViolatorHasFinalResult(ThirdCourtRequest courtRequest, CourtFinalResultAlias alias) {
        boolean hasNull = courtRequest.getDefendants().stream()
                .map(DefendantRequest::getFinalResult)
                .allMatch(Objects::isNull);

        if (hasNull) {
//            throw new CourtValidationException("У некоторых нарушиелей не заполнен finalResult");
            throw new CourtValidationException("Some defendant have no finalResult");
        }

        boolean allMatch = courtRequest.getDefendants().stream()
                .map(DefendantRequest::getFinalResult)
                .allMatch(fr -> fr.is(alias));

        if (!allMatch) {
//            throw new CourtValidationException("Некоторые нарушиели не имеют finalResult " + alias.getCourtCode());
            throw new CourtValidationException("Some defendant have no finalResult " + alias.getCourtCode());
        }


    }

    public static void checkNoOneViolatorHasFinalResult(ThirdCourtRequest courtRequest, CourtFinalResultAlias alias) {
        boolean anyMatch = courtRequest.getDefendants().stream()
                .map(DefendantRequest::getFinalResult)
                .filter(Objects::nonNull)
                .anyMatch(fr -> fr.is(alias));

        if (anyMatch) {
//            throw new CourtValidationException("Некоторые нарушиели имеют finalResult " + alias.getCourtCode());
            throw new CourtValidationException("Some defendant have finalResult " + alias.getCourtCode());
        }
    }

    public static void checkAllViolatorHasNoFinalResult(ThirdCourtRequest courtRequest) {
        boolean allNoHas = courtRequest.getDefendants().stream()
                .map(DefendantRequest::getFinalResult)
                .filter(Objects::nonNull)
                .count() == 0;

        if (!allNoHas) {
//            throw new CourtValidationException("У некоторых нарушиелей заполнен finalResult");
            throw new CourtValidationException("Some defendant have finalResult");
        }
    }

    public static void checkStatus(ThirdCourtRequest request, CourtStatusAlias statusAlias, CourtActionName actionName) {
        if (!request.getStatus().is(statusAlias)) {
            throw new CourtStatusAndActionNotConsistentException(actionName, statusAlias, request.getStatus().getAlias());
        }
    }

    public static void checkOneDefendant(ThirdCourtRequest request) {
        if (request.getDefendants().size() != 1)
            throw new NotImplementedException("Material resolution implement only for one violator");
    }

    public static void checkReject(ThirdCourtRequest request) {
        DefendantRequest defendant = request.getDefendants().stream().findFirst().get();

        if (CourtHelper.extractCourtMaterialRejectBase(request).isEmpty()) {
            throw new CourtValidationException(CourtValidationException.REJECT_BASE_REQUIRED_FOR_REJECTED_MATERIAL_RESOLUTION);
        }
//        if (defendant.getFinalResult() != null) {
//            throw new CourtValidationException(CourtValidationException.FINAL_RESULT_SET_FOR_REJECTED_MATERIAL_RESOLUTION);
//        }
        if (defendant.getFinalResult() == null) {
            return;
        }
        if (defendant.getFinalResult().not(CourtFinalResultAlias.MATERIAL_REJECTED)) {
            throw new CourtValidationException(CourtValidationException.FINAL_RESULT_MAST_BE_REJECT_OR_EMPTY_FOR_REJECTED_MATERIAL_RESOLUTION);
        }
    }
}
