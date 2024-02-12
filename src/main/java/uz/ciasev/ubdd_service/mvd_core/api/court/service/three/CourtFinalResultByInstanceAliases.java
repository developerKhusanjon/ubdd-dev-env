package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import uz.ciasev.ubdd_service.exception.court.CourtValidationException;

import static uz.ciasev.ubdd_service.exception.court.CourtValidationException.COURT_FINAL_RESULT_NOT_SUPPORTED;

public enum CourtFinalResultByInstanceAliases {

    FR_I_PUNISHMENT(1L),
    FR_I_TERMINATION(2L),
    FR_I_CASE_RETURNING(4L),
    FR_I_SENT_TO_OTHER_COURT(113L),
    FR_I_RE_QUALIFICATION(115L),

    FR_II_FULLY_SATISFIED(215L),
    FR_II_PARTIALLY_SATISFIED(216L),
    FR_II_REJECTED(217L),
    FR_II_NOT_RELEVANT(218L),

    FR_III_FULLY_SATISFIED(343L),
    FR_III_PARTIALLY_SATISFIED(344L),
    FR_III_REJECTED(345L),
    FR_III_NOT_RELEVANT(346L),

    FR_IV_FULLY_SATISFIED(401L),
    FR_IV_PARTIALLY_SATISFIED(402L),
    FR_IV_REJECTED(403L),
    FR_IV_NOT_RELEVANT(404L);

    private final Long val;

    CourtFinalResultByInstanceAliases(Long val) {
        this.val = val;
    }

    public static CourtFinalResultByInstanceAliases getNameByValue(Long val) {
        for (CourtFinalResultByInstanceAliases value : CourtFinalResultByInstanceAliases.values()) {
            if (value.val.equals(val))
                return value;
        }

        throw new CourtValidationException(COURT_FINAL_RESULT_NOT_SUPPORTED);
    }
}
