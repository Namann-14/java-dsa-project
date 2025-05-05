import services.BookService;
import services.PatronService;
import services.ReservationService;
import services.TransactionService;
import models.Book;
import models.Patron;
import models.Reservation;
import models.Transaction;
import datastructures.LinkedList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.function.Consumer;

public class LibraryManager {
    private BookService bookService;
    private PatronService patronService;
    private TransactionService transactionService;
    private ReservationService reservationService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LibraryManager() {
        bookService = new BookService();
        patronService = new PatronService();
        transactionService = new TransactionService(bookService, patronService);
        reservationService = new ReservationService(bookService, patronService);
        
        // Add some sample data
        addSampleData();
    }

    private void addSampleData() {
        // Add sample books
        bookService.addBook("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 
                LocalDate.of(1925, 4, 10), "Fiction");
        bookService.addBook("To Kill a Mockingbird", "Harper Lee", "9780061120084", 
                LocalDate.of(1960, 7, 11), "Fiction");
        bookService.addBook("1984", "George Orwell", "9780451524935", 
                LocalDate.of(1949, 6, 8), "Dystopian");
        bookService.addBook("The Hobbit", "J.R.R. Tolkien", "9780547928227", 
                LocalDate.of(1937, 9, 21), "Fantasy");
        bookService.addBook("Pride and Prejudice", "Jane Austen", "9780141439518", 
                LocalDate.of(1813, 1, 28), "Romance");
        
        // Add sample patrons
        patronService.addPatron("John Smith", "john@example.com", "123 Main St", "2023-01-15");
        patronService.addPatron("Emily Johnson", "emily@example.com", "456 Oak Ave", "2023-02-20");
        patronService.addPatron("Michael Brown", "michael@example.com", "789 Pine Rd", "2023-03-10");
    }

    // BOOK MANAGEMENT
    public Book addBook(String title, String author, String isbn, String publicationDate, String genre) {
        try {
            LocalDate date = LocalDate.parse(publicationDate, DATE_FORMATTER);
            return bookService.addBook(title, author, isbn, date, genre);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return null;
        }
    }

    public boolean removeBook(String id) {
        return bookService.removeBook(id);
    }

    public Book findBookById(String id) {
        return bookService.findBookById(id);
    }

    public LinkedList<Book> findBooksByTitle(String title) {
        return bookService.findBooksByTitle(title);
    }

    public LinkedList<Book> findBooksByAuthor(String author) {
        return bookService.findBooksByAuthor(author);
    }

    public LinkedList<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public void displayBooksSortedByTitle(Consumer<Book> displayFunction) {
        bookService.displayBooksSortedByTitle(displayFunction);
    }

    public void displayBooksSortedByAuthor(Consumer<Book> displayFunction) {
        bookService.displayBooksSortedByAuthor(displayFunction);
    }

