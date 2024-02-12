package uz.ciasev.ubdd_service.service.court.trans;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.exception.CourtTransferError;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransNationality;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.exception.transfer.DictTransferNotPresent;
import uz.ciasev.ubdd_service.repository.court.trans.CourtTransNationalityRepository;
import uz.ciasev.ubdd_service.service.dict.person.NationalityDictionaryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourtTransferNationalityServiceImpl implements CourtTransferNationalityService {

    private final CourtTransNationalityRepository courtNationalityRepository;
    private final NationalityDictionaryService nationalityService;

    @Override
    public Long getExternalId(Nationality nationality) {
        return courtNationalityRepository.findByInternalId(nationality.getId())
                .orElseThrow(() -> new DictTransferNotPresent(CourtTransNationality.class, nationality))
                .getExternalId();
    }

    @Override
    public Nationality getByExternalId(Long externalId) {
        Long internalId = getInternalId(externalId);
        return nationalityService.getById(internalId);
    }

    @Override
    public Optional<Nationality> findByExternalId(Long externalId) {
        Long internalId = getInternalId(externalId);
        return nationalityService.findById(internalId);
    }

    private Long getInternalId(Long externalId) {
        return courtNationalityRepository.findByExternalId(externalId)
                .map(CourtTransNationality::getInternalId)
                .orElseThrow(() -> new CourtTransferError(CourtTransNationality.class, externalId));
    }
}
