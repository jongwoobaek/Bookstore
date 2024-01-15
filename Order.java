package bookstore;

import bookstore.bookstoreException.BookstoreException;
import bookstore.bookstoreException.BookstoreExceptionList;
import bookstore.bookstoreException.ErrorCode;

import java.util.*;

public class Order {
    private Scanner sc;
    private Customer customer;
    private AdminService admin = AdminService.getInstance();
    //    private List<Book> basket = new ArrayList<>();
    private Map<Book, Integer> basket = new HashMap<Book, Integer>();
    private BookService bookService = new BookService();

    BookstoreExceptionList error = new BookstoreExceptionList();

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
            try {
                System.out.print("Please enter your name : ");
                String tempName = sc.next();

                /*
                  입력 받은 이름이 지정된 형식과 일치하는지 확인합니다.
                  일치하지 않을 경우 exception을 발생시킵니다.
                  일치할 경우 name에 입력 받은 이름을 할당합니다.
                 */
                if (error.isValidName(tempName)) throw new BookstoreException(ErrorCode.INVALID_INPUT_NAME);
                else {
                    name = tempName;
                    break;
                }
            } catch (Exception e) {
                System.out.println();
            }
        }

        String phoneNumber;

        /*
          입력 받은 번호가 지정된 형식과 일치하는지 확인합니다.
          일치하지 않을 경우 exception을 발생시킵니다.
          일치할 경우 phoneNumber에 입력 받은 번호를 할당합니다.
         */
        while (true) {
            try {
                System.out.print("Please enter your phone number(010-0000-0000) : ");
                String inputPhoneNum = sc.next();

                if (error.isValidPhoneNumber(inputPhoneNum)) {
                    throw new BookstoreException(ErrorCode.INVALID_INPUT_PHONENUMBER);
                } else {
                    phoneNumber = inputPhoneNum;

                    // 번호까지 일치하면 Customer 생성자에 입력 받은 값을 할당한 name과 phoneNumber를 이용하여 인스턴스를 생성합니다.
                    customer = new Customer(name, phoneNumber);

                    System.out.println("\n**************************");
                    System.out.println("Welcome to Shopping Mall");
                    System.out.println("Welcome to Book Market!\n");

                    break;
                }
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    /**
     * 프로그램 상에서 선택할 수 있는 메뉴 목록을 보여줍니다.
     */
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

    /**
     * 로그인한 유저의 정보를 보여줍니다.
     * 관리자 로그인을 했을 경우 Adminstrator mode를 띄워 줍니다.
     */
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

    /**
     * checkAndPrintEmptyBasket 메소드를 호출하여 장바구니가 비어있는지를 확인합니다.
     * 장바구니가 비어있지 않으면 showBookInfoAndQuantity 메소드를 호출합니다.
     * @see #checkAndPrintEmptyBasket()
     * @see #showBookInfoAndQuantity()
     */
    public void showBasketList() {
        if (checkAndPrintEmptyBasket()) return;

        System.out.println("\n[Basket list]");

        showBookInfoAndQuantity();
    }

    /**
     * checkAndPrintEmptyBasket 메소드를 호출하여 장바구니가 비어있는지를 확인합니다.
     * 장바구니가 비어있지 않으면 장바구니를 비운 후 메세지를 출력합니다.
     * @see #checkAndPrintEmptyBasket()
     */
    public void clearBasket() {
        if (checkAndPrintEmptyBasket()) return;

        basket.clear();
        System.out.println("Bsket has been emptied.");
    }

    /**
     * bookService에 입력되어 있는 책들의 정보를 출력합니다.
     * @see #bookService
     * @see #error
     */
    public void addBookToBasket() {
        for (String bookInfo : bookService.getBookInfo()) System.out.print(bookInfo);

        // id를 통해 선택될 Book의 인스턴스를 null로 초기화 합니다.
        Book pickedBook = null;

        while (true) {
            // Book에 인스턴스가 할당되면 반복문이 종료됩니다.
            if (pickedBook != null) break;

            // 사용자로부터 id를 입력받기 위해 메세지를 출력합니다.
            System.out.print("Please enter the ID of the book to add to the basket :");
            String inputID = sc.next();

            /*
              bookService.getBookList() 메소드를 통해 Book들이 저장되어 있는 List를 불러옵니다.
              입력 받은 id와 일치하는 Book이 있을 경우 pickedBook에 해당 Book을 할당합니다.
             */
            for (Book book : bookService.getBookList()) {
                if (book.getId().equals(inputID)) {
                    pickedBook = book;
                }
            }

            // 입력 받은 id가 유효하지 않을 경우 exception을 발생시키고, 반복문에 계속됩니다.
            try {
                if (!error.isExistBookId(pickedBook)) {
                    throw new BookstoreException(ErrorCode.BOOKLIST_NO_ID);
                }
            } catch (Exception e) {
                System.out.println();
                continue;
            }

            // id가 유효할 경우 사용자에게 입력 확인 메세지를 출력합니다.
            System.out.println("Would you like to add it to the basket? Y | N");
            String inputAnswer = sc.next();

            if (inputAnswer.equalsIgnoreCase("Y")) {
                /*
                  "y" or "Y"가 입력 되었을 때, basket에 pickedBook이 포함되어 있지 않을 경우 basket의 키에 pickedBook을
                  입력하고, 값에 기존에 입력 되어 있던 값에 1을 더해줍니다.
                 */
                if (basket.containsKey(pickedBook)) basket.put(pickedBook, basket.get(pickedBook) + 1);
                // 그렇지 않을 경우 키에 pickedBook을 입력하고, 값에 1을 입력합니다.
                else basket.put(pickedBook, 1);
            } else if (inputAnswer.equalsIgnoreCase("N")) {
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
