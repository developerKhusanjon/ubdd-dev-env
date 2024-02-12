package uz.ciasev.ubdd_service.entity.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum AdmStatusAlias {

    REGISTERED(1L),
    CONSIDERING(3L),
    SENT_TO_ORGAN(4L),
    PREPARE_FOR_COURT(6L),
    RETURN_FROM_ORGAN(5L),
    RETURN_FROM_COURT(7L),
    DECISION_MADE(2L),
    SENT_TO_COURT(9L),
    SEND_TO_MIB(15L),
    IN_EXECUTION_PROCESS(11L),
    EXECUTED(12L),
    MERGED(13L),
    RETURN_FROM_MIB(18L),
    IN_REVIEW_PROCESS(20L),

    @Deprecated
    ANNULLED(8L);

    private final Long id;

    public static List<Long> getIds(AdmStatusAlias... values) {
        return Arrays.stream(values).map(AdmStatusAlias::getId).collect(Collectors.toList());
    }

    public boolean is(AdmStatusAlias alias) {
        return this.equals(alias);
    }

    public AdmStatusAlias getAlias() {
        return this;
    }

    //    EXECUTION_NOT_POSSIBLE,
}
