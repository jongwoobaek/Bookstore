package bookstore;

public class Customer extends Person {
    public Customer(String name, String phoneNumber) {
        super(name, phoneNumber);
    }

    @Override
    public String getPersonInfo() {
        return "Customer name : %s%nCustomer phone number : %s%n"
                .formatted(this.name, this.phoneNumber);
    }
}
