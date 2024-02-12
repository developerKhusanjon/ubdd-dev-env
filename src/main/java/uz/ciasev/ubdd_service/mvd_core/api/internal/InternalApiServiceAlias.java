package uz.ciasev.ubdd_service.mvd_core.api.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InternalApiServiceAlias {
    VIOLATION_EVENT("VIOL_EVNT"),
    TECH_PASS("UBDD"),
    CUSTOMS_VEHICLE("VEH_CUST");

    private final String codePrefix;
}
