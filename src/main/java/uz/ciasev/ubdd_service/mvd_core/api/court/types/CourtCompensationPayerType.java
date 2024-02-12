package uz.ciasev.ubdd_service.mvd_core.api.court.types;

import lombok.Getter;

@Getter
public enum CourtCompensationPayerType {
    VIOLATOR(1L),
    EMPLOYER(2L);

    private final Long id;

    CourtCompensationPayerType(Long value) {
        this.id = value;
    }

    public Long getCourtCode() {
        return id;
    }
}
