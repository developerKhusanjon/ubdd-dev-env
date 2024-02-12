package uz.ciasev.ubdd_service.service.generator;

import org.springframework.validation.annotation.Validated;

@Validated
public interface MailNumberGeneratorService {

    String generateNumber();
}