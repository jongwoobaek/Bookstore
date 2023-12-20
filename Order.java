package oop.day3.bookStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Order {
    private Scanner sc;
    private Customer customer;
    private Admin admin = Admin.getInstance();
    private List<Book> basket = new ArrayList<>();
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

    public void showMenu() {
        System.out.println("\n*************************");
        System.out.println("1. 고객 정보 확인하기\n2. 장바구니 상품 목록 보기\n4. 바구니에 항목 추가히기\n8. 종료\n9. 관리자 로그인");
        System.out.println("*************************\n");
        System.out.print("메뉴 번호를 선택해주세요 ");
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
            } else {
                System.out.println("장바구니에 추가하겠습니까? Y | N");
                char inputAnswer = sc.next().charAt(0);

                if (Character.toUpperCase(inputAnswer) == 'Y') {
                    basket.add(pickedBook);
                    System.out.printf("%s 도서가 장바구니에 추가되었습니다.%n", pickedBook.getId());
                }

                if (Character.toUpperCase(inputAnswer) == 'N') {
                    break;
                }
            }
        }
    }

    public void showBasketList() {
        System.out.println("\n장바구니 목록:");

        for (Book book : basket) {
            System.out.print(book.toString());
        }

        if (basket.isEmpty()) System.out.println("장바구니가 비어있습니다.");
    }
}
