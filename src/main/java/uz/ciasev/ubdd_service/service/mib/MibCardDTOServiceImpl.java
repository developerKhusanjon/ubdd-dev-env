package uz.ciasev.ubdd_service.service.mib;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.adm.mib.*;
import uz.ciasev.ubdd_service.entity.mib.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MibCardDTOServiceImpl implements MibCardDTOService {

    private final MibCardMovementService cardMovementService;
    private final MibCardNotificationService notificationService;

    @Transactional
    public MibCardResponseDTO buildDetail(Supplier<MibExecutionCard> supplier) {
        return convertToDetailDTO(supplier.get());
    }

    @Override
    public List<MibCardResponseDTO> buildDetailList(Supplier<List<MibExecutionCard>> supplier) {
        return supplier.get().stream()
                .map(this::convertToDetailDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<MibSyncSendingResponseDTO> buildSyncDetailPage(Supplier<Page<MibSverkaSending>> supplier) {
        return supplier.get().map(MibSyncSendingResponseDTO::new);
    }

    public MibCardResponseDTO convertToDetailDTO(MibExecutionCard card) {
        return new MibCardResponseDTO(
                card,
                cardMovementService.findCurrentByCard(card).orElse(null),
                notificationService.getPresetNotification(card).orElse(null)
        );
    }

    @Override
    public MibSyncSendingResponseDTO convertToDetailDTO(MibSverkaSending sending) {
        return new MibSyncSendingResponseDTO(sending);
    }

    @Transactional
    public MibCardDocumentResponseDTO buildDocumentDetail(Supplier<MibCardDocument> supplier) {
        return convertToDocumentDetailDTO(supplier.get());
    }

    public MibCardDocumentResponseDTO convertToDocumentDetailDTO(MibCardDocument card) {
        return new MibCardDocumentResponseDTO(card);
    }

    public List<MibCardDocumentResponseDTO> buildDocumentList(Supplier<List<MibCardDocument>> supplier) {
        return supplier.get().stream()
                .map(this::convertToDocumentDetailDTO)
                .collect(Collectors.toList());
    }

    public List<MibCardMovementResponseDTO> buildMovementList(Supplier<List<MibCardMovement>> supplier) {
        return supplier.get().stream()
                .map(MibCardMovementResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Page<MibExecutionStatusDocumentResponseDTO> buildMibExecutionDocumentPage(Supplier<Page<MibExecutionStatusDocument>> supplier) {
        return supplier.get()
                .map(MibExecutionStatusDocumentResponseDTO::new);
    }

    @Override
    public List<MibCardReturnRequestListResponseDTO> buildReturnRequestList(Supplier<List<MibCardMovementReturnRequest>> supplier) {
        return supplier.get().stream()
                .map(MibCardReturnRequestListResponseDTO::new)
                .collect(Collectors.toList());
    }
}