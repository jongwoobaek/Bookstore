package oop.day3.bookStore;

import java.util.Scanner;

public class Order {
    private Book[] bookList = {
            new Book.Builder()
                    .id("ISBN1234")
                    .category("쉽게 배우는 JSP 웹 프로그래밍")
                    .price(27000)
                    .author("송미영")
                    .title("단계별로 쇼핑몰을 구현하며 배우는 JSP 웹 프로그래밍")
                    .publisher("IT전문서")
                    .date("2018/10/08")
                    .build(),
            new Book.Builder()
                    .id("ISBN1235")
                    .category("안드로이드 프로그래밍")
                    .price(33000)
                    .author("우재남")
                    .title("실습 단계별 명쾌한 멘토링!")
                    .publisher("IT전문서")
                    .date("2022/01/22")
                    .build(),
            new Book.Builder()
                    .id("ISBN1236")
                    .category("스크래치")
                    .price(2200)
                    .author("고광일")
                    .title("컴퓨팅 사고력을 키우는 블록 코딩")
                    .publisher("컴퓨터 입문")
                    .date("2019/06/10")
                    .build(),
    };
    private Customer customer;
    private int basketNum = 0;

    public void startOrder() {
        Scanner sc = new Scanner(System.in);

        System.out.print("당신의 이름을 입력하세요 : ");
        String name = sc.next();

        System.out.print("연락처를 입력하세요 : ");
        String phoneNumber = sc.next();

        customer = new Customer(name, phoneNumber);

        System.out.println("**************************");
        System.out.println("Welcome to Shopping Mall");
        System.out.println("Welcome to Book Market!\n");

        while (true) {
            showMenu();

            int inputNum = sc.nextInt();

            if (inputNum == 8) {
                System.out.println("프로그램 종료");
                break;
            }

            switch (inputNum) {
                case 1 -> showCustomerInfo(customer);
                case 2 -> showBasketList(customer);
                case 4 -> {
                    showBookList();
                    addBook(sc);
                }
                default -> System.out.println("없는 번호입니다.");
            }
        }
    }

    private void showCustomerInfo(Customer customer) {
        System.out.print(customer.getCustomerInfo());
    }

    private void showMenu() {
        System.out.println("\n*************************");
        System.out.println("1. 고객 정보 확인하기\n2. 장바구니 상품 목록 보기\n4. 바구니에 항목 추가히기\n8. 종료");
        System.out.println("*************************\n");
        System.out.print("메뉴 번호를 선택해주세요 ");
    }

    private void showBookList() {
        for (Book book : bookList) System.out.print(book.getBookInfo());
    }

    private void addBook(Scanner sc) {
        Book pickedBook = null;

        while (true) {
            if (pickedBook != null) break;

            System.out.print("장바구니에 추가할 도서의 ID를 입력하세요 :");
            String inputID = sc.next();

            for (Book book : bookList) {
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
                    Book[] customerBasket = customer.getBasket();
                    customerBasket[basketNum] = pickedBook;

                    System.out.printf("%s 도서가 장바구니에 추가되었습니다.%n", customerBasket[basketNum].getId());

                    basketNum++;
                }

                if (Character.toUpperCase(inputAnswer) == 'N') {
                    break;
                }
            }
        }
    }

    private void showBasketList(Customer customer) {
        boolean isBasketEmpty = true;

        System.out.println("\n장바구니 목록:");

        for (Book book : customer.getBasket()) {
            if (book == null) continue;

            System.out.print(book.getBookInfo());
            isBasketEmpty = false;
        }

        if (isBasketEmpty) System.out.println("장바구니가 비어있습니다.");
    }
}
