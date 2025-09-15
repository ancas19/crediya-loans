package co.com.crediya.loan.model.commons.enums;

public enum Constants {

    PENDING("PENDIENTE"),
    APPROVED("APROBADO"),
    REJECTED("RECHAZADO"),
    AUTHORIZATION("Authorization"),
    ROLE("ROLE_%s"),
    CALIM_IDENTIFICATION("IDENTIFICATION"),
    CLAIM_ROLE("ROLE"),
    BEARER("Bearer ");

    private final String value;

    Constants(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
