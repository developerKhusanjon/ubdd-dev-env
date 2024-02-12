package uz.ciasev.ubdd_service.service.generator;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;


@Validated
public interface ResolutionNumberGeneratorService {
    
    String generateNumber(ResolutionCreateRequest resolution);
    
    String generateSeries(ResolutionCreateRequest resolution);
}