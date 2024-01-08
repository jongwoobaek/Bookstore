package bookstore;

public class AdminService extends Person {
    private Admin[] admins = Admin.values();
    private Admin admin;
    private String id;
    private String pw;

    private AdminService() {
    }

    private static class AdminInstanceHolder {
        private static final AdminService INSTANCE = new AdminService();
    }

    public static AdminService getInstance() {
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
                .formatted(this.name, this.phoneNumber, this.id, this.pw);

    }

    public boolean checkAdminId(String inputId) {
        for (Admin admin : admins) {
            if (admin.getId().equals(inputId)) {
                this.admin = admin;
                return true;
            }
        }

        return false;
    }

    public boolean checkAdminPw(String inputPw) {
        if (this.admin.getPw().equals(inputPw)) return true;

        admin = null;

        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
