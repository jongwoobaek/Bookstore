package bookstore;

public class Book {
    private String id;
    private String category;
    private int price;
    private String author;
    private String title;
    private String publisher;
    private String date;

    public static class Builder {
        private String id;
        private String category;
        private int price;
        private String author;
        private String title;
        private String publisher;
        private String date;

        public Builder id(String id) {
            this.id = id;

            return this;
        }

        public Builder category(String category) {
            this.category = category;

            return this;
        }

        public Builder price(int price) {
            this.price = price;

            return this;
        }

        public Builder author(String author) {
            this.author = author;

            return this;
        }

        public Builder title(String title) {
            this.title = title;

            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;

            return this;
        }

        public Builder date(String date) {
            this.date = date;

            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    private Book(Builder builder) {
        this.id = builder.id;
        this.category = builder.category;
        this.price = builder.price;
        this.author = builder.author;
        this.title = builder.title;
        this.publisher = builder.publisher;
        this.date = builder.date;
    }

    public String getId() {
        return this.id;
    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "%s | %s | %d | %s | %s | %s | %s |%n".formatted(
                id, category, price, author, title, publisher, date
        );
    }
}
