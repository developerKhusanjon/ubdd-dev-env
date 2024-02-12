package uz.ciasev.ubdd_service.service.mib;

import org.springframework.data.domain.Page;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.*;
import uz.ciasev.ubdd_service.entity.mib.*;

import java.util.List;
import java.util.function.Supplier;

public interface MibCardDTOService {

    MibCardResponseDTO buildDetail(Supplier<MibExecutionCard> supplier);

    List<MibCardResponseDTO> buildDetailList(Supplier<List<MibExecutionCard>> supplier);

    Page<MibSyncSendingResponseDTO> buildSyncDetailPage(Supplier<Page<MibSverkaSending>> supplier);

    MibCardResponseDTO convertToDetailDTO(MibExecutionCard card);

    MibSyncSendingResponseDTO convertToDetailDTO(MibSverkaSending sending);

    MibCardDocumentResponseDTO buildDocumentDetail(Supplier<MibCardDocument> supplier);

    MibCardDocumentResponseDTO convertToDocumentDetailDTO(MibCardDocument card);

    List<MibCardDocumentResponseDTO> buildDocumentList(Supplier<List<MibCardDocument>> supplier);

    List<MibCardMovementResponseDTO> buildMovementList(Supplier<List<MibCardMovement>> supplier);

    Page<MibExecutionStatusDocumentResponseDTO> buildMibExecutionDocumentPage(Supplier<Page<MibExecutionStatusDocument>> supplier);

    List<MibCardReturnRequestListResponseDTO> buildReturnRequestList(Supplier<List<MibCardMovementReturnRequest>> supplier);
}