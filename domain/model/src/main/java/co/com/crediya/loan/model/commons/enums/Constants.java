package co.com.crediya.loan.model.commons.enums;

public enum Constants {

    PENDING("PENDIENTE"),;

    private final String value;

    Constants(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
