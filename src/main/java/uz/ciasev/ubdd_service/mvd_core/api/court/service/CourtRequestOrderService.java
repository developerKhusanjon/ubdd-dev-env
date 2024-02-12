package uz.ciasev.ubdd_service.mvd_core.api.court.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;
import uz.ciasev.ubdd_service.entity.court.CourtRequestOrder;
import uz.ciasev.ubdd_service.exception.court.CourtRequestOrderException;
import uz.ciasev.ubdd_service.repository.court.CourtRequestOrderRepository;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtRequestOrderService {

    private final CourtRequestOrderRepository orderRepository;


     public <T extends CourtBaseDTO> void applyWithOrderCheck(CourtMethod method, T requestDTO, Consumer<T> consumer) {
        addToOrder(method, requestDTO);
        checkExistNotHandledPreviewClaimId(method, requestDTO);
        consumer.accept(requestDTO);
        removeFromOrder(method, requestDTO);
    }

    public void addToOrder(CourtMethod method, CourtBaseDTO requestDTO) {
        if (!orderRepository.existsByMethodAndCaseIdAndClaimId(method.getId(), requestDTO.getCaseId(), requestDTO.getClaimId())) {
            orderRepository.create(new CourtRequestOrder(method, requestDTO.getCaseId(), requestDTO.getClaimId()));
        }
    }

    public void removeFromOrder(CourtMethod method, CourtBaseDTO requestDTO) {
        orderRepository.deleteByCaseIdAndClaimId(method.getId(), requestDTO.getCaseId(), requestDTO.getClaimId());
    }

    public void checkExistNotHandledPreviewClaimId(CourtMethod method, CourtBaseDTO requestDTO) {
        List<CourtRequestOrder> queue = orderRepository.getQueue(method.getId(), requestDTO.getCaseId(), requestDTO.getClaimId());
        if (!queue.isEmpty()) {
            throw new CourtRequestOrderException(queue.stream().map(CourtRequestOrder::getClaimId).collect(Collectors.toList()));
        }
    }

}
