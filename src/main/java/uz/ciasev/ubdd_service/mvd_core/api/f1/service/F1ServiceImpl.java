package uz.ciasev.ubdd_service.mvd_core.api.f1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.ciasev.ubdd_service.mvd_core.api.ExternalAddressService;
import uz.ciasev.ubdd_service.mvd_core.api.ExternalApiAddress;
import uz.ciasev.ubdd_service.mvd_core.api.f1.F1InvalidResponseException;
import uz.ciasev.ubdd_service.mvd_core.api.f1.F1ServerApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.f1.dto.*;
import uz.ciasev.ubdd_service.config.wrapper.ApiResponseError;
import uz.ciasev.ubdd_service.dto.internal.response.F1DocumentListDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.service.dict.person.GenderDictionaryService;
import uz.ciasev.ubdd_service.service.dict.person.NationalityDictionaryService;

import java.math.BigInteger;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class F1ServiceImpl implements F1Service {

    private final ObjectMapper mapper;
    private final String gcpPersonInfoUrl;
    private final RestTemplate restTemplate;
    private final GenderDictionaryService genderService;
    private final NationalityDictionaryService nationalityService;
    private final DictionaryService<PersonDocumentType> documentTypeService;
    private final ExternalAddressService externalAddressService;

    @Autowired
    public F1ServiceImpl(@Value("${gcp-api.host}") String host,
                         @Value("${gcp-api.photo-url}") String gcpPhotoUrl,
                         ObjectMapper mapper, @Qualifier("f1RestTemplate") RestTemplate restTemplate,
                         GenderDictionaryService genderService,
                         NationalityDictionaryService nationalityService,
                         DictionaryService<PersonDocumentType> documentTypeService, ExternalAddressService externalAddressService) {
        this.gcpPersonInfoUrl = host + "/v0/api/gcp";
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.genderService = genderService;
        this.nationalityService = nationalityService;
        this.documentTypeService = documentTypeService;
        this.externalAddressService = externalAddressService;
    }

    @Override
    public F1Document findByPinpp(String pinpp) {

        GcpPersonInfo personInfo = findGcpInfoByPinpp(pinpp);

        if (personInfo == null) {
            throw new NotFoundException(ErrorCode.GCP_PERSON_INFO_NOT_FOUND_BY_PINPP);
        }

        F1Document f1Document;

        Gender gender = genderService.getById(personInfo.getGenderId());
        Nationality nationality = nationalityService.getById(personInfo.getNationalityId());
        PersonDocumentType documentType = documentTypeService.getById(personInfo.getPersonDocumentTypeId());
        Address birthAddress = externalAddressService.buildAddress(personInfo.getBirthAddress());
        Address givenAddress = externalAddressService.buildAddress(personInfo.getGivenAddress());
        Address residentAddress = null;
        if (personInfo.getResidentAddress() != null) {
            residentAddress = externalAddressService.buildAddress(personInfo.getResidentAddress());
        }

        f1Document = new F1Document(personInfo);
        f1Document.setGender(gender);
        f1Document.setNationality(nationality);
        f1Document.setPersonDocumentType(documentType);
        f1Document.setBirthAddress(birthAddress);
        f1Document.setGivenAddress(givenAddress);
        f1Document.setResidentAddress(residentAddress);

        return f1Document;
    }

    @Override
    public GcpPersonInfo findGcpInfoByPinpp(String pinpp) {

        if (pinpp == null || pinpp.length() != 14) {
            throw new ValidationException(ErrorCode.PINPP_INVALID);
        }

        GcpObjectResponse response = sendRequest(
                UriComponentsBuilder.fromHttpUrl(gcpPersonInfoUrl + "/persons/" + pinpp).build().encode().toUri(),
                GcpObjectResponse.class
        ).getBody();

        return Optional.ofNullable(response).map(GcpObjectResponse::getData).map(GcpObjectData::getObject).orElse(null);
    }

    @Override
    public byte[] getPhotoById(String id, String photoType) {
        if (photoType.length() > 30)
            throw new F1PhotoTypeLengthException();
        byte[] photo;
        try {
            photo = restTemplate.getForObject(
                    gcpPersonInfoUrl + "/persons/photo/" + id + "/type/" + photoType,
                    byte[].class);

        } catch (RestClientException e) {
            photo = null;
        }
        return photo;
    }


    private <T> ResponseEntity<T> sendRequest(URI uri, Class<T> responseType) {
        long start = System.currentTimeMillis();
        log.debug("GCP REQUEST START {}: {} (QWAS)", LocalDateTime.now(), uri);

        try {

            return restTemplate.getForEntity(uri, responseType);

        } catch (HttpClientErrorException e) {
            throw new ValidationException(extractErrorCode(e));
        } catch (RestClientResponseException e) {
            throw new F1ServerApplicationException(extractErrorCode(e), e.getResponseBodyAsString());
        } catch (RestClientException e) {
            throw new F1ServerApplicationException(ErrorCode.F1_SERVICE_CONNECTION_ERROR, e.getMessage());
        } finally {
            log.debug("GCP REQUEST END {}: {}, REQUEST-TIME:{} (QWAS)", LocalDateTime.now(), uri, System.currentTimeMillis() - start);
        }
    }


    private String extractErrorCode(RestClientResponseException e) {
        ApiResponseError error = convertStringToObject(e);
        return Optional.ofNullable(error.getData())
                .map(ApiResponseError.Body::getCode)
                .orElse("F1_SERVICE_ERROR");
    }

    private ApiResponseError convertStringToObject(RestClientResponseException e) {
        try {
            return mapper.readValue(e.getResponseBodyAsString(), ApiResponseError.class);
        } catch (JsonProcessingException exception) {
            throw new F1InvalidResponseException(exception.getMessage());
        }
    }

}
