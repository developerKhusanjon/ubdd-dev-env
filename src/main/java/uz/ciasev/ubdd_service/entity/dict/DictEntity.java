package uz.ciasev.ubdd_service.entity.dict;

import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.time.LocalDate;

public interface DictEntity {

    Long getId();

    MultiLanguage getName();

    Boolean getIsActive();

    String getCode();

    LocalDate getOpenedDate();

    LocalDate getClosedDate();
}
