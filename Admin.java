package bookstore;

public enum Admin {
    ADMIN1("Admin", "Admin1234"),
    ADMIN12("Admin2", "Admin1235");

    private String id;
    private String pw;

    Admin(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }
}
