package uz.ciasev.ubdd_service.service.violation_event.decision;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.violation_event.dto.ViolationEventApiDTO;
import uz.ciasev.ubdd_service.dto.internal.ubdd_data.UbddTexPassDTOI;
import uz.ciasev.ubdd_service.entity.violation_event.ViolationEventResult;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.violation_event.ViolationEventAlreadyResolvedException;
import uz.ciasev.ubdd_service.service.violation_event.ViolationEventResultService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViolationEventResolvingValidationServiceImpl implements ViolationEventResolvingValidationService {

    private final ViolationEventResultService resultService;


    @Override
    public void checkNotResolved(Long eventId) {
        Optional<ViolationEventResult> existResult = resultService.findActiveByViolationEventId(eventId);
        if (existResult.isPresent()) {
            throw new ViolationEventAlreadyResolvedException(existResult.get());
        }
    }

    @Override
    public void checkCompletenessOfDataToMakeDecision(ViolationEventApiDTO event) {
        if (event.getVehicleNumberPhotoPath() == null || event.getVehicleNumberPhotoPath().isBlank()) {
            throw new ValidationException(ErrorCode.EVENT_VEHICLE_NUMBER_PHOTO_IS_EMPTY);
        }

        boolean isFarViewPhotoEmpty = event.getFarViewPhotoPath() == null || event.getFarViewPhotoPath().isBlank();
        boolean isNearViewPhotoEmpty = event.getNearViewPhotoPath() == null || event.getNearViewPhotoPath().isBlank();
        if (isFarViewPhotoEmpty && isNearViewPhotoEmpty) {
            throw new ValidationException(ErrorCode.EVENT_VEHICLE_VIEW_PHOTO_IS_EMPTY);
        }

        if (event.getArticlePart() == null) {
            throw new ValidationException(ErrorCode.EVENT_ARTICLE_PART_ID_IS_EMPTY);
        }

        if (event.getViolationType() == null) {
            throw new ValidationException(ErrorCode.EVENT_VIOLATION_TYPE_ID_IS_EMPTY);
        }

        if (event.getViolationTime() == null) {
            throw new ValidationException(ErrorCode.EVENT_VIOLATION_TIME_IS_EMPTY);
        }

        if (event.getRegion() == null) {
            throw new ValidationException(ErrorCode.EVENT_REGION_ID_IS_EMPTY);
        }

        if (event.getDistrict() == null) {
            throw new ValidationException(ErrorCode.EVENT_DISTRICT_ID_IS_EMPTY);
        }

    }

    @Override
    public void checkCompletenessOfDataToMakeDecision(UbddTexPassDTOI texPassData) {
        if (texPassData.getVehicleOwnerType().getIsJuridic()) {

            boolean isOrganizationNameEmpty = texPassData.getVehicleOwnerOrganizationName() == null || texPassData.getVehicleOwnerOrganizationName().isBlank();
            boolean isOrganizationInnEmpty = texPassData.getVehicleOwnerInn() == null || texPassData.getVehicleOwnerInn().isBlank();

            if (isOrganizationNameEmpty && isOrganizationInnEmpty) {
                throw new ValidationException(ErrorCode.JURIDIC_TECH_PASS_INN_AND_ORGANIZATION_NAME_IS_EMPTY);
            }

        } else {

            if (texPassData.getVehicleOwnerPinpp() != null && !texPassData.getVehicleOwnerPinpp().isBlank()) {
                return;
            }

            if (texPassData.getVehicleOwnerFirstName() == null || texPassData.getVehicleOwnerFirstName().isBlank()) {
                throw new ValidationException(ErrorCode.VEHICLE_OWNER_FIRST_NAME_LAT_REQUIRED);
            }

            if (texPassData.getVehicleOwnerSecondName() == null || texPassData.getVehicleOwnerSecondName().isBlank()) {
                throw new ValidationException(ErrorCode.VEHICLE_OWNER_SECOND_NAME_LAT_REQUIRED);
            }

            if (texPassData.getVehicleOwnerBirthDate() == null) {
                throw new ValidationException(ErrorCode.VEHICLE_OWNER_BIRTH_DATE_REQUIRED);
            }
        }
    }

}
