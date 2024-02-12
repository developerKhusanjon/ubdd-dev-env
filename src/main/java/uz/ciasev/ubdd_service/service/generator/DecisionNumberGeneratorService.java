package uz.ciasev.ubdd_service.service.generator;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

@Validated
public interface DecisionNumberGeneratorService {
    AdmDocumentNumber generate(Decision decision);
}