package uz.ciasev.ubdd_service.mvd_core.api.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourtMethod {

    COURT_FIRST(1), COURT_SECOND(2), COURT_SECOND_MATERIAL(21), COURT_THIRD(3),  COURT_THIRD_MATERIAL(32), COURT_FIFTH(5), COURT_SEVEN(7), COURT_NINE(9);

    private final int id;
}
