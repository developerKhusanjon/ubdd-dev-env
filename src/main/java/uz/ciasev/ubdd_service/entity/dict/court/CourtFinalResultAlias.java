package uz.ciasev.ubdd_service.entity.dict.court;

public enum CourtFinalResultAlias {
    PUNISHMENT("1"),
    TERMINATION("2"),
    RETURNING("4"),
    SEPARATING("5"),
    MATERIAL_REJECTED("112"),
    SENT_TO_OTHER_COURT("113"),
    RE_QUALIFICATION("115"),
    APPEAL("215, 216, 217"),
    NOT_RELATE("218"),
    OTHER("other");

    private final String value;

    CourtFinalResultAlias(String value) {
        this.value = value;
    }

    public String getCourtCode() {
        return value;
    }
}
