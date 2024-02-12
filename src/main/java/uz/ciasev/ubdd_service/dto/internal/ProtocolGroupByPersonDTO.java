package uz.ciasev.ubdd_service.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolGroupByPersonDTO {

    @NotBlank(message = ErrorCode.FIRST_NAME_KIR_REQUIRED)
    private String firstNameKir;

    private String secondNameKir;

    @NotBlank(message = ErrorCode.LAST_NAME_KIR_REQUIRED)
    private String lastNameKir;

    @NotBlank(message = ErrorCode.FIRST_NAME_LAT_REQUIRED)
    private String firstNameLat;

    private String secondNameLat;

    @NotBlank(message = ErrorCode.LAST_NAME_LAT_REQUIRED)
    private String lastNameLat;

    @NotNull(message = ErrorCode.BIRTH_DATE_REQUIRED)
    @PastOrPresent(message = ErrorCode.BIRTH_DATE_INVALID)
    private LocalDate birthDate;

    @NotBlank(message = ErrorCode.ACTUAL_ADDRESS_REQUIRED)
    private String actualAddress;

    @Size(max = 1000, message = ErrorCode.REQUIREMENT_SIZE_MORE_THEN_1000)
    private List<Long> protocols;

    public ProtocolGroupByPersonDTO(Person person, String actualAddress, List<Long> protocols) {

        this.firstNameKir = person.getFirstNameKir();
        this.secondNameKir = person.getSecondNameKir();
        this.lastNameKir = person.getLastNameKir();

        this.firstNameLat = person.getFirstNameLat();
        this.secondNameLat = person.getSecondNameLat();
        this.lastNameLat = person.getLastNameLat();

        this.birthDate = person.getBirthDate();

        this.actualAddress = actualAddress;

        this.protocols = protocols;
    }

    public List<Long> getProtocols() {
        return Optional.ofNullable(protocols).orElse(List.of());
    }
}
