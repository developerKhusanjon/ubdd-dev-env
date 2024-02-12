package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdmCaseNumberGeneratorServiceImpl implements AdmCaseNumberGeneratorService {

    private static final int TEMPLATE_LENGTH = 8;
    private static final String NUMBER_TEMPLATE = "00000000";
    private final NumberGeneratorService numberGeneratorService;

    @Override
    public String generateNumber(LocalDate openedDate) {
        Long year = (long) openedDate.getYear();
        String number = getAdmCaseNumber(year);
        return String.join("", String.valueOf(year).substring(2), number);
    }

    @Override
    public String generateNumber(AdmCase admCase) {
        return generateNumber(admCase.getOpenedDate());
    }

    @Override
    public String generateSeries() {
        return String.join("", "D");
    }

    private String getYear() {
        return String.valueOf(LocalDate.now().getYear()).substring(2);
    }

    private String getAdmCaseNumber(Long year) {

        Long curNumber = numberGeneratorService.incrementAndGet(EntityNameAlias.CASE, year);

        StringBuilder builder = new StringBuilder(NUMBER_TEMPLATE);
        builder.replace((TEMPLATE_LENGTH - curNumber.toString().length()), TEMPLATE_LENGTH, curNumber.toString());

        return builder.toString();
    }
}