    public void updateBook(String id, String title, String author, String isbn, String publicationDate, String genre) {
        Book existingBook = bookService.findBookById(id);
        if (existingBook != null) {
            try {
                LocalDate date = publicationDate != null && !publicationDate.isEmpty() 
                    ? LocalDate.parse(publicationDate, DATE_FORMATTER)
                    : existingBook.getPublicationDate();
                
                Book updatedBook = new Book(
                    id,
                    title != null && !title.isEmpty() ? title : existingBook.getTitle(),
                    author != null && !author.isEmpty() ? author : existingBook.getAuthor(),
                    isbn != null && !isbn.isEmpty() ? isbn : existingBook.getIsbn(),
                    date,
                    genre != null && !genre.isEmpty() ? genre : existingBook.getGenre()
                );
                updatedBook.setAvailable(existingBook.isAvailable());
                bookService.updateBook(updatedBook);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }

    // PATRON MANAGEMENT
    public Patron addPatron(String name, String contactInfo, String address, String membershipDate) {
        return patronService.addPatron(name, contactInfo, address, membershipDate);
    }

    public boolean removePatron(String id) {
        return patronService.removePatron(id);
    }

    public Patron findPatronById(String id) {
        return patronService.findPatronById(id);
    }

    public LinkedList<Patron> findPatronsByName(String name) {
        return patronService.findPatronsByName(name);
    }

    public LinkedList<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    public void updatePatron(String id, String name, String contactInfo, String address, String membershipDate) {
        Patron existingPatron = patronService.findPatronById(id);
        if (existingPatron != null) {
            Patron updatedPatron = new Patron(
                id,
                name != null && !name.isEmpty() ? name : existingPatron.getName(),
                contactInfo != null && !contactInfo.isEmpty() ? contactInfo : existingPatron.getContactInfo(),
                address != null && !address.isEmpty() ? address : existingPatron.getAddress(),
                membershipDate != null && !membershipDate.isEmpty() ? membershipDate : existingPatron.getMembershipDate()
            );
            patronService.updatePatron(updatedPatron);
        }
    }

    // TRANSACTION MANAGEMENT
    public Transaction borrowBook(String bookId, String patronId, int daysToReturn) {
        return transactionService.borrowBook(bookId, patronId, daysToReturn);
    }

    public boolean returnBook(String transactionId) {
        return transactionService.returnBook(transactionId);
    }

    public Transaction findTransactionById(String id) {
        return transactionService.findTransactionById(id);
    }

    public LinkedList<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    public LinkedList<Transaction> getActiveTransactions() {
        return transactionService.getActiveTransactions();
    }

    public LinkedList<Transaction> getTransactionsByPatronId(String patronId) {
        return transactionService.getTransactionsByPatronId(patronId);
    }

    public LinkedList<Transaction> getTransactionsByBookId(String bookId) {
        return transactionService.getTransactionsByBookId(bookId);
    }

    public LinkedList<Transaction> getOverdueTransactions() {
        return transactionService.getOverdueTransactions();
    }

    // RESERVATION MANAGEMENT
    public Reservation reserveBook(String bookId, String patronId) {
        return reservationService.reserveBook(bookId, patronId);
    }

    public boolean cancelReservation(String reservationId) {
        return reservationService.cancelReservation(reservationId);
    }

    public Reservation getNextReservation(String bookId) {
        return reservationService.getNextReservation(bookId);
    }

    public boolean fulfillReservation(String reservationId) {
        return reservationService.fulfillReservation(reservationId);
    }

    public Reservation findReservationById(String id) {
        return reservationService.findReservationById(id);
    }

    public LinkedList<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    public LinkedList<Reservation> getActiveReservations() {
        return reservationService.getActiveReservations();
    }

    public LinkedList<Reservation> getReservationsByPatronId(String patronId) {
        return reservationService.getReservationsByPatronId(patronId);
    }

    public LinkedList<Reservation> getReservationsByBookId(String bookId) {
        return reservationService.getReservationsByBookId(bookId);
    }

    public int getQueuePosition(String bookId, String patronId) {
        return reservationService.getQueuePosition(bookId, patronId);
    }
    
    // SORTING UTILITIES
    public LinkedList<Book> sortBooksByTitle(LinkedList<Book> books) {
        return sortBooks(books, Comparator.comparing(Book::getTitle));
    }
    
    public LinkedList<Book> sortBooksByAuthor(LinkedList<Book> books) {
        return sortBooks(books, Comparator.comparing(Book::getAuthor));
    }
    
    public LinkedList<Book> sortBooksByPublicationDate(LinkedList<Book> books) {
        return sortBooks(books, Comparator.comparing(Book::getPublicationDate));
    }
    
    private LinkedList<Book> sortBooks(LinkedList<Book> books, Comparator<Book> comparator) {
        // Create a new list to avoid modifying the original
        LinkedList<Book> sortedBooks = new LinkedList<>();
        
        // Copy all books to sortedBooks
        for (int i = 0; i < books.size(); i++) {
            sortedBooks.add(books.get(i));
        }
        
        // Bubble sort implementation
        int n = sortedBooks.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Book book1 = sortedBooks.get(j);
                Book book2 = sortedBooks.get(j + 1);
                
                if (comparator.compare(book1, book2) > 0) {
                    // Swap elements by creating a new list
                    LinkedList<Book> tempList = new LinkedList<>();
                    
                    // Add all elements before j
                    for (int k = 0; k < j; k++) {
                        tempList.add(sortedBooks.get(k));
                    }
                    
                    // Add the swapped elements
                    tempList.add(book2);
                    tempList.add(book1);
                    
                    // Add all elements after j+1
                    for (int k = j + 2; k < n; k++) {
                        tempList.add(sortedBooks.get(k));
                    }
                    
                    sortedBooks = tempList;
                }
            }
        }
        
        return sortedBooks;
    }
    
    // SEARCH UTILITIES
    public Book searchBookByExactId(String id) {
        return bookService.findBookById(id);
    }
    
    public Patron searchPatronByExactId(String id) {
        return patronService.findPatronById(id);
    }
    
    public LinkedList<Book> searchBooksByKeyword(String keyword) {
        LinkedList<Book> results = new LinkedList<>();
        LinkedList<Book> allBooks = bookService.getAllBooks();
        
        keyword = keyword.toLowerCase();
        
        for (int i = 0; i < allBooks.size(); i++) {
            Book book = allBooks.get(i);
            if (book.getTitle().toLowerCase().contains(keyword) || 
                book.getAuthor().toLowerCase().contains(keyword) ||
                book.getGenre().toLowerCase().contains(keyword)) {
                results.add(book);
            }
        }
        
        return results;
    }
} 