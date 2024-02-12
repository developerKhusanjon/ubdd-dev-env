package uz.ciasev.ubdd_service.service.prosecutor.opinion;

import org.springframework.data.domain.Page;
import uz.ciasev.ubdd_service.dto.internal.response.IDResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.opinion.ProsecutorOpinionDocumentResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.adm.prosecutor.opinion.ProsecutorOpinionResponseDTO;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinion;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocument;
import uz.ciasev.ubdd_service.entity.prosecutor.opinion.ProsecutorOpinionDocumentProjection;

import java.util.function.Supplier;

public interface ProsecutorOpinionDTOService {

    IDResponseDTO buildIdResponseDTO(Supplier<ProsecutorOpinion> opinionSupplier);

    Page<ProsecutorOpinionResponseDTO> buildProsecutorOpinionPage(Supplier<Page<ProsecutorOpinion>> opinionPageSupplier);

    Page<ProsecutorOpinionDocumentResponseDTO> buildDocumentPageByOpinionId(
            Supplier<Page<ProsecutorOpinionDocument>> opinionDocumentPageSupplier);

    Page<ProsecutorOpinionDocumentResponseDTO> buildDocumentPageByAdmCaseId(
            Supplier<Page<ProsecutorOpinionDocumentProjection>> opinionDocumentPageSupplier);
}
