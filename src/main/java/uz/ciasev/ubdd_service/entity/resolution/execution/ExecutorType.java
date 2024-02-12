package uz.ciasev.ubdd_service.entity.resolution.execution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/*
 * Дополнительная информация к изменению статуса для гаи.
 * Использование каждого из значений при новом кейсе надо уточнять у гаи
 * */
@Getter
@RequiredArgsConstructor
public enum ExecutorType {

    MIB_EXECUTOR,
    MIB_ORGAN,
    COURT_EXECUTION_BY_MATERIAL,
    BILLING,
    BILLING_WITH_DISCOUNT,
    MANUAL_EXECUTION,
    SYSTEM_AUTO_EXECUTION,

//    PenaltyPayed(21),
//    PenaltyPayedWithDiscount(22),
//    PenaltyWithdrawByMib(23),
//    RelatedWithCourt(24);
//
//    private final int value;
}
