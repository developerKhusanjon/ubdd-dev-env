package uz.ciasev.ubdd_service.service;

import org.springframework.data.domain.Page;
import uz.ciasev.ubdd_service.dto.internal.response.dict.*;
import uz.ciasev.ubdd_service.entity.dict.*;

import java.util.function.Supplier;

public interface ConvertDTOService {

    Page<Long> buildPage(Supplier<Page<Long>> supplier);

    RegionResponseDTO convertRegionToDTO(Region region);
}
