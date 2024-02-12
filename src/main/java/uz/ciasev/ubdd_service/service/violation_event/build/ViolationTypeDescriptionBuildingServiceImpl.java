package uz.ciasev.ubdd_service.service.violation_event.build;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.RadarViolationEventApiDTO;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViolationTypeDescriptionBuildingServiceImpl implements ViolationTypeDescriptionBuildingService {
    private final Map<Long, DynamicDescriptionMethods> dynamicDescriptions = Map.ofEntries(
            Map.entry(2L, this::getSpeedingViolationDescription)
    );

    @Override
    public String getViolationTypeDescription(ViolationEventApiDTO violationEventApiDTO) {//@Nullable RadarViolationEventApiDTO radarEvent) {
        ArticleViolationType violationType = violationEventApiDTO.getViolationType();
        RadarViolationEventApiDTO radarEvent = violationEventApiDTO.getRadarEvent();

        if (violationType == null) return "---";

        Long violationTypeId = violationType.getId();

        if (dynamicDescriptions.containsKey(violationTypeId)) return getDynamicDescription(violationType, radarEvent);

        return Optional.ofNullable(violationType.getRadarFabulaDescription()).orElseGet(violationType::getDefaultName);
    }

    private String getDynamicDescription(ArticleViolationType violationType, @Nullable RadarViolationEventApiDTO radarEvent) {
        if (radarEvent == null) return violationType.getDefaultName();

        return dynamicDescriptions.get(violationType.getId()).executeWith(violationType, radarEvent);
    }

    private String getSpeedingViolationDescription(ArticleViolationType violationType, RadarViolationEventApiDTO radarEvent) {
        Double maxAllowedSpeed = radarEvent.getMaxAllowedSpeed();
        Double fixedSpeed = radarEvent.getFixedSpeed();
        String radarFabulaDescription = violationType.getRadarFabulaDescription();

        if (radarFabulaDescription == null || maxAllowedSpeed == null || fixedSpeed == null) return violationType.getDefaultName();

        return String.format(
                    radarFabulaDescription,
                    maxAllowedSpeed,
                    fixedSpeed,
                    fixedSpeed - 5
                );
    }

    @FunctionalInterface
    private interface DynamicDescriptionMethods {
        String executeWith(ArticleViolationType violationType, @Nullable RadarViolationEventApiDTO radarEvent);
    }
}
