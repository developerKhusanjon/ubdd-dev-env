package uz.ciasev.ubdd_service.utils;

public enum MonthConverter {

    JANUARY("YANVAR"),
    FEBRUARY("FEVRAL"),
    MARCH("MART"),
    APRIL("APREL"),
    MAY("MAY"),
    JUNE("IYUN"),
    JULY("IYUL"),
    AUGUST("AVGUST"),
    SEPTEMBER("SENTYABR"),
    OCTOBER("OCTYABR"),
    NOVEMBER("NOYABR"),
    DECEMBER("DEKABR");

    private final String value;

    MonthConverter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
