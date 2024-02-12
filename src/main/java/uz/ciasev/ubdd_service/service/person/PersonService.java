package uz.ciasev.ubdd_service.service.person;

import uz.ciasev.ubdd_service.mvd_core.api.f1.dto.F1Document;
import uz.ciasev.ubdd_service.dto.internal.request.PersonDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.PersonListResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.PersonResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Person buildFromF1(F1Document f1Document);

    Person buildManual(PersonDocumentRequestDTO docDTO, PersonRequestDTO personDTO);

    Person findOrSave(Person person);

    Optional<Person> getByRealPinpp(String pinpp);

    Person findById(Long id);

    PersonListResponseDTO findDetailById(Long id);

    List<PersonListResponseDTO> findAllDetailById(List<Long> ids);

    Optional<Person> findByPinpp(String pinpp);

    PersonResponseDTO convertToDTO(Person person);

    String generateFakePinpp(PersonRequestDTO personDTO, PersonDocumentRequestDTO docDTO);

    List<Person> findViolatorsPinppByAdmCaseId(Long caseId);
}
