package bookstore.bookstoreException;

public class BookstoreException extends RuntimeException {
    private ErrorCode errorCode;

    public BookstoreException(ErrorCode code) {
        this.errorCode = code;

        System.out.println("\n******************** error 발생 ******************** ");
        System.out.println(errorCode.getCode()+" / "+errorCode.getMessage()+" / "+errorCode.getStatus());
        System.out.println("***************************************************");
    }
}
