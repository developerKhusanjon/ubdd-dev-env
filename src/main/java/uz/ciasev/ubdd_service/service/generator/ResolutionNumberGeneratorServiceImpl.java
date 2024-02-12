package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ResolutionNumberGeneratorServiceImpl implements ResolutionNumberGeneratorService {

    private static final int TEMPLATE_LENGTH = 8;
    private static final String NUMBER_TEMPLATE = "00000000";
    private final NumberGeneratorService numberGeneratorService;
    
    private String generateNumber(String organ, LocalDateTime resolutionTime) {
        Long year = (long) resolutionTime.getYear();
        String number = getResolutionNumber(year);
        return String.join("", String.valueOf(year).substring(2), organ, number);
    }

    @Override
    public String generateNumber(ResolutionCreateRequest resolution) {
        String organCode = resolution.getOrgan().getCode();
        return generateNumber(organCode, resolution.getResolutionTime());
    }

    private String generateSeries(String region) {
        return String.join("", "R", region);
    }

    @Override
    public String generateSeries(ResolutionCreateRequest resolution) {
        String regionSerialName = resolution.getRegion().getSerialName();
        return generateSeries(regionSerialName);
    }

    private String getYear() {
        return String.valueOf(LocalDate.now().getYear()).substring(2);
    }

    private String getResolutionNumber(Long year) {

        Long curNumber = numberGeneratorService.incrementAndGet(EntityNameAlias.RESOLUTION, year);

        StringBuilder builder = new StringBuilder(NUMBER_TEMPLATE);
        builder.replace((TEMPLATE_LENGTH - curNumber.toString().length()), TEMPLATE_LENGTH, curNumber.toString());

        return builder.toString();
    }
}