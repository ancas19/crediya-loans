package co.com.crediya.commons.enums;

public enum ErrorMessages {
    ERROR_MESSAGE_GLOBAL_EXCEPTION("Internal server error, contact with support for help"),;
    private final String message;

    ErrorMessages(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
