package uz.ciasev.ubdd_service.service.prosecutor.opinion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.IDResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.opinion.ProsecutorOpinionDocumentResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.opinion.ProsecutorOpinionResponseDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinion;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocumentProjection;

import javax.transaction.Transactional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ProsecutorOpinionDTOServiceImpl implements ProsecutorOpinionDTOService {
    @Override
    @Transactional
    public IDResponseDTO buildIdResponseDTO(Supplier<ProsecutorOpinion> opinionSupplier) {
        return IDResponseDTO.of(opinionSupplier.get());
    }

    @Override
    public Page<ProsecutorOpinionResponseDTO> buildProsecutorOpinionPage(Supplier<Page<ProsecutorOpinion>> opinionPageSupplier) {
        return opinionPageSupplier.get().map(ProsecutorOpinionResponseDTO::new);
    }

    @Override
    public Page<ProsecutorOpinionDocumentResponseDTO> buildDocumentPageByOpinionId(
            Supplier<Page<ProsecutorOpinionDocument>> opinionDocumentPageSupplier) {
        return opinionDocumentPageSupplier.get().map(ProsecutorOpinionDocumentResponseDTO::new);
    }

    @Override
    public Page<ProsecutorOpinionDocumentResponseDTO> buildDocumentPageByAdmCaseId(
            Supplier<Page<ProsecutorOpinionDocumentProjection>> opinionDocumentPageSupplier) {
        return opinionDocumentPageSupplier.get().map(ProsecutorOpinionDocumentResponseDTO::new);
    }
}
