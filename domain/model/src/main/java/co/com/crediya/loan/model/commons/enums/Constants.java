package co.com.crediya.loan.model.commons.enums;

public enum Constants {

    PENDING("PENDIENTE"),
    APPROVED("APROBADO"),
    REJECTED("RECHAZADO"),
    AUTHORIZATION("Authorization"),
    ROLE("ROLE_%s"),
    CALIM_IDENTIFICATION("IDENTIFICATION"),
    CLAIM_ROLE("ROLE"),
    BEARER("Bearer "),
    TABLE_LOAN_CONTENT(
            """
                    <tr th:each="payment">
                      <td style="padding:8px;font-size:13px;border:1px solid #e6eef0;">%s</td>
                      <td style="padding:8px;font-size:13px;border:1px solid #e6eef0;">%s</td>
                      <td style="padding:8px;font-size:13px;border:1px solid #e6eef0;">%s</td>
                      <td style="padding:8px;font-size:13px;border:1px solid #e6eef0;">%s</td>
                      <td style="padding:8px;font-size:13px;border:1px solid #e6eef0;">%s</td>
                    </tr>
                  """
    );

    private final String value;

    Constants(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
