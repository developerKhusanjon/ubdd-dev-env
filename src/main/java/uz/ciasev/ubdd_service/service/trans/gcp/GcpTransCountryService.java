package uz.ciasev.ubdd_service.service.trans.gcp;

import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransCountryCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransCountry;
import uz.ciasev.ubdd_service.service.trans.TransEntityCRDService;

public interface GcpTransCountryService extends TransEntityCRDService<GcpTransCountry, GcpTransCountryCreateRequestDTO> {
}
