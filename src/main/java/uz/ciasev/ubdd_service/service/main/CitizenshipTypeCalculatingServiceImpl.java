package uz.ciasev.ubdd_service.service.main;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.PersonDocument;
import uz.ciasev.ubdd_service.entity.dict.person.*;
import uz.ciasev.ubdd_service.exception.CitizenshipTypeCalculationUnacceptable;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.service.dict.AliasedDictionaryService;

import java.util.EnumMap;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.person.CitizenshipCalculatingModeAlias.*;
import static uz.ciasev.ubdd_service.entity.dict.person.CitizenshipTypeAlias.*;

@Service
@RequiredArgsConstructor
public class CitizenshipTypeCalculatingServiceImpl implements CitizenshipTypeCalculatingService {

    @Getter
    private final CitizenshipTypeAlias unknownAlias = CitizenshipTypeAlias.UNKNOWN;

    private final EnumMap<CitizenshipCalculatingModeAlias, CitizenshipTypeAlias> documentToCitizenshipAliasMap = new EnumMap<>(CitizenshipCalculatingModeAlias.class);
    private final AliasedDictionaryService<CitizenshipType, CitizenshipTypeAlias> service;

    {
        documentToCitizenshipAliasMap.put(FOREVER_UZBEK, UZBEK);
        documentToCitizenshipAliasMap.put(FOREVER_STATELESS, STATELESS);
        documentToCitizenshipAliasMap.put(FOREVER_FOREIGN, FOREIGN);
        documentToCitizenshipAliasMap.put(FOREVER_UNKNOWN, CitizenshipTypeAlias.UNKNOWN);
    }

    @Override
    public CitizenshipType calculate(PersonDocument document) {
        return findByAlias(document.getPersonDocumentType(), document.getGivenAddress());
    }

    private CitizenshipTypeAlias calculateCitizenshipAlias(CitizenshipCalculatingModeAlias calculatingMode, Address givenDocumentAddress) {
        if (calculatingMode.equals(CALCULATION_UNACCEPTABLE))
            throw new CitizenshipTypeCalculationUnacceptable();

        if (calculatingMode.equals(DEPEND_ON_GIVEN_PLACE))
            return calculateCitizenshipAliasByGivenPlace(givenDocumentAddress);

        return Optional
                .ofNullable(documentToCitizenshipAliasMap.get(calculatingMode))
                .orElseThrow(() -> new ImplementationException("Unknown citizenship calculating mode alias in document"));
    }

    private CitizenshipTypeAlias calculateCitizenshipAliasByGivenPlace(Address address) {
        return address.isUzbekistan() ? UZBEK : FOREIGN;
    }

    private CitizenshipType findByAlias(PersonDocumentType personDocumentType, Address givenDocumentAddress) {
        CitizenshipTypeAlias alias = calculateCitizenshipAlias(personDocumentType.getCitizenshipCalculatingMode(), givenDocumentAddress);
        return service.getByAlias(alias);
    }
}
