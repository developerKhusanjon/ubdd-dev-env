package uz.ciasev.ubdd_service.service.trans.gcp;

import uz.ciasev.ubdd_service.dto.internal.trans.request.gcp.GcpTransGenderCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransGender;
import uz.ciasev.ubdd_service.service.trans.TransEntityCRDService;

public interface GcpTransGenderService extends TransEntityCRDService<GcpTransGender, GcpTransGenderCreateRequestDTO> {
}
