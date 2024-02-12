package uz.ciasev.ubdd_service.service.prosecutor.protest;

import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import uz.ciasev.ubdd_service.dto.internal.response.IDResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.protest.ProsecutorProtestDocumentResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.protest.ProsecutorProtestResponseDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocumentProjection;

import java.util.function.Supplier;


public interface ProsecutorProtestDTOService {

    IDResponseDTO buildIdResponseDTO(Supplier<ProsecutorProtest> protestSupplier);

    Page<ProsecutorProtestResponseDTO> buildProsecutorProtestPage(Supplier<Page<ProsecutorProtest>> protestPageSupplier);

    Page<ProsecutorProtestDocumentResponseDTO> buildDocumentPageByProtestId(
            Supplier<Page<Pair<ProsecutorProtestDocument, String>>> documentPageSupplier, Long protestId);

    Page<ProsecutorProtestDocumentResponseDTO> buildDocumentPageByResolutionId(
            Supplier<Page<ProsecutorProtestDocumentProjection>> documentPageSupplier);
}
