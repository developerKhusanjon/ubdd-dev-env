package uz.ciasev.ubdd_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.dict.*;
import uz.ciasev.ubdd_service.entity.dict.*;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ConvertDTOServiceImpl implements ConvertDTOService {

    @Override
    public Page<Long> buildPage(Supplier<Page<Long>> supplier) {
        return supplier.get();
    }

    @Override
    public RegionResponseDTO convertRegionToDTO(Region region) {
        RegionResponseDTO responseDTO = null;
        if (region != null) {
            responseDTO = new RegionResponseDTO(region);
        }
        return responseDTO;
    }
}
