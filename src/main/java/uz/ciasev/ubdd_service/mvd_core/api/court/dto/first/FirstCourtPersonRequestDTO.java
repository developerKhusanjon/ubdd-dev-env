package uz.ciasev.ubdd_service.mvd_core.api.court.dto.first;

import java.time.LocalDate;

public interface FirstCourtPersonRequestDTO {
    void setId(Long value);
    void setPinpp(Long value);
    void setLastName(String value);
    void setFirstName(String value);
    void setMiddleName(String value);
    void setLastNameKir(String value);
    void setFirstNameKir(String value);
    void setMiddleNameKir(String value);
    void setGender(Long value);
    void setCitizenship(Long value);
    void setBirthdate(LocalDate value);
    void setDistrict(Long value);
    void setCurrentAddress(String value);

    void setAge(Long value);
}