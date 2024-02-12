package uz.ciasev.ubdd_service.service.main;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipTypeAlias;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentTypeAlias;
import uz.ciasev.ubdd_service.exception.CitizenshipTypeCalculationUnacceptable;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;

import java.util.EnumMap;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.person.CitizenshipTypeAlias.*;
import static uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentTypeAlias.*;

//@Service
@RequiredArgsConstructor
public class CitizenshipTypeCalculatingServiceByDocTypeImpl implements CitizenshipTypeCalculatingService {

    @Getter
    private final CitizenshipTypeAlias unknownAlias = CitizenshipTypeAlias.UNKNOWN;

    private final EnumMap<PersonDocumentTypeAlias, CitizenshipTypeAlias> documentToCitizenshipAliasMap = new EnumMap<>(PersonDocumentTypeAlias.class);
    private final AliasedDictionaryService<CitizenshipType, CitizenshipTypeAlias> service;

    {
        documentToCitizenshipAliasMap.put(PASSPORT, UZBEK);
        documentToCitizenshipAliasMap.put(ID_CARD, UZBEK);
        documentToCitizenshipAliasMap.put(MILITARY_ID, UZBEK);
        documentToCitizenshipAliasMap.put(CHILDREN_ID_CARD, UZBEK);
        documentToCitizenshipAliasMap.put(DRIVER_LICENSE, UZBEK);
        documentToCitizenshipAliasMap.put(AUTOMOBILE_PASSPORT, UZBEK);
        documentToCitizenshipAliasMap.put(STATELESS_PASSPORT, STATELESS);
        documentToCitizenshipAliasMap.put(STATELESS_ID_CARD, STATELESS);
        documentToCitizenshipAliasMap.put(STATELESS_RESIDENCE_PERMIT, STATELESS);
        documentToCitizenshipAliasMap.put(DIPLOMATIC_PASSPORT, FOREIGN);
        documentToCitizenshipAliasMap.put(DIPLOMATIC_ID_CARD, FOREIGN);
        documentToCitizenshipAliasMap.put(RESIDENCE_PERMIT, FOREIGN);
        documentToCitizenshipAliasMap.put(PersonDocumentTypeAlias.UNKNOWN, CitizenshipTypeAlias.UNKNOWN);
    }

    @Override
    public CitizenshipType calculate(PersonDocument document) {
        return findByAlias(document.getPersonDocumentType(), document.getGivenAddress());
    }

    private CitizenshipTypeAlias calculateCitizenshipAlias(PersonDocumentType personDocumentType, Address givenDocumentAddress) {
        if (personDocumentType.getAlias().equals(OTHER))
            throw new CitizenshipTypeCalculationUnacceptable();

        return Optional
                .ofNullable(documentToCitizenshipAliasMap.get(personDocumentType.getAlias()))
                .orElseGet(() -> calculateCitizenshipAliasByGivenPlace(givenDocumentAddress));
    }

    private CitizenshipTypeAlias calculateCitizenshipAliasByGivenPlace(Address address) {
        return address.isUzbekistan() ? UZBEK : FOREIGN;
    }

    private CitizenshipType findByAlias(PersonDocumentType personDocumentType, Address givenDocumentAddress) {
        CitizenshipTypeAlias alias = calculateCitizenshipAlias(personDocumentType, givenDocumentAddress);
        return service.getByAlias(alias);
    }
}
