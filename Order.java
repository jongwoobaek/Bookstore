package bookstore;

import bookstore.bookstoreException.BookstoreExceptionList;

import java.util.*;

public class Order {
    private Scanner sc;
    private Customer customer;
    private AdminService admin = AdminService.getInstance();
    //    private List<Book> basket = new ArrayList<>();
    private Map<Book, Integer> basket = new HashMap<Book, Integer>();
    private BookService bookService = new BookService();

    private BookstoreExceptionList error = new BookstoreExceptionList();

    public Order(Scanner sc) {
        this.sc = sc;
    }

    /**
     * Scanner를 통해 사용자 이름과 연락처를 입력받습니다.
     * 입력받은 이름과 연락차를 이용하여 Customer인스턴스를 생성합니다.
     * 환영인사를 출력합니다.
     */
    public void login() {
        System.out.println("\n[Login]");
        String name;

        while (true) {
            System.out.print("Please enter your name : ");
            String tempName = sc.next();

            if (!tempName.matches("^[a-zA-Z가-힣]*$")) {
                System.out.println("You can only enter letters!\n");

                continue;
            }

            name = tempName;
            break;
        }

        String phoneNumber;

        while (true) {
            System.out.print("Please enter your phone number : ");
            String tempPhoneNum = sc.next();

            if (!tempPhoneNum.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
                System.out.println("""
                    You need to enter as follows:
                    010-0000-0000
                    """);
            } else {
                phoneNumber = tempPhoneNum;
                customer = new Customer(name, phoneNumber);

                System.out.println("\n**************************");
                System.out.println("Welcome to Shopping Mall");
                System.out.println("Welcome to Book Market!\n");

                break;
            }
        }
    }

    public void showMenu() {
        System.out.println("\n******************************************************************************");
        System.out.printf("""
                        %-45s%-45s
                        %-45s%-45s
                        %-45s%-45s
                        %-45s%-45s
                        %-45s
                        """,
                "1. Check customer information", "2. View basket contents",
                "3. Empty the basket", "4. Add item to the basket",
                "5. Reduce quantity of items in the basket", "6. Delete items from the basket",
                "7. Show the receipt", "8. Exit the program",
                "9. Admin login");
        System.out.println("******************************************************************************\n");
        System.out.print("Please select a menu number. ");
    }

    public void showCustomerInfo() {
        if (!(admin.name == null)) {
            System.out.println("\n======== Administrator mode ========");
            System.out.print(customer.getPersonInfo());
            System.out.println("============ =========== ============");
        } else {
            System.out.println("\n[Customer information]");
            System.out.print(customer.getPersonInfo());
        }
    }

    public void showBasketList() {
        if (checkAndPrintEmptyBasket()) return;

        System.out.println("\n[Basket list]");

        showBookInfoAndQuantity();
    }

    public void clearBasket() {
        if (checkAndPrintEmptyBasket()) return;

        basket.clear();
        System.out.println("Bsket has been emptied.");
    }

    public void addBasketList() {
        for (String bookInfo : bookService.getBookInfo()) {
            System.out.print(bookInfo);
        }

        Book pickedBook = null;

        while (true) {
            if (pickedBook != null) break;

            System.out.print("Please enter the ID of the book to add to the basket :");
            String inputID = sc.next();

            for (Book book : bookService.getBookList()) {
                if (book.getId().equals(inputID)) {
                    pickedBook = book;
                }
            }

            if (pickedBook == null) {
                System.out.println("The entered ID is invalid!\n");
                continue;
            }

            System.out.println("Would you like to add it to the basket? Y | N");
            char inputAnswer = Character.toUpperCase(sc.next().charAt(0));

            if (inputAnswer == 'Y') {
                if (basket.containsKey(pickedBook)) basket.put(pickedBook, basket.get(pickedBook) + 1);
                else basket.put(pickedBook, 1);
            } else if (inputAnswer == 'N') {
                System.out.println("The item addition has been canceled");
                break;
            } else {
                System.out.println("Please enter only Y or N!");
                break;
            }

            System.out.printf("%s The book has been added to the basket%n", pickedBook.getId());
        }
    }

    public void reduceQuantity() {
        if (checkAndPrintEmptyBasket()) return;

        showBookInfoAndQuantity();

        System.out.print("Please enter the ID of the item to decrease its quantity: ");
        String inputID = sc.next();

        boolean isVerifiedID = false;

        for (Book book : basket.keySet()) {
            if (book.getId().equals(inputID)) {
                basket.put(book, basket.get(book) - 1);
                isVerifiedID = true;

                if (basket.get(book) == 0) basket.remove(book);
            }
        }

        if (!isVerifiedID) {
            System.out.println("\nThere is no matching item.");
            return;
        }

        showBasketList();
    }

    public void deleteBook() {
        if (checkAndPrintEmptyBasket()) return;

        showBookInfoAndQuantity();

        System.out.print("Please enter the ID of the item to delete. ");
        String inputID = sc.next();

        boolean isVerifiedID = false;
        Book targetBook = null;

        for (Book book : basket.keySet()) {
            if (book.getId().equals(inputID)) {
                targetBook = book;
                isVerifiedID = true;
            }
        }

        basket.remove(targetBook);

        if (!isVerifiedID) {
            System.out.println("\nThere is no matching item.");
            return;
        }

        System.out.println("The item has been deleted.");

        showBasketList();
    }

    public void showReceipt() {
        if (checkAndPrintEmptyBasket()) return;

        showBasketList();

        int totalQuantity = 0;
        int totalPrice = 0;

        for (int quantity : basket.values()) {
            totalQuantity += quantity;
        }

        for (Book book : basket.keySet()) {
            totalPrice += book.getPrice() * basket.get(book);
        }

        System.out.println("=============== Total amount ===============");
        System.out.printf("Total %d books | Amount %d won%n", totalQuantity, totalPrice);
    }

    public void adminLogin() {
        if (!(admin.name == null)) {
            System.out.println("You are already logged in");
            return;
        }

        System.out.println("Please enter the administrator information.");

        System.out.print("ID : ");
        String adminId = sc.next();

        if (!admin.checkAdminId(adminId)) {
            System.out.println("The entered ID is not registered.");
            return;
        }

        System.out.print("PW : ");
        String adminPassword = sc.next();

        if (!admin.checkAdminPw(adminPassword)) {
            System.out.println("The password is incorrect..");
            return;
        }

        admin.setName(customer.name);
        admin.setPhoneNumber(customer.phoneNumber);
        admin.setId(adminId);
        admin.setPw(adminPassword);

        System.out.println(admin.getPersonInfo());
    }

    public void exit() {
        System.out.println("Exit the program.");
        System.exit(0);
    }

    private void showBookInfoAndQuantity() {
        basket.forEach((book, quantity) -> {
            System.out.printf("Quantity: %d books:%n" +
                            "%s%n",
                    quantity, book.toString());
        });
    }

    private boolean checkAndPrintEmptyBasket() {
        if (basket.isEmpty()) {
            System.out.println("The basket is empty.");
            return true;
        }

        return false;
    }
}
