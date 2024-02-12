package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.resolution.Resolution;

public class MibNoAccessOnResolutionException extends ForbiddenException {

    public MibNoAccessOnResolutionException(Resolution resolution) {
        super(ErrorCode.NO_ACCESS_ON_OBJECT,
                String.format(
                        "Mib system has no access on case in organ=%s department=%s region=%s district=%s",
                        resolution.getOrganId(),
                        resolution.getDepartmentId(),
                        resolution.getRegionId(),
                        resolution.getDistrictId()
                ));
    }
}
