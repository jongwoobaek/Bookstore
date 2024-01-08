package bookstore;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private List<Book> bookList = new ArrayList<Book>(
            List.of(new Book.Builder()
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
                            .build()
            ));

    public List<String> getBookInfo() {
        List<String> booksInfo = new ArrayList<String>();

        for (Book book : bookList) {
            booksInfo.add(book.toString());
        }

        return booksInfo;
    }

    public List<Book> getBookList() {
        return bookList;
    }
}
