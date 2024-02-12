package uz.ciasev.ubdd_service.service.generator;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
public interface AdmCaseNumberGeneratorService {

    String generateNumber(@NotNull LocalDate openedDate);
    String generateNumber(AdmCase admCase);
    String generateSeries();
}