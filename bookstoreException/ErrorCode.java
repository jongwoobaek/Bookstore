package bookstore.bookstoreException;

public enum ErrorCode implements EnumModel{
    INVALID_INPUT_PHONENUMBER(400, "The phone number format is not valid.", "P001"),
    INVALID_INPUT_NAME(400, "You can only enter letters!", "N001"),
    BOOKLIST_NO_ID(404, "The entered ID is invalid!", "B001");

    private  int status;
    private  String code;
    private  String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
