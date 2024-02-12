package uz.ciasev.ubdd_service.mvd_core.api.autocon.service;

import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconCloseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconOpenDTO;

public interface AutoconApiService {

    void addDebtor(AutoconOpenDTO requestBody);

    void deleteDebtor(AutoconCloseDTO requestBody);

    String getToken();
}
