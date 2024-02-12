package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DecisionNumberGeneratorServiceImpl implements DecisionNumberGeneratorService {

    private static final int TEMPLATE_LENGTH = 8;
    private static final String NUMBER_TEMPLATE = "00000000";
    private final NumberGeneratorService numberGeneratorService;

    @Override
    public AdmDocumentNumber generate(Decision decision) {
        return AdmDocumentNumber.builder()
                .series(generateSeries(decision))
                .number(generateNumber(decision))
                .isUniqueNumber(true)
                .build();
    }


    private String generateNumber(String organ, LocalDateTime resolutionTime) {
        Long year = (long) resolutionTime.getYear();
        String number = getDecisionNumber(year);
        return String.join("", String.valueOf(year).substring(2), organ, number);
    }

    public String generateNumber(Decision decision) {
        String organCode = decision.getResolution().getOrgan().getCode();
        return generateNumber(organCode, decision.getResolution().getResolutionTime());
    }

    public String generateSeries(Decision decision) {
        String regionSerialName = decision.getResolution().getRegion().getSerialName();
        return generateSeries(regionSerialName);
    }

    private String generateSeries(String region) {
        return String.join("", "Q", region);
    }

    private String getYear() {
        return String.valueOf(LocalDate.now().getYear()).substring(2);
    }

    private String getDecisionNumber(Long year) {

        Long curNumber = numberGeneratorService.incrementAndGet(EntityNameAlias.DECISION, year);

        StringBuilder builder = new StringBuilder(NUMBER_TEMPLATE);
        builder.replace((TEMPLATE_LENGTH - curNumber.toString().length()), TEMPLATE_LENGTH, curNumber.toString());

        return builder.toString();
    }
}