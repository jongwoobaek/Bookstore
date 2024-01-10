package bookstore.bookstoreException;

import bookstore.Book;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookstoreExceptionList {
    public static boolean isValidName(String name) {
        String regex = "^[a-zA-Z가-힣]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);

        if (matcher.matches()) return false;

        return true;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\d{3}-\\d{3,4}-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) return false;

        return true;
    }

    public static boolean isExistBookId(Book book) {
        if (book == null) return false;
        return true;
    }
}
