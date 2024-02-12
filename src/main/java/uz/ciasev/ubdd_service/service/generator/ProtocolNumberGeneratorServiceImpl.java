package uz.ciasev.ubdd_service.service.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.EntityNameAlias;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.service.protocol.ProtocolCreateRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProtocolNumberGeneratorServiceImpl implements ProtocolNumberGeneratorService {

    private static final int TEMPLATE_LENGTH = 8;
    private static final String NUMBER_TEMPLATE = "00000000";
    private final NumberGeneratorService numberGeneratorService;

    private String generateNumber(Organ organ, LocalDateTime registrationTime) {
        Long year = (long) registrationTime.getYear();
        String number = getProtocolNumber(year);
        return String.join("", String.valueOf(year).substring(2), organ.getCode(), number);
    }

    @Override
    public String generateNumber(ProtocolCreateRequest protocol) {
        return generateNumber(protocol.getOrgan(), protocol.getRegistrationTime());
    }

    private String generateSeries(Region region) {
        return String.join("", "P", region.getSerialName());
    }

    @Override
    public String generateSeries(ProtocolCreateRequest protocol) {
        return generateSeries(protocol.getRegion());
    }

    private String getYear() {
        return String.valueOf(LocalDate.now().getYear()).substring(2);
    }

    private String getProtocolNumber(Long year) {

        Long curNumber = numberGeneratorService.incrementAndGet(EntityNameAlias.PROTOCOL, year);

        StringBuilder builder = new StringBuilder(NUMBER_TEMPLATE);
        builder.replace((TEMPLATE_LENGTH - curNumber.toString().length()), TEMPLATE_LENGTH, curNumber.toString());

        return builder.toString();
    }
}