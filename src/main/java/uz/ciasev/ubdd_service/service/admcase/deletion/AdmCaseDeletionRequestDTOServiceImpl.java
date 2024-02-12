package uz.ciasev.ubdd_service.service.admcase.deletion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.adm.admcase.AdmCaseDeletionRequestResponseDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequestProjection;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class AdmCaseDeletionRequestDTOServiceImpl implements AdmCaseDeletionRequestDTOService {

    @Override
    public Page<AdmCaseDeletionRequestResponseDTO> buildPage(Supplier<Page<AdmCaseDeletionRequestProjection>> supplier) {
        return supplier.get().map(AdmCaseDeletionRequestResponseDTO::new);
    }
}
