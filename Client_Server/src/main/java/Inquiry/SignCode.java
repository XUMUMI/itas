package Inquiry;

public enum SignCode {
    SIGN_IN(0), SIGN_UP(1),
    SUCCESS(0), FILD(1),
    AVAILABLE(0), UNAVAILABLE(1);

    private int code;

    SignCode(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
