package bookStore;

public class Admin extends Person {
    private String id = "Admin";
    private String password = "Admin1234";

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
                .formatted(this.name, this.phoneNumber, this.id, this.password);

    }

    public String getId() {
        return this.id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
