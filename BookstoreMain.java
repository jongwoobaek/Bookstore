package bookstore;

import java.util.Scanner;

public class BookstoreMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Order order = new Order(sc);

        order.login();

        while (true) {
            order.showMenu();

            String inputNum = sc.next();

            switch (inputNum) {
                case "1" -> order.showCustomerInfo();
                case "2" -> order.showBasketList();
                case "3" -> order.clearBasket();
                case "4" -> order.addBookToBasket();
                case "5" -> order.reduceQuantity();
                case "6" -> order.deleteBook();
                case "7" -> order.showReceipt();
                case "8" -> order.exit();
                case "9" -> order.adminLogin();
                default -> {
                    if (inputNum.matches("[+-]?\\d*(\\.\\d+)?")) {
                        System.out.println("The number does not exist.");
                    } else {
                        System.out.println("You can only enter numbers!");
                    }
                }
            }
        }

    }
}
