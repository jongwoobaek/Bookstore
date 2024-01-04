package bookStore;

import java.util.Scanner;

public class BookStoreMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Order order = new Order(sc);

        order.login();

        while (true) {
            order.showMenu();

            int inputNum = sc.nextInt();

            switch (inputNum) {
                case 1 -> order.showCustomerInfo();
                case 2 -> order.showBasketList();
                case 3 -> order.clearBasket();
                case 4 -> {
                    order.showBookList();
                    order.addBasketList();
                }
                case 5 -> order.reduceQuantity();
                case 6 -> order.deleteBook();
                case 7 -> order.showReceipt();
                case 8 -> order.exit();
                case 9 -> order.adminLogin();
                default -> System.out.println("The number does not exist.");
            }
        }

    }
}
