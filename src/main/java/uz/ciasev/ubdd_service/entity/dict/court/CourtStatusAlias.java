package uz.ciasev.ubdd_service.entity.dict.court;

import java.util.List;

public enum CourtStatusAlias {
    OTHER(1L),

    REGISTERED_IN_COURT(2L),
    CANCELLED(20L),

    JUDGE_APPOINTED(3L),
    PROCESS_REVIEW(11L),
    PAUSED(8L),

    RESOLVED(13L),
    MERGED(18L),
    RETURNED(17L),

    PASSED_TO_ARCHIVE(16L),
    RE_REGISTERED_IN_COURT(19L),
    DECLINED_PASSED_TO_ARCHIVE(21L),
    RETURNED_FROM_ARCHIVE(22L),

    VALIDATION(666L),
    SENT_TO_COURT(555L);

    private final Long value;

    CourtStatusAlias(Long value) {
        this.value = value;
    }

    public static List<Long> getBaseOfThierdMethod() {
        return List.of(3L, 11L, 13L, 17L, 18L);
    }

    public Long getValue() {
        return value;
    }
}
