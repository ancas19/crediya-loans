package co.com.crediya.loan.model.commons.enums;

public enum Endpoints {
    CREATE_LOANS("/solicitud"),
    FIND_LOAN("/solicitud/detalles");

    private final String value;

    Endpoints(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
