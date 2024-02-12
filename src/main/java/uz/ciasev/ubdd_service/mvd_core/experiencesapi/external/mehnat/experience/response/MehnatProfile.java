package uz.ciasev.ubdd_service.mvd_core.experiencesapi.external.mehnat.experience.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MehnatProfile {
    @JsonProperty("birth_date")
    private String birthDate;
    private Integer gender;
    @JsonProperty("person_name")
    private String personName;
    @JsonProperty("person_patronymic")
    private String personPatronymic;
    @JsonProperty("person_surname")
    private String personSurname;
    private String pin;

}
