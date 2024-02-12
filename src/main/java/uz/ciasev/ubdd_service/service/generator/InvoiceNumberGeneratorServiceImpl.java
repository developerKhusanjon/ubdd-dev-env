package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.repository.AdmEntityNumberRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InvoiceNumberGeneratorServiceImpl implements InvoiceNumberGeneratorService {

    private static final int TEMPLATE_LENGTH = 10;
    private static final String NUMBER_TEMPLATE = "0000000000";
    private final AdmEntityNumberRepository admEntityNumberRepository;

    @Override
    public String generateNumber() {

        String year = getYear();
        String number = getInvoiceNumber();

        return String.join("", year, number);
    }

    private String getYear() {
        return String.valueOf(LocalDate.now().getYear()).substring(2);
    }

    private String getInvoiceNumber() {

        Long curNumber = admEntityNumberRepository.getNextInvoiceNumber();

        StringBuilder builder = new StringBuilder(NUMBER_TEMPLATE);
        builder.replace((TEMPLATE_LENGTH - curNumber.toString().length()), TEMPLATE_LENGTH, curNumber.toString());

        return builder.toString();
    }
}