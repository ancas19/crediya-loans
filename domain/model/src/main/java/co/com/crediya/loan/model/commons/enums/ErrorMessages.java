package co.com.crediya.loan.model.commons.enums;

public enum ErrorMessages {
    ERROR_MESSAGE_GLOBAL_EXCEPTION("Internal server error, contact with support for help"),
    ERROR_SEARCHING_USER_INFORMATION("Error searching user information"),
    ERROR_STATE_NOT_FOUND("State with name %s not found"),
    ERROR_MESSAGE_LOAN_TYPE_NOT_FOUND("Loan type with name %s not found"),
    ERROR_MESSAGE_USER_NOT_FOUND("User with identification %s not found"),
    ERROR_MESSAGES_USER_CREATE_LOAN("A user cannot create a request loan for another user"),
    ERROR_MESSAGE_LOANS_NOT_FOUND("Loans not found"),
    ERROR_MESSAGE_LOAN_NOT_FOUND("Loan with id %s not found"),
    ERROR_STATE_ID_NOT_FOUND("State with id %s not found"),
    ERROR_MESSAGE_LOAN_STATE_CANNOT_BE_UPDATED("The loan state cannot be updated because it is not  in pending state"),
    ERROR_MESSAGE_TEMPLATE_NOT_FOUND("Template with name %s not found"),;
    private final String message;

    ErrorMessages(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
