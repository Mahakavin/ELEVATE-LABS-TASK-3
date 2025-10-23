package calc;
import java.util.*;

public class Task_3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Library lib = new Library();

        System.out.println("Welcome to the Library System!");

        // Register user
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.print("Enter your user ID: ");
        String userId = scan.nextLine();
        User user = new User(name, userId);
        lib.registerUser(user);

        // Add books
        System.out.print("How many books do you want to add? ");
        int bookCount = scan.nextInt();
        scan.nextLine(); // consume newline

        for (int i = 0; i < bookCount; i++) {
            System.out.println("\nEnter details for Book " + (i + 1));
            System.out.print("Title: ");
            String title = scan.nextLine();
            System.out.print("Author: ");
            String author = scan.nextLine();
            System.out.print("Book ID: ");
            String id = scan.nextLine();
            Book book = new Book(title, author, id);
            lib.addBook(book);
        }

        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View all books");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. View borrowed books");
            System.out.println("5. Exit");

            int choice = scan.nextInt();
            scan.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    lib.displayAllBooks();
                    break;
                case 2:
                    System.out.print("Enter Book ID to borrow: ");
                    String borrowId = scan.nextLine();
                    Book bookToBorrow = lib.findBookById(borrowId);
                    if (bookToBorrow != null) {
                        user.borrowBook(bookToBorrow);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Book ID to return: ");
                    String returnId = scan.nextLine();
                    Book bookToReturn = lib.findBookById(returnId);
                    if (bookToReturn != null) {
                        user.returnBook(bookToReturn);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 4:
                    user.displayBorrowedBooks();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
class Book {
    private String title;
    private String author;
    private String id;
    private boolean isAvailable;

    public Book(String title, String author, String id) {
        this.title = title;
        this.author = author;
        this.id = id;
        this.isAvailable = true;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getId() { return id; }
    public boolean isAvailable() { return isAvailable; }

    public void issue() {
        isAvailable = false;
    }

    public void returnBook() {
        isAvailable = true;
    }

    public void display() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ID: " + id);
        System.out.println("Availability: " + (isAvailable ? "Available" : "Issued"));
    }
}

 class User {
    private String name;
    private String userId;
    private List<Book> borrowedBooks;

    public User(String name, String userId) {
        this.name = name;
        this.userId = userId;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getUserId() { return userId; }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.issue();
            System.out.println(name + " borrowed \"" + book.getTitle() + "\"");
        } else {
            System.out.println("Sorry, \"" + book.getTitle() + "\" is already issued.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.returnBook();
            System.out.println(name + " returned \"" + book.getTitle() + "\"");
        } else {
            System.out.println("This book was not borrowed by " + name);
        }
    }

    public void displayBorrowedBooks() {
        System.out.println(name + "'s Borrowed Books:");
        for (Book book : borrowedBooks) {
            book.display();
        }
    }
}

 class Library {
    private List<Book> allBooks;
    private List<User> allUsers;

    public Library() {
        allBooks = new ArrayList<>();
        allUsers = new ArrayList<>();
    }

    public void addBook(Book book) {
        allBooks.add(book);
    }

    public void registerUser(User user) {
        allUsers.add(user);
    }

    public Book findBookById(String id) {
        for (Book book : allBooks) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public void displayAllBooks() {
        System.out.println("Library Books:");
        for (Book book : allBooks) {
            book.display();
        }
    }
}

