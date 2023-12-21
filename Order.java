package bookStore;

import java.util.*;

public class Order {
    private Scanner sc;
    private Customer customer;
    private Admin admin = Admin.getInstance();
    //    private List<Book> basket = new ArrayList<>();
    private Map<Book, Integer> basket = new HashMap<Book, Integer>();
    private BookService bookService = new BookService();

    public Order(Scanner sc) {
        this.sc = sc;
    }

    /**
     * Scanner를 통해 사용자 이름과 연락처를 입력받습니다.
     * 입력받은 이름과 연락차를 이용하여 Customer인스턴스를 생성합니다.
     * 환영인사를 출력합니다.
     */
    public void login() {
        System.out.print("당신의 이름을 입력하세요 : ");
        String name = sc.next();

        System.out.print("연락처를 입력하세요 : ");
        String phoneNumber = sc.next();

        customer = new Customer(name, phoneNumber);

        System.out.println("**************************");
        System.out.println("Welcome to Shopping Mall");
        System.out.println("Welcome to Book Market!\n");
    }

    public void showMenu() {
        System.out.println("\n*************************");
        System.out.println("""
                1. 고객 정보 확인하기\t2. 장바구니 상품 목록 보기
                3. 장바구니 비우기\t4. 바구니에 항목 추가히기
                5. 장바구니의 항목 수량 줄이기\t6. 장바구니의 항목 삭제하기
                7. 영수증 표시하기\t8. 종료
                9. 관리자 로그인""");
        System.out.println("*************************\n");
        System.out.print("메뉴 번호를 선택해주세요 ");
    }

    public void adminLogin() {
        if (!(admin.name == null)) {
            System.out.println("이미 로그인 되었습니다.");
            return;
        }

        System.out.println("관리자 정보를 입력하세요.");

        System.out.print("아이디 : ");
        String adminId = sc.next();

        if (!admin.getId().equals(adminId)) {
            System.out.println("등록되지 않은 아이디입니다.");
            return;
        }

        System.out.print("비밀번호 : ");
        String adminPassword = sc.next();

        if (!admin.getPassword().equals(adminPassword)) {
            System.out.println("비밀번호가 다릅니다.");
            return;
        }

        admin.setName(customer.name);
        admin.setPhoneNumber(customer.phoneNumber);

        System.out.println(admin.getPersonInfo());
    }

    public void showCustomerInfo() {
        if (!(admin.name == null)) {
            System.out.println("\n======== 관리자 모드 ========");
            System.out.print(customer.getPersonInfo());
            System.out.println("======== ========= ========");
        } else {
            System.out.print(customer.getPersonInfo());
        }
    }

    private void showBookInfoAndQuantity() {
        basket.forEach((book, quantity) -> {
            System.out.printf("수량 %d권:%n" +
                            "%s",
                    quantity, book.toString());
        });
    }

    private boolean checkAndPrintEmptyBasket() {
        if (basket.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return true;
        }
        return false;
    }

    public void showBasketList() {
        if (checkAndPrintEmptyBasket()) return;

        System.out.println("\n장바구니 목록:");

        showBookInfoAndQuantity();
    }

    public void clearBasket() {
        if (checkAndPrintEmptyBasket()) return;

        basket.clear();
        System.out.println("장바구니를 비웠습니다.");

    }

    public void reduceQuantity() {
        if (checkAndPrintEmptyBasket()) return;

        showBookInfoAndQuantity();

        System.out.print("수량을 줄일 항목의 아이디를 입력하세요: ");
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
            System.out.println("\n일치하는 항목이 없습니다.");
            return;
        }

        showBasketList();
    }

    public void deleteBook() {
        if (checkAndPrintEmptyBasket()) return;

        showBookInfoAndQuantity();

        System.out.print("삭제할 항목의 아이디를 입력하세요. ");
        String inputID = sc.next();

        boolean isVerifiedID = false;

        for (Book book : basket.keySet()) {
            if (book.getId().equals(inputID)) {
                basket.remove(book);

                isVerifiedID = true;
            }
        }

        if (!isVerifiedID) {
            System.out.println("\n일치하는 항목이 없습니다.");
            return;
        }

        System.out.println("해당 항목이 삭제되었습니다.");

        showBasketList();
    }

    public void showReceipt(){
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

        System.out.println("=============== 총합 ===============");
        System.out.printf("총 %d권 | 금액 %d원%n", totalQuantity, totalPrice);
    }

    public void showBookList() {
        for (String bookInfo : bookService.getBookInfo()) {
            System.out.print(bookInfo);
        }
    }

    public void addBasketList() {
        Book pickedBook = null;

        while (true) {
            if (pickedBook != null) break;

            System.out.print("장바구니에 추가할 도서의 ID를 입력하세요 :");
            String inputID = sc.next();

            for (Book book : bookService.getBookList()) {
                if (book.getId().equals(inputID)) {
                    pickedBook = book;
                }
            }

            if (pickedBook == null) {
                System.out.println("유효하지 않은 아이디입니다.\n");
                continue;
            }

            System.out.println("장바구니에 추가하겠습니까? Y | N");
            char inputAnswer = sc.next().charAt(0);

            if (Character.toUpperCase(inputAnswer) == 'Y' && basket.containsKey(pickedBook)) {
                basket.put(pickedBook, basket.get(pickedBook) + 1);
            } else {
                basket.put(pickedBook, 1);
            }

            if (Character.toUpperCase(inputAnswer) == 'N') {
                System.out.println("항목 추가를 취소합니다.");
                break;
            }

            System.out.printf("%s 도서가 장바구니에 추가되었습니다.%n", pickedBook.getId());
        }
    }
}
