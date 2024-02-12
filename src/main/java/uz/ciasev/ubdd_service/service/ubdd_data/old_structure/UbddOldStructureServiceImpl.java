package uz.ciasev.ubdd_service.service.ubdd_data.old_structure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolRequestAdditionalDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolUbddDataRequestTransportDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.ProtocolUbddDataRequestUbddDTO;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.UbddInsuranceDataRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddAttorneyLetterDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddDriverLicenseDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDVehicleColorType;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.entity.ubdd_data.UbddDataToProtocolBind;
import uz.ciasev.ubdd_service.entity.ubdd_data.old_structure.ProtocolUbddDataView;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.repository.ubdd_data.ProtocolUbddDataViewRepository;
import uz.ciasev.ubdd_service.repository.ubdd_data.UbddDataToProtocolBindRepository;
import uz.ciasev.ubdd_service.service.main.protocol.ProtocolAdditionalService;
import uz.ciasev.ubdd_service.service.ubdd_data.*;
import uz.ciasev.ubdd_service.utils.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UbddOldStructureServiceImpl implements UbddOldStructureService {

    private final UbddDataToProtocolBindService bindService;
    private final UbddTexPassDataService texPassDataService;
    private final UbddDrivingLicenseDataService drivingLicenseDataService;
    private final UbddInsuranceDataService insuranceDataService;
    private final UbddAttorneyLetterDataService attorneyLetterDataService;

    private final ProtocolAdditionalService additionalService;

    private final ProtocolUbddDataViewRepository protocolUbddDataViewRepository;

    private final UbddDataToProtocolBindRepository bindRepository;

    @Override
    @Transactional
    public void createBindForNewProtocol(User user, Protocol protocol, ProtocolRequestAdditionalDTO additional) {
        if (additional == null) {
            return;
        }

        Long ubddTexPassDataId = saveTexPassData(user, additional);
        if (ubddTexPassDataId == null) {
            return;
        }

        Long ubddDrivingLicenseDataId = saveDrivingLicenseData(user, additional);
        Long ubddInsuranceDataId = saveInsuranceData(user, additional);
        Long ubddAttorneyLetterDataId = saveAttorneyLetterData(user, additional);
        Long vehicleArrestId = saveVehicleArrest(user, protocol, additional);

        OldStructureUbddDataToProtocolBindInternalDTO bind = new OldStructureUbddDataToProtocolBindInternalDTO();
        bind.setUbddTexPassDataId(ubddTexPassDataId);
        bind.setUbddDrivingLicenseDataId(ubddDrivingLicenseDataId);
        bind.setUbddTintingDataId(null);
        bind.setUbddInsuranceDataId(ubddInsuranceDataId);
        bind.setVehicleArrestId(vehicleArrestId);
        bind.setUbddAttorneyLetterDataId(ubddAttorneyLetterDataId);

        bindService.save(user, protocol.getId(), bind);
    }

    @Override
    @Transactional
    public void updateProtocolAdditional(User user, Long protocolId, ProtocolRequestAdditionalDTO requestDTO) {
        Protocol protocol = additionalService.updateProtocolAdditional(user, protocolId, requestDTO);
        Optional<UbddDataToProtocolBind> bindOpt = bindService.findByProtocolId(protocolId);
        if (bindOpt.isEmpty()) {
            createBindForNewProtocol(user, protocol, requestDTO);
            return;
        }

        UbddDataToProtocolBind bind = bindOpt.get();
        if (!bind.isOld()) {
            throw new ValidationException(ErrorCode.OLD_API_FOR_NEW_UBDD_DATA);
        }

        createBindForNewProtocol(user, protocol, requestDTO);

    }

    @Override
    public Optional<ProtocolUbddDataView> findByProtocolId(Long id) {
        return protocolUbddDataViewRepository.findByProtocolId(id);
    }

    private Long saveAttorneyLetterData(User user, ProtocolRequestAdditionalDTO additional) {
        if (additional.getUbdd() == null) {
            return null;
        }

        ProtocolUbddDataRequestUbddDTO request = additional.getUbdd();
        if (StringUtils.isEmptyOrNullString(request.getAttorneySeries()) && StringUtils.isEmptyOrNullString(request.getAttorneyNumber())) {
            return null;
        }

        UbddAttorneyLetterDTO attorneyLetter = buildUbddAttorneyLetterDto(request);

        return attorneyLetterDataService.save(user, attorneyLetter).getId();
    }

    private Long saveVehicleArrest(User user, Protocol protocol, ProtocolRequestAdditionalDTO additional) {
        if (additional.getUbdd() == null) {
            return null;
        }

        ProtocolUbddDataRequestUbddDTO request = additional.getUbdd();
        if (request.getImpound() == null || StringUtils.isEmptyOrNullString(request.getVehicleNumber())) {
            return null;
        }

        String owner = StringUtils.joinIgnoreNull(
                ",",
                Optional.ofNullable(request.getVehicleOwnerInn()).map(s -> String.format("STIR: %s", s)).orElse(null),
                Optional.ofNullable(request.getVehicleOwnerPinpp()).map(s -> String.format("JShShIR: %s", s)).orElse(null)
        );

        Integer arrestId = bindRepository.saveArrest(
                request.getVehicleNumber(),
                request.getImpound().getVehicleArrestPlaceId(),
                protocol.getUserId(),
                protocol.getRegistrationTime(),
                Optional.ofNullable(request.getVehicleBrand()).orElse(""),
                owner,
                Optional.ofNullable(request.getVehicleColor()).orElse("")
        );

        if (-1 == arrestId) {
            throw new ImplementationException(ErrorCode.UBDD_ARREST_NOT_SAVE, "DB procedure return -1");
        }

        return arrestId.longValue();
    }

    private Long saveInsuranceData(User user, ProtocolRequestAdditionalDTO additional) {
        if (additional.getUbdd() == null) {
            return null;
        }

        ProtocolUbddDataRequestUbddDTO request = additional.getUbdd();
        if (StringUtils.isEmptyOrNullString(request.getInsuranceNumber())) {
            return null;
        }

        UbddInsuranceDataRequestDTO insuranceData = buildUbddInsuranceDataDto(request);

        return insuranceDataService.save(user, insuranceData).getId();
    }

    private Long saveDrivingLicenseData(User user, ProtocolRequestAdditionalDTO additional) {
        if (additional.getUbdd() == null) {
            return null;
        }

        ProtocolUbddDataRequestUbddDTO request = additional.getUbdd();
        if (StringUtils.isEmptyOrNullString(request.getDrivingLicenseSeries()) && StringUtils.isEmptyOrNullString(request.getDrivingLicenseNumber())) {
            return null;
        }

        UbddDriverLicenseDTO drivingLicense = buildUbddDriverLicenseDataDto(request);

        return drivingLicenseDataService.save(user, drivingLicense).getId();
    }

    private Long saveTexPassData(User user, ProtocolRequestAdditionalDTO additional) {
        UbddTexPassDTO request = buildUbddTexPassDataDto(additional);
        if (request == null) {
            return null;
        }

        return texPassDataService.saveAndGetDTO(user, request).getId();
    }

    private UbddInsuranceDataRequestDTO buildUbddInsuranceDataDto(ProtocolUbddDataRequestUbddDTO request) {
        UbddInsuranceDataRequestDTO dto = new UbddInsuranceDataRequestDTO();

        dto.setPolicySeries("");
        dto.setPolicyNumber(request.getAttorneySeries());
        dto.setFromDate(LocalDate.EPOCH);
        dto.setToDate(LocalDate.EPOCH);

        return dto;
    }

    private UbddAttorneyLetterDTO buildUbddAttorneyLetterDto(ProtocolUbddDataRequestUbddDTO request) {
        UbddAttorneyLetterDTO dto = new UbddAttorneyLetterDTO();

        dto.setNumber(StringUtils.joinIgnoreNull("", request.getAttorneySeries(), request.getAttorneyNumber()));

        return dto;
    }


    private UbddTexPassDTO buildUbddTexPassDataDto(ProtocolRequestAdditionalDTO request) {
        if (request.getUbdd() != null) {
            return buildUbddTexPassDataDto(request.getUbdd());
        } else if (request.getTransport() != null) {
            return buildUbddTexPassDataDto(request.getTransport());
        }
        return null;
    }

    private UbddTexPassDTO buildUbddTexPassDataDto(ProtocolUbddDataRequestTransportDTO request) {
        UbddTexPassDTO dto = new UbddTexPassDTO();
        dto.setVehicleOwnerType(null);
        dto.setVehicleOwnerInn(request.getVehicleOwnerInn());
        dto.setVehicleOwnerOrganizationName(request.getVehicleOwnerFirstName());
        dto.setVehicleOwnerLastName(request.getVehicleOwnerLastName());
        dto.setVehicleOwnerFirstName(request.getVehicleOwnerFirstName());
        dto.setVehicleOwnerSecondName(request.getVehicleOwnerSecondName());
        dto.setVehicleOwnerBirthDate(request.getVehicleOwnerBirthdate());
        dto.setVehicleOwnerPinpp(request.getVehicleOwnerPinpp());
        dto.setVehicleNumber(request.getVehicleNumber());
        dto.setVehicleColor(request.getVehicleColor());
        dto.setVehicleSubColor(Optional.ofNullable(request.getVehicleColorType()).map(UBDDVehicleColorType::getDefaultName).orElse(null));
        dto.setVehicleBodyType(null);
        dto.setVehicleBrand(request.getVehicleBrand());
        dto.setVehicleModel(request.getVehicleBrand());
        dto.setVehicleEngineSeries(request.getVehicleEngineSeries());
        dto.setVehicleEngineHorsePower(request.getVehicleEngineHorsePower());
        dto.setVehicleRegistrationDate(request.getVehicleRegistrationDate());
        dto.setVehicleChassisSeries(request.getVehicleChassisSeries());
        dto.setTexPassSeries(request.getVehiclePassSeries());
        dto.setTexPassNumber(request.getVehiclePassNumber());
        try {
            dto.setVehicleYear(Integer.valueOf(request.getVehicleYear()));
        } catch (NumberFormatException ignored) {
            dto.setVehicleYear(-1);
        }
        dto.setVehicleId(null);
        dto.setVehicleBodySeries(request.getVehicleBodySeries());
        dto.setTexPassGivenAddress(null);

        return dto;
    }

    private UbddTexPassDTO buildUbddTexPassDataDto(ProtocolUbddDataRequestUbddDTO request) {
        UbddTexPassDTO dto = new UbddTexPassDTO();
        dto.setVehicleOwnerType(request.getVehicleOwner());
        dto.setVehicleOwnerInn(request.getVehicleOwnerInn());
        dto.setVehicleOwnerOrganizationName(null);
        dto.setVehicleOwnerLastName(null);
        dto.setVehicleOwnerFirstName(null);
        dto.setVehicleOwnerSecondName(null);
        dto.setVehicleOwnerBirthDate(null);
        dto.setVehicleOwnerPinpp(request.getVehicleOwnerPinpp());
        dto.setVehicleNumber(request.getVehicleNumber());
        dto.setVehicleColor(request.getVehicleColor());
        dto.setVehicleSubColor(Optional.ofNullable(request.getVehicleColorType()).map(UBDDVehicleColorType::getDefaultName).orElse(null));
        dto.setVehicleBodyType(request.getVehicleBodyType());
        dto.setVehicleBrand(request.getVehicleBrand());
        dto.setVehicleModel(request.getVehicleBrand());
        dto.setVehicleEngineSeries(request.getVehicleEngineSeries());
        dto.setVehicleEngineHorsePower(request.getVehicleEngineHorsePower());
        dto.setVehicleRegistrationDate(request.getVehicleRegistrationDate());
        dto.setVehicleChassisSeries(request.getVehicleChassisSeries());
        dto.setTexPassSeries(request.getVehiclePassSeries());
        dto.setTexPassNumber(request.getVehiclePassNumber());
        dto.setVehicleYear(null);
        dto.setVehicleId(null);
        dto.setVehicleBodySeries(request.getVehicleBodySeries());
        dto.setTexPassGivenAddress(request.getRegistrationAddress());

        return dto;
    }

    private UbddDriverLicenseDTO buildUbddDriverLicenseDataDto(ProtocolUbddDataRequestUbddDTO request) {
        if (request.getDrivingLicenseSeries() == null && request.getDrivingLicenseNumber() == null) {
            return null;
        }

        UbddDriverLicenseDTO dto = new UbddDriverLicenseDTO();

        dto.setVehicleOwnerPinpp(request.getVehicleOwnerPinpp());
        dto.setVehicleOwnerBirthDate(null);
        dto.setVehicleOwnerLastName(null);
        dto.setVehicleOwnerFirstName(null);
        dto.setVehicleOwnerSecondName(null);
        dto.setDrivingLicenseFromDate(null);
        dto.setDrivingLicenseToDate(null);
        dto.setDrivingLicenseIssuedBy(null);
        dto.setDrivingLicenseSerial(request.getDrivingLicenseSeries());
        dto.setDrivingLicenseNumber(request.getDrivingLicenseNumber());
        dto.setCategories(List.of());
        dto.setDrivingLicenseGivenAddress(null);

        return dto;
    }
}
