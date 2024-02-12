package uz.ciasev.ubdd_service.service.court.trans;

import uz.ciasev.ubdd_service.entity.dict.person.Nationality;

import java.util.Optional;

public interface CourtTransferNationalityService {

    Long getExternalId(Nationality nationality);

    Nationality getByExternalId(Long externalId);

    Optional<Nationality> findByExternalId(Long externalId);
}
