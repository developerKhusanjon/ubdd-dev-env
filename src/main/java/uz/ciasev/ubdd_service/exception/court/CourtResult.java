package uz.ciasev.ubdd_service.exception.court;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourtResult {
    SUCCESSFULLY(1L),
    VALIDATION_ERROR(3L),
    SINGLE_TREAD_INTERRUPTED_ERROR(98L),
    SINGLE_TREAD_EXECUTION_ERROR(99L);

    private Long value;

}
