package uz.ciasev.ubdd_service.mvd_core.api.mib.api.types;

public enum ProtocolType {
    ADM(3), GAI(2), RADAR(1);

    private int value;

    ProtocolType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return String.valueOf(value);
    }

}
