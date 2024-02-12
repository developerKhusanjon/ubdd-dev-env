package uz.ciasev.ubdd_service.service.prosecutor.protest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.IDResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.protest.ProsecutorProtestDocumentResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.protest.ProsecutorProtestResponseDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtest;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.protest.ProsecutorProtestDocumentProjection;

import javax.transaction.Transactional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ProsecutorProtestDTOServiceImpl implements ProsecutorProtestDTOService {

    @Override
    @Transactional
    public IDResponseDTO buildIdResponseDTO(Supplier<ProsecutorProtest> protestSupplier) {

        ProsecutorProtest protest = protestSupplier.get();
        return IDResponseDTO.of(protest);
    }

    @Override
    public Page<ProsecutorProtestResponseDTO> buildProsecutorProtestPage(Supplier<Page<ProsecutorProtest>> protestPageSupplier) {
        return protestPageSupplier.get().map(ProsecutorProtestResponseDTO::new);
    }

    @Override
    public Page<ProsecutorProtestDocumentResponseDTO> buildDocumentPageByProtestId(
            Supplier<Page<Pair<ProsecutorProtestDocument, String>>>  documentProtestNumberPairsSupplier, Long protestId) {

        return documentProtestNumberPairsSupplier.get().map(pair -> new ProsecutorProtestDocumentResponseDTO(pair.getFirst(), pair.getSecond()));
    }

    @Override
    public Page<ProsecutorProtestDocumentResponseDTO> buildDocumentPageByResolutionId(
            Supplier<Page<ProsecutorProtestDocumentProjection>> documentPageSupplier) {

        return documentPageSupplier.get().map(ProsecutorProtestDocumentResponseDTO::new);
    }
}
