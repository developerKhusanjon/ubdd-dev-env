package uz.ciasev.ubdd_service.entity.dict.resolution;

import uz.ciasev.ubdd_service.entity.dict.BackendAlias;

public enum PunishmentTypeAlias implements BackendAlias {
    PENALTY,
    WITHDRAWAL,
    CONFISCATION,
    LICENSE_REVOCATION,
    ARREST,
    DEPORTATION,
    COMMUNITY_WORK,
    MEDICAL_PENALTY;

    @Override
    public long getId() {
        return 0;
    }
}
