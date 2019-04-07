package Inquiry;

public enum StatusCode {
    UNKNOWN_ERROR(-1),
    SIGN_IN(0), SIGN_UP(1),
    SUCCESS(0), FIELD(1),
    AVAILABLE(0), UNAVAILABLE(1),
    TIMEOUT(2), NETWORK_ERROR(3);

    private int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
