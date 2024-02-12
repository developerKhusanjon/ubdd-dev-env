package uz.ciasev.ubdd_service.mvd_core.api.court.service.nine;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.CourtVictimRequestDTO;
import uz.ciasev.ubdd_service.entity.Person;

import javax.validation.Valid;

@Validated
public interface NineMethodFromCourtService {

    Person accept(@Valid CourtVictimRequestDTO victim);
}
