package bookstore.bookstoreException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookstoreExceptionList {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\d{3}-\\d{3,4}-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {
            return false;
        }

        return true;
    }
}
