package uz.ciasev.ubdd_service.service.court.material;

import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;

import java.time.LocalDate;

public interface CourtMaterialFieldsRegistrationRequest {

    String getRegistrationNumber();

    LocalDate getRegistrationDate();

    CourtMaterialType getMaterialType();
}
