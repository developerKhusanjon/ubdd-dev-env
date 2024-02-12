package uz.ciasev.ubdd_service.service.court.trans;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.trans.response.court.CourtTransferResponseDTO;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;

public interface CourtTransferDictionaryService {

    CourtTransfer findByExternalId(Long externalId);

    Page<CourtTransferResponseDTO> findAll(Pageable pageable);

    CourtTransfer findByRegionAndDistrictIds(Long regionId, Long districtId);
}
