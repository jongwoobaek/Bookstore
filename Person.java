package bookstore;

public abstract class Person {
    protected String name;
    protected String phoneNumber;
    protected String address;

    public Person(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Person(){}

    public abstract String getPersonInfo();
}
