package uz.ciasev.ubdd_service.service.court.material;

import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialRejectBase;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

import java.util.Optional;


public interface CourtMaterialFieldsService {

    CourtMaterialFields move(CourtMaterialFields movedFields, Long toClimeId, Region courtRegion, District courtDistrict);

    CourtMaterialFields review(CourtMaterialFields reviewedCourtFields, Long toClimeId);

    CourtMaterialFields returnMaterial(CourtMaterialFields courtFields, CourtReturnReason courtReturnReason);

    CourtMaterialFields granted(CourtMaterialFields courtFields);

    CourtMaterialFields reject(CourtMaterialFields courtFields, CourtMaterialRejectBase rejectBase);

    CourtMaterialFields grantedWithNewResolution(CourtMaterialFields courtFields, Resolution resolution);

    CourtMaterialFields getCurrent(CourtMaterial courtMaterial);

    Optional<CourtMaterialFields> getCurrentOpt(CourtMaterial courtMaterial);

    CourtMaterialFields open(CourtMaterial courtMaterial, CourtMaterialFieldsRegistrationRequest registration);

    CourtMaterialFields update(CourtMaterialFields courtFields, CourtMaterialFieldsRequest registration);

    CourtMaterialFields updateInformationStatus(CourtMaterialFields courtFields, CourtStatus status);

    Optional<CourtMaterialFields> findByClaimId(Long claimId);
}
