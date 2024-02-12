package uz.ciasev.ubdd_service.service.main.resolution;

import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;

public interface DiscountService {
    PenaltyPunishment.DiscountVersion calculateDiscount(SingleResolutionRequestDTO requestDTO);
}
