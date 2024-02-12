package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;

@RequiredArgsConstructor
public class ValueNumberGeneratorService implements ResolutionNumberGeneratorService, DecisionNumberGeneratorService {

    private final String number;
    private final boolean isUniqueNumber;

    @Override
    public String generateNumber(ResolutionCreateRequest resolution) {
        return number;
    }

    @Override
    public String generateSeries(ResolutionCreateRequest resolution) {
        return "";
    }

    private AdmDocumentNumber generate() {
        return AdmDocumentNumber.builder()
                .series("")
                .number(number)
                .isUniqueNumber(isUniqueNumber)
                .build();
    }

    @Override
    public AdmDocumentNumber generate(Decision decision) {
        return generate();
    }
}