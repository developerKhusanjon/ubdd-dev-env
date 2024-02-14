package uz.ciasev.ubdd_service.service.court.trans;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.trans.response.court.CourtTransferResponseDTO;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.exception.transfer.DictTransferNotPresent;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransferDictionaryRepository;

import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.COURT_FIELD_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CourtTransferDictionaryServiceImpl implements CourtTransferDictionaryService {

    private final CourtTransferDictionaryRepository courtTransferDictionaryRepository;

    @Override
    public Page<CourtTransferResponseDTO> findAll(Pageable pageable) {
        return courtTransferDictionaryRepository
                .findAll(pageable)
                .map(CourtTransferResponseDTO::new);
    }

    @Override
    public CourtTransfer findByExternalId(Long externalId) {
        return courtTransferDictionaryRepository
                .findByExternalId(externalId)
                .orElseThrow(()-> new CourtValidationException((COURT_FIELD_NOT_FOUND + externalId)));
    }

    @Override
    public CourtTransfer findByRegionAndDistrictIds(Long regionId, Long districtId) {
        return courtTransferDictionaryRepository
                .findByRegionAndDistrictIds(regionId, districtId)
                .orElseThrow(() -> new DictTransferNotPresent(CourtTransfer.class, "regionId", regionId, "districtId", districtId));
    }
}
