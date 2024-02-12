package uz.ciasev.ubdd_service.mvd_core.api.court.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine.*;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.victim.VictimDetailRequestDTO;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransGeography;
import uz.ciasev.ubdd_service.entity.dict.person.*;
import uz.ciasev.ubdd_service.service.UnknownValueService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransferNationalityService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransGeographyService;
import uz.ciasev.ubdd_service.service.dict.UnknownValueByIdDictionaryService;
import uz.ciasev.ubdd_service.service.dict.person.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NineCourtRequestMapper implements CourtRequestMapper<CourtVictimRequestExternalDTO, CourtVictimRequestDTO> {

    private final CourtTransGeographyService courtTransGeographyService;
    private final CourtTransferNationalityService courtNationalityService;
    private final NationalityDictionaryService nationalityService;
    private final OccupationDictionaryService occupationService;
    private final UnknownValueService unknownValueService;
    private final CitizenshipTypeDictionaryService citizenshipTypeService;
    private final GenderDictionaryService genderService;
    private final IntoxicationTypeDictionaryService intoxicationTypeService;
    private final UnknownValueByIdDictionaryService<PersonDocumentType> personDocumentTypeService;


    @Override
    public CourtVictimRequestDTO map(CourtVictimRequestExternalDTO requestDTO) {
        return buildInternalDTO(requestDTO);
    }

    public CourtVictimRequestDTO buildInternalDTO(CourtVictimRequestExternalDTO dto) {
        CourtVictimRequestDTO rsl = new CourtVictimRequestDTO();

        String pinpp = Optional.ofNullable(dto.getPinpp()).filter(s -> !s.isBlank()).orElse(null);

        rsl.setCaseId(dto.getCaseId());
        rsl.setClaimId(dto.getClaimId());
        if (pinpp != null) {
            rsl.setPinpp(pinpp);
        } else {
            rsl.setPerson(Optional.ofNullable(dto.getPerson()).map(this::buildInternalDTO).orElse(null));
            rsl.setDocument(Optional.ofNullable(dto.getDocument()).map(this::buildInternalDTO).orElse(null));
        }
        rsl.setActualAddress(buildInternalDTO(dto.getActualAddress()));
        rsl.setPostAddress(buildInternalDTO(dto.getPostAddress()));
        rsl.setVictimDetail(buildInternalDTO(dto.getVictimDetail()));

        rsl.setMobile(Optional.ofNullable(dto.getMobile()).orElse(unknownValueService.getMobile()));
        rsl.setLandline(dto.getLandline());
        rsl.setInn(dto.getInn());

        return rsl;
    }


    public PersonRequestDTO buildInternalDTO(PersonRequestExternalDTO dto) {
        PersonRequestDTO rsl = new PersonRequestDTO();

        rsl.setFirstNameKir(Optional.ofNullable(dto.getFirstNameKir()).orElseGet(dto::getFirstNameLat));
        rsl.setSecondNameKir(Optional.ofNullable(dto.getSecondNameKir()).orElseGet(dto::getSecondNameLat));
        rsl.setLastNameKir(Optional.ofNullable(dto.getLastNameKir()).orElseGet(unknownValueService::getTextValue));
        rsl.setFirstNameLat(dto.getFirstNameLat());
        rsl.setSecondNameLat(Optional.ofNullable(dto.getSecondNameLat()).orElseGet(unknownValueService::getTextValue));
        rsl.setLastNameLat(dto.getLastNameLat());
        rsl.setBirthDate(dto.getBirthDate());
        rsl.setBirthAddress(buildInternalDTO(dto.getBirthAddress()));
        rsl.setCitizenshipType(getCitizenshipType(dto.getCitizenshipTypeId()));
        rsl.setGender(getGender(dto.getGenderId()));
        rsl.setNationality(getNationality(dto.getNationalityId()));

        return rsl;
    }

    public PersonDocumentRequestDTO buildInternalDTO(PersonDocumentRequestExternalDTO dto) {

        PersonDocumentRequestDTO rsl = new PersonDocumentRequestDTO();

        rsl.setSeries(Optional.ofNullable(dto.getSeries()).orElseGet(unknownValueService::getDocumentSeries));
        rsl.setNumber(Optional.ofNullable(dto.getNumber()).orElseGet(unknownValueService::getDocumentNumber));
        rsl.setPersonDocumentType(getPersonDocumentType(dto.getPersonDocumentTypeId()));
        rsl.setDocumentGivenAddress(buildInternalDTO(dto.getGivenAddressDTO()));
        rsl.setGivenDate(Optional.ofNullable(dto.getGivenDate()).orElseGet(unknownValueService::getDocumentDate));
        rsl.setExpireDate(Optional.ofNullable(dto.getExpireDate()).orElseGet(unknownValueService::getDocumentDate));

        rsl.getPersonDocumentType().setIsActive();
        return rsl;
    }

    public VictimDetailRequestDTO buildInternalDTO(VictimDetailRequestExternalDTO dto) {
        VictimDetailRequestDTO rsl = new VictimDetailRequestDTO();

        Long occupationId = Optional.ofNullable(dto).map(VictimDetailRequestExternalDTO::getOccupationId).orElse(null);
        Long intoxicationTypeId = Optional.ofNullable(dto).map(VictimDetailRequestExternalDTO::getIntoxicationTypeId).orElse(null);

        rsl.setOccupation(getOccupation(occupationId));
        rsl.setIntoxicationType(getIntoxicationType(intoxicationTypeId));
        rsl.setEmploymentPlace(Optional.ofNullable(dto).map(VictimDetailRequestExternalDTO::getEmploymentPlace).orElseGet(unknownValueService::getTextValue));
        rsl.setEmploymentPosition(Optional.ofNullable(dto).map(VictimDetailRequestExternalDTO::getEmploymentPosition).orElseGet(unknownValueService::getTextValue));
        rsl.setSignature(Optional.ofNullable(dto).map(VictimDetailRequestExternalDTO::getSignature).orElse(null));

        return rsl;
    }

    public AddressRequestDTO buildInternalDTO(AddressRequestExternalDTO dto) {

        if (dto == null) {
            return unknownValueService.getAddress();
        }

        if (dto.getCountryId() == null && dto.getRegionId() == null && dto.getDistrictId() == null) {
            return unknownValueService.getAddress();
        }


        AddressRequestDTO rsl = new AddressRequestDTO();

        CourtTransGeography transAddress = courtTransGeographyService.getByExternal(dto.getCountryId(), dto.getRegionId(), dto.getDistrictId());
        rsl.setCountry(transAddress.getCountry());
        rsl.setRegion(transAddress.getRegion());
        rsl.setDistrict(transAddress.getDistrict());
        rsl.setAddress(dto.getAddress());

        return rsl;

    }

    private Nationality getNationality(Long externalId) {
        if (externalId == null) {
            return nationalityService.getUnknown();
        }
        return courtNationalityService.getByExternalId(externalId);
    }

    private Occupation getOccupation(Long externalId) {
        if (externalId == null) {
            return occupationService.getUnknown();
        }
        return occupationService.getOrCreateNew(externalId);
    }

    private CitizenshipType getCitizenshipType(Long externalId) {
        if (externalId == null) {
            return citizenshipTypeService.getUnknown();
        }
        return citizenshipTypeService.getById(externalId);
    }

    private Gender getGender(Long externalId) {
        if (externalId == null) {
            return genderService.getUnknown();
        }
        return genderService.getById(externalId);
    }

    private PersonDocumentType getPersonDocumentType(Long externalId) {
        if (externalId == null) {
            return personDocumentTypeService.getUnknown();
        }
        return personDocumentTypeService.getById(externalId);
    }

    private IntoxicationType getIntoxicationType(Long externalId) {
        if (externalId == null) {
            return null;
        }
        return intoxicationTypeService.getById(externalId);
    }
}
