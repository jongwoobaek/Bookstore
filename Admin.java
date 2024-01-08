package bookStore;

public class Admin extends Person {
    private enum Admins {
        ADMIN1("Admin", "Admin1234");

        private String id;
        private String pw;

        Admins(String id, String pw) {
            this.id = id;
            this.pw = pw;
        }
    }

    private Admin() {
    }

    private static class AdminInstanceHolder {
        private static final Admin INSTANCE = new Admin();
    }

    public static Admin getInstance() {
       return AdminInstanceHolder.INSTANCE;
    }

    @Override
    public String getPersonInfo() {
        return """
                Name : %s
                Contact information : %s
                ID : %s
                PW : %s%n
                """
                .formatted(this.name, this.phoneNumber, Admins.ADMIN1.id, Admins.ADMIN1.pw);

    }

    public String getId() {
        return Admins.ADMIN1.id;
    }

    public String getPassword() {
        return Admins.ADMIN1.pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
