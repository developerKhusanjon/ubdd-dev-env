package uz.ciasev.ubdd_service.service.generator;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.service.protocol.ProtocolCreateRequest;


@Validated
public interface ProtocolNumberGeneratorService {

    String generateNumber(ProtocolCreateRequest protocol);
    String generateSeries(ProtocolCreateRequest protocol);
}