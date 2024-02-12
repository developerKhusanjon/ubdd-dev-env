package uz.ciasev.ubdd_service.service.admcase.deletion;

import org.springframework.data.domain.Page;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseDeletionRequestResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequestProjection;

import java.util.function.Supplier;

public interface AdmCaseDeletionRequestDTOService {

    Page<AdmCaseDeletionRequestResponseDTO> buildPage(Supplier<Page<AdmCaseDeletionRequestProjection>> supplier);
}
