// Book has no constructor, so the compiler adds an empty default one.
// The variables then get default values: null for String, 0 for numbers.
class Book {
    String title;
    int pages;
}

public class DefaultConstructorDemo {
    public static void main(String[] args) {
        Book book = new Book();
        System.out.println("title = " + book.title);
        System.out.println("pages = " + book.pages);
    }
}
