package uz.ciasev.ubdd_service.service.violation_event.build;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.manzil.ManzilService;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonDocumentRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.PersonRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorDetailRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.implementation.LogicalException;
import uz.ciasev.ubdd_service.service.UnknownValueService;
import uz.ciasev.ubdd_service.service.address.AddressReferenceBookService;
import uz.ciasev.ubdd_service.service.address.AddressValidationService;
import uz.ciasev.ubdd_service.service.dict.*;
import uz.ciasev.ubdd_service.service.dict.person.*;

import java.util.EnumMap;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ViolationEventViolatorBuildingServiceImpl implements ViolationEventViolatorBuildingService {

    private final ManzilService manzilService;
    private final RegionDictionaryService regionService;
    private final DistrictDictionaryService districtService;
    private final CountryDictionaryService countryService;
    private final OccupationDictionaryService occupationService;
    private final NationalityDictionaryService nationalityService;
    private final AddressValidationService addressValidationService;
    private final CitizenshipTypeDictionaryService citizenshipTypeService;
    private final GenderDictionaryService genderService;
    private final UnknownValueByIdDictionaryService<PersonDocumentType> personDocumentTypeService;
    private final AddressReferenceBookService addressReferenceBookService;
    private final UnknownValueService unknownValueService;

    private final EnumMap<ExternalSystemAlias, Function<UbddTexPassDTOI, ViolatorCreateRequestDTO>> builderMap = new EnumMap<>(ExternalSystemAlias.class);

    {
        builderMap.put(ExternalSystemAlias.TECH_PASS, this::buildForUzbekTechPass);
        builderMap.put(ExternalSystemAlias.CUSTOMS_EVENT, this::buildForCustomsTechPass);
    }


    @Override
    public ViolatorCreateRequestDTO build(UbddTexPassDTOI texPassData) {
        Function<UbddTexPassDTOI, ViolatorCreateRequestDTO> builder = builderMap.get(texPassData.getExternalSystem());
        if (builder == null) {
            throw new LogicalException("Unexpected vehicle data source system for building violator for violation event");
        }

        return builder.apply(texPassData);
    }

    private ViolatorCreateRequestDTO buildForUzbekTechPass(UbddTexPassDTOI texPassData) {

        ViolatorCreateRequestDTO requestDTO = new ViolatorCreateRequestDTO();

        if (texPassData.isVehicleOwnerPinppEmpty()) {
            setPersonWithoutPinpp(texPassData, requestDTO);
        } else {
            setPersonWithPinpp(texPassData, requestDTO);
        }

        requestDTO.setViolatorDetail(buildViolatorDetailRequestDTO(texPassData));

        AddressRequestDTO address = getActualAddressForUzbekTechPass(texPassData);
        addressValidationService.validateLocalAddress(address);
        requestDTO.setActualAddress(address);
        requestDTO.setPostAddress(address);
        requestDTO.setMobile(unknownValueService.getMobile());

        return requestDTO;
    }

    private ViolatorCreateRequestDTO buildForCustomsTechPass(UbddTexPassDTOI texPassData) {

        ViolatorCreateRequestDTO requestDTO = new ViolatorCreateRequestDTO();

        setPersonWithoutPinpp(texPassData, requestDTO);

        requestDTO.setViolatorDetail(buildViolatorDetailRequestDTO(texPassData));

        AddressRequestDTO address = new AddressRequestDTO(addressReferenceBookService.getAddressReferenceBookByPlace(AddressReferenceBookService.Place.CUSTOMS));
        addressValidationService.validateLocalAddress(address);
        requestDTO.setActualAddress(address);
        requestDTO.setPostAddress(address);
        requestDTO.setMobile(unknownValueService.getMobile());

        return requestDTO;
    }

    private void setPersonWithPinpp(UbddTexPassDTOI texPassData, ViolatorCreateRequestDTO requestDTO) {
        requestDTO.setPinpp(texPassData.getVehicleOwnerPinpp());
    }

    private void setPersonWithoutPinpp(UbddTexPassDTOI texPassData, ViolatorCreateRequestDTO requestDTO) {
        PersonRequestDTO person = new PersonRequestDTO();

        person.setFirstNameLat(Optional.ofNullable(texPassData.getVehicleOwnerFirstName()).filter(Strings::isNotBlank).orElseThrow(() -> new ValidationException(ErrorCode.VEHICLE_OWNER_FIRST_NAME_LAT_REQUIRED)));
        person.setSecondNameLat(Optional.ofNullable(texPassData.getVehicleOwnerSecondName()).filter(Strings::isNotBlank).orElseThrow(() -> new ValidationException(ErrorCode.VEHICLE_OWNER_SECOND_NAME_LAT_REQUIRED)));
        person.setBirthDate(Optional.ofNullable(texPassData.getVehicleOwnerBirthDate()).orElseThrow(() -> new ValidationException(ErrorCode.VEHICLE_OWNER_BIRTH_DATE_REQUIRED)));

        person.setLastNameLat(Optional.ofNullable(texPassData.getVehicleOwnerLastName()).orElse(""));
        person.setLastNameKir(Optional.ofNullable(texPassData.getVehicleOwnerLastNameKir()).orElse(""));
        person.setFirstNameKir(Optional.ofNullable(texPassData.getVehicleOwnerFirstNameKir()).orElseGet(texPassData::getVehicleOwnerFirstName));
        person.setSecondNameKir(Optional.ofNullable(texPassData.getVehicleOwnerSecondNameKir()).orElseGet(texPassData::getVehicleOwnerSecondName));
        person.setCitizenshipType(Optional.ofNullable(texPassData.getVehicleOwnerCitizenshipType()).orElseGet(citizenshipTypeService::getUnknown));
        person.setGender(Optional.ofNullable(texPassData.getVehicleOwnerGender()).orElseGet(genderService::getUnknown));
        person.setNationality(nationalityService.getUnknown());

        person.setBirthAddress(buildAddressRequestDTO(texPassData.getVehicleOwnerBirthAddress()));


        PersonDocumentRequestDTO document = new PersonDocumentRequestDTO();
        document.setSeries(Optional.ofNullable(texPassData.getVehicleOwnerDocumentSeries()).orElse(unknownValueService.getDocumentSeries()));
        document.setNumber(Optional.ofNullable(texPassData.getVehicleOwnerDocumentNumber()).orElse(unknownValueService.getDocumentNumber()));
        document.setPersonDocumentType(Optional.ofNullable(texPassData.getVehicleOwnerDocumentType()).orElseGet(personDocumentTypeService::getUnknown));
        document.setGivenDate(Optional.ofNullable(texPassData.getVehicleOwnerDocumentGivenDate()).orElse(unknownValueService.getDocumentDate()));
        document.setExpireDate(Optional.ofNullable(texPassData.getVehicleOwnerDocumentExpireDate()).orElse(unknownValueService.getDocumentDate()));
        document.setDocumentGivenAddress(buildAddressRequestDTO(texPassData.getVehicleOwnerDocumentGivenAddress()));

        requestDTO.setPerson(person);
        requestDTO.setDocument(document);
    }

    private AddressRequestDTO getActualAddressForUzbekTechPass(UbddTexPassDTOI texPassData) {
        if (!texPassData.isVehicleOwnerPinppEmpty()) {
            Optional<Address> manzilAddressOpt = manzilService.findAddressByPinpp(texPassData.getVehicleOwnerPinpp());
            if (manzilAddressOpt.isPresent()) {
                return buildAddressRequestDTO(manzilAddressOpt.get());
            }
        }

        AddressRequestDTO address = texPassData.getVehicleOwnerAddress();
        if (address == null) {
            throw new ValidationException(ErrorCode.TECH_PASS_OWNER_ADDRESS_IS_EMPTY);
        }

        return buildAddressRequestDTO(address);
    }


    private AddressRequestDTO buildAddressRequestDTO(AddressRequestDTO address) {
        if (address == null) {
            return unknownValueService.getAddress();
        }

        AddressRequestDTO request = new AddressRequestDTO();
        request.setAddress(address.getAddress());
        request.setDistrict(address.getDistrict());
        request.setRegion(address.getRegion());
        request.setCountry(address.getCountry());
        return request;
    }


    private AddressRequestDTO buildAddressRequestDTO(Address address) {
        if (address == null) {
            return unknownValueService.getAddress();
        }

        AddressRequestDTO request = new AddressRequestDTO();
        request.setAddress(address.getAddress());
        request.setDistrict(districtService.getById(address.getDistrictId()));
        request.setRegion(regionService.getById(address.getRegionId()));
        request.setCountry(countryService.getById(address.getCountryId()));
        return request;
    }


    private ViolatorDetailRequestDTO buildViolatorDetailRequestDTO(UbddTexPassDTOI texPassData) {
        ViolatorDetailRequestDTO request = new ViolatorDetailRequestDTO();
        request.setOccupation(occupationService.getUnknown());
        request.setEmploymentPlace(unknownValueService.getTextValue());
        request.setEmploymentPosition(unknownValueService.getTextValue());
        request.setIntoxicationType(null);
        request.setAdditionally(texPassData.getOwnerInfo());
        request.setSignature(null);
        return request;
    }

}
