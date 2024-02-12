package uz.ciasev.ubdd_service.mvd_core.api.texpass.service;

import uz.ciasev.ubdd_service.dto.internal.response.F1DocumentListDTO;

import java.util.List;

public interface TexPassService {

    List<F1DocumentListDTO> findPersonByChip(String number);
    F1DocumentListDTO getPersonByChip(String number);
}
