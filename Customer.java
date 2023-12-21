package bookStore;

public class Customer extends Person {
    public Customer(String name, String phoneNumber) {
        super(name, phoneNumber);
    }

    @Override
    public String getPersonInfo() {
        return "고객 이름 : %s%n고객 전화번호 : %s%n".formatted(this.name, this.phoneNumber);
    }
}
