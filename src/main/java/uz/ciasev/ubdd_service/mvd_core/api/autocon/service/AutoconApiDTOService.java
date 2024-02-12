package uz.ciasev.ubdd_service.mvd_core.api.autocon.service;

import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconCloseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.autocon.dto.AutoconOpenDTO;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;

public interface AutoconApiDTOService {

    AutoconOpenDTO buildOpenDTO(AutoconSending sending);

    AutoconCloseDTO buildCloseDTO(AutoconSending sending);
}
