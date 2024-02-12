package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MailNumberGeneratorServiceImpl implements MailNumberGeneratorService {

    private static final int TEMPLATE_LENGTH = 11;
    private static final String NUMBER_TEMPLATE = "00000000000";
    private final NumberGeneratorService numberGeneratorService;

    @Override
    public String generateNumber() {
        Long year = (long) LocalDate.now().getYear();
        String number = getMailNumber(year);
        return String.join("", String.valueOf(year).substring(2), number);
    }

    private String getMailNumber(Long year) {

        Long curNumber = numberGeneratorService.incrementAndGet(EntityNameAlias.MAIL_NOTIFICATION, year);

        StringBuilder builder = new StringBuilder(NUMBER_TEMPLATE);
        builder.replace((TEMPLATE_LENGTH - curNumber.toString().length()), TEMPLATE_LENGTH, curNumber.toString());

        return builder.toString();
    }
}