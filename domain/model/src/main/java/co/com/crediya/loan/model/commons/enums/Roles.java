package co.com.crediya.loan.model.commons.enums;

public enum Roles {
    ADMIN("ADMIN"),
    ASESOR("ASESOR"),
    CLIENTE("CLIENTE");


    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
