package oop.day3.bookStore;

public class Customer {
    private String name;
    private String phoneNumber;
    private Book[] basket = new Book[3];

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerInfo() {
        return "고객 이름 : %s%n고객 전화번호 : %s%n".formatted(this.name, this.phoneNumber);
    }

    public Book[] getBasket() {
        return basket;
    }
}
