package uz.ciasev.ubdd_service.mvd_core.api.court.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourtActionName {
    REVIEW(1),
    UPDATE_CASE(2),
    UPDATE_INFORMATION_STATUS(2),
    SEPARATION(3),
    MOVEMENT(4),
    MERGE(5),
    RESOLUTION(5),
    RETURNING(5),
    EDITING_OF_RESOLUTION(1),
    EDITING_OF_RETURNING(1)
    ;

    private final int acceptOrder;
}
