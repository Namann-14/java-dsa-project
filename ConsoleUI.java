import models.Book;
import models.Patron;
import models.Reservation;
import models.Transaction;
import datastructures.LinkedList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsoleUI {
    private LibraryManager libraryManager;
    private Scanner scanner;
    private boolean running;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ConsoleUI(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        running = true;
        displayWelcomeMessage();
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    bookManagementMenu();
                    break;
                case 2:
                    patronManagementMenu();
                    break;
                case 3:
                    transactionManagementMenu();
                    break;
                case 4:
                    reservationManagementMenu();
                    break;
                case 5:
                    searchMenu();
                    break;
                case 0:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayWelcomeMessage() {
        System.out.println("===================================");
        System.out.println("| LIBRARY MANAGEMENT SYSTEM |");
        System.out.println("===================================");
        System.out.println("A console-based Java project that incorporates");
        System.out.println("data structures and algorithms with CRUD operations");
        System.out.println("===================================\n");
    }

    private void displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Book Management");
        System.out.println("2. Patron Management");
        System.out.println("3. Transaction Management");
        System.out.println("4. Reservation Management");
        System.out.println("5. Search");
        System.out.println("0. Exit");
    }

    // BOOK MANAGEMENT MENU
    private void bookManagementMenu() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== BOOK MANAGEMENT ===");
            System.out.println("1. Add New Book");
            System.out.println("2. View All Books");
            System.out.println("3. View Book Details");
            System.out.println("4. Update Book");
            System.out.println("5. Remove Book");
            System.out.println("6. View Books Sorted by Title");
            System.out.println("7. View Books Sorted by Author");
            System.out.println("8. View Books Sorted by Publication Date");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    viewBookDetails();
                    break;
                case 4:
                    updateBook();
                    break;
                case 5:
                    removeBook();
                    break;
                case 6:
                    viewBooksSortedByTitle();
                    break;
                case 7:
                    viewBooksSortedByAuthor();
                    break;
                case 8:
                    viewBooksSortedByPublicationDate();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addBook() {
        System.out.println("\n=== ADD NEW BOOK ===");
        
        String title = getStringInput("Enter title: ");
        String author = getStringInput("Enter author: ");
        String isbn = getStringInput("Enter ISBN: ");
        String publicationDate = getStringInput("Enter publication date (yyyy-MM-dd): ");
        String genre = getStringInput("Enter genre: ");
        
        Book book = libraryManager.addBook(title, author, isbn, publicationDate, genre);
        
        if (book != null) {
            System.out.println("Book added successfully with ID: " + book.getId());
        } else {
            System.out.println("Failed to add book. Please check your inputs.");
        }
    }

    private void viewAllBooks() {
        System.out.println("\n=== ALL BOOKS ===");
        
        LinkedList<Book> books = libraryManager.getAllBooks();
        
        if (books.isEmpty()) {
            System.out.println("No books found in the library.");
        } else {
            displayBookList(books);
        }
    }

    private void viewBookDetails() {
        System.out.println("\n=== VIEW BOOK DETAILS ===");
        
        String bookId = getStringInput("Enter book ID: ");
        Book book = libraryManager.findBookById(bookId);
        
        if (book != null) {
            displayBook(book);
        } else {
            System.out.println("Book not found with ID: " + bookId);
        }
    }

    private void updateBook() {
        System.out.println("\n=== UPDATE BOOK ===");
        
        String bookId = getStringInput("Enter book ID: ");
        Book book = libraryManager.findBookById(bookId);
        
        if (book != null) {
            System.out.println("Current book details:");
            displayBook(book);
            System.out.println("\nEnter new details (leave blank to keep current value):");
            
            String title = getStringInput("Enter new title: ");
            String author = getStringInput("Enter new author: ");
            String isbn = getStringInput("Enter new ISBN: ");
            String publicationDate = getStringInput("Enter new publication date (yyyy-MM-dd): ");
            String genre = getStringInput("Enter new genre: ");
            
            libraryManager.updateBook(bookId, title, author, isbn, publicationDate, genre);
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book not found with ID: " + bookId);
        }
    }

    private void removeBook() {
        System.out.println("\n=== REMOVE BOOK ===");
        
        String bookId = getStringInput("Enter book ID: ");
        
        boolean removed = libraryManager.removeBook(bookId);
        
        if (removed) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found with ID: " + bookId);
        }
    }

    private void viewBooksSortedByTitle() {
        System.out.println("\n=== BOOKS SORTED BY TITLE ===");
        
        LinkedList<Book> books = libraryManager.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found in the library.");
            return;
        }
        
        LinkedList<Book> sortedBooks = libraryManager.sortBooksByTitle(books);
        displayBookList(sortedBooks);
    }

    private void viewBooksSortedByAuthor() {
        System.out.println("\n=== BOOKS SORTED BY AUTHOR ===");
        
        LinkedList<Book> books = libraryManager.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found in the library.");
            return;
        }
        
        LinkedList<Book> sortedBooks = libraryManager.sortBooksByAuthor(books);
        displayBookList(sortedBooks);
    }

    private void viewBooksSortedByPublicationDate() {
        System.out.println("\n=== BOOKS SORTED BY PUBLICATION DATE ===");
        
        LinkedList<Book> books = libraryManager.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books found in the library.");
            return;
        }
        
        LinkedList<Book> sortedBooks = libraryManager.sortBooksByPublicationDate(books);
        displayBookList(sortedBooks);
    }

    // PATRON MANAGEMENT MENU
    private void patronManagementMenu() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== PATRON MANAGEMENT ===");
            System.out.println("1. Register New Patron");
            System.out.println("2. View All Patrons");
            System.out.println("3. View Patron Details");
            System.out.println("4. Update Patron Information");
            System.out.println("5. Remove Patron");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    registerPatron();
                    break;
                case 2:
                    viewAllPatrons();
                    break;
                case 3:
                    viewPatronDetails();
                    break;
                case 4:
                    updatePatron();
                    break;
                case 5:
                    removePatron();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerPatron() {
        System.out.println("\n=== REGISTER NEW PATRON ===");
        
        String name = getStringInput("Enter name: ");
        String contactInfo = getStringInput("Enter contact info (email/phone): ");
        String address = getStringInput("Enter address: ");
        String membershipDate = LocalDate.now().format(DATE_FORMATTER);
        
        Patron patron = libraryManager.addPatron(name, contactInfo, address, membershipDate);
        
        System.out.println("Patron registered successfully with ID: " + patron.getId());
    }

    private void viewAllPatrons() {
        System.out.println("\n=== ALL PATRONS ===");
        
        LinkedList<Patron> patrons = libraryManager.getAllPatrons();
        
        if (patrons.isEmpty()) {
            System.out.println("No patrons found in the library.");
        } else {
            displayPatronList(patrons);
        }
    }

    private void viewPatronDetails() {
        System.out.println("\n=== VIEW PATRON DETAILS ===");
        
        String patronId = getStringInput("Enter patron ID: ");
        Patron patron = libraryManager.findPatronById(patronId);
        
        if (patron != null) {
            displayPatron(patron);
            
            // Show borrowing history
            System.out.println("\nBorrowing History:");
            LinkedList<Transaction> transactions = libraryManager.getTransactionsByPatronId(patronId);
            
            if (transactions.isEmpty()) {
                System.out.println("No borrowing history found.");
            } else {
                for (int i = 0; i < transactions.size(); i++) {
                    Transaction transaction = transactions.get(i);
                    Book book = libraryManager.findBookById(transaction.getBookId());
                    System.out.println((i + 1) + ". " + book.getTitle() + 
                            " (Borrowed: " + transaction.getBorrowDate() + 
                            ", Due: " + transaction.getDueDate() + 
                            ", Returned: " + (transaction.isReturned() ? transaction.getReturnDate() : "Not yet") + ")");
                }
            }
            
            // Show active reservations
            System.out.println("\nActive Reservations:");
            LinkedList<Reservation> reservations = libraryManager.getReservationsByPatronId(patronId);
            
            if (reservations.isEmpty()) {
                System.out.println("No active reservations found.");
            } else {
                for (int i = 0; i < reservations.size(); i++) {
                    Reservation reservation = reservations.get(i);
                    Book book = libraryManager.findBookById(reservation.getBookId());
                    System.out.println((i + 1) + ". " + book.getTitle() + 
                            " (Reserved on: " + reservation.getReservationDate() + 
                            ", Queue Position: " + libraryManager.getQueuePosition(book.getId(), patronId) + ")");
                }
            }
        } else {
            System.out.println("Patron not found with ID: " + patronId);
        }
    }

    private void updatePatron() {
        System.out.println("\n=== UPDATE PATRON ===");
        
        String patronId = getStringInput("Enter patron ID: ");
        Patron patron = libraryManager.findPatronById(patronId);
        
        if (patron != null) {
            System.out.println("Current patron details:");
            displayPatron(patron);
            System.out.println("\nEnter new details (leave blank to keep current value):");
            
            String name = getStringInput("Enter new name: ");
            String contactInfo = getStringInput("Enter new contact info: ");
            String address = getStringInput("Enter new address: ");
            
            libraryManager.updatePatron(patronId, name, contactInfo, address, "");
            System.out.println("Patron updated successfully.");
        } else {
            System.out.println("Patron not found with ID: " + patronId);
        }
    }

    private void removePatron() {
        System.out.println("\n=== REMOVE PATRON ===");
        
        String patronId = getStringInput("Enter patron ID: ");
        
        boolean removed = libraryManager.removePatron(patronId);
        
        if (removed) {
            System.out.println("Patron removed successfully.");
        } else {
            System.out.println("Patron not found or could not be removed.");
        }
    }

    // TRANSACTION MANAGEMENT MENU
    private void transactionManagementMenu() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== TRANSACTION MANAGEMENT ===");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. View All Transactions");
            System.out.println("4. View Active Borrows");
            System.out.println("5. View Overdue Books");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    viewAllTransactions();
                    break;
                case 4:
                    viewActiveBorrows();
                    break;
                case 5:
                    viewOverdueBooks();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void borrowBook() {
        System.out.println("\n=== BORROW BOOK ===");
        
        String bookId = getStringInput("Enter book ID: ");
        Book book = libraryManager.findBookById(bookId);
        
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }
        
        if (!book.isAvailable()) {
            System.out.println("This book is currently not available for borrowing.");
            
            // Show the patron if there's a reservation queue
            Reservation nextReservation = libraryManager.getNextReservation(bookId);
            if (nextReservation != null) {
                Patron reservedBy = libraryManager.findPatronById(nextReservation.getPatronId());
                System.out.println("This book is reserved by: " + reservedBy.getName());
            }
            
            // Ask if they want to reserve the book
            String reserveChoice = getStringInput("Would you like to place a reservation for this book? (y/n): ");
            if (reserveChoice.equalsIgnoreCase("y")) {
                String patronId = getStringInput("Enter patron ID for reservation: ");
                Patron patron = libraryManager.findPatronById(patronId);
                
                if (patron != null) {
                    Reservation reservation = libraryManager.reserveBook(bookId, patronId);
                    if (reservation != null) {
                        System.out.println("Book reserved successfully. Reservation ID: " + reservation.getId());
                        System.out.println("Queue position: " + libraryManager.getQueuePosition(bookId, patronId));
                    } else {
                        System.out.println("Failed to reserve book.");
                    }
                } else {
                    System.out.println("Patron not found with ID: " + patronId);
                }
            }
            return;
        }
        
        String patronId = getStringInput("Enter patron ID: ");
        Patron patron = libraryManager.findPatronById(patronId);
        
        if (patron == null) {
            System.out.println("Patron not found with ID: " + patronId);
            return;
        }
        
        int daysToReturn = getIntInput("Enter number of days to borrow (max 30): ");
        if (daysToReturn <= 0 || daysToReturn > 30) {
            System.out.println("Invalid number of days. Please enter a value between 1 and 30.");
            return;
        }
        
        Transaction transaction = libraryManager.borrowBook(bookId, patronId, daysToReturn);
        
        if (transaction != null) {
            System.out.println("Book borrowed successfully. Transaction ID: " + transaction.getId());
            System.out.println("Due date: " + transaction.getDueDate());
        } else {
            System.out.println("Failed to borrow book.");
        }
    }

    private void returnBook() {
        System.out.println("\n=== RETURN BOOK ===");
        
        // Option 1: Return by transaction ID
        System.out.println("1. Return by Transaction ID");
        System.out.println("2. Return by Book ID and Patron ID");
        int returnMethod = getIntInput("Choose return method: ");
        
        boolean returned = false;
        String bookId = "";
        
        if (returnMethod == 1) {
            String transactionId = getStringInput("Enter transaction ID: ");
            returned = libraryManager.returnBook(transactionId);
            if (returned) {
                Transaction transaction = libraryManager.findTransactionById(transactionId);
                bookId = transaction.getBookId();
            }
        } else if (returnMethod == 2) {
            bookId = getStringInput("Enter book ID: ");
            String patronId = getStringInput("Enter patron ID: ");
            
            LinkedList<Transaction> patronTransactions = libraryManager.getTransactionsByPatronId(patronId);
            Transaction targetTransaction = null;
            
            for (int i = 0; i < patronTransactions.size(); i++) {
                Transaction transaction = patronTransactions.get(i);
                if (!transaction.isReturned() && transaction.getBookId().equals(bookId)) {
                    targetTransaction = transaction;
                    break;
                }
            }
            
            if (targetTransaction != null) {
                returned = libraryManager.returnBook(targetTransaction.getId());
            } else {
                System.out.println("No active borrow found for this book and patron.");
            }
        } else {
            System.out.println("Invalid choice.");
            return;
        }
        
        if (returned) {
            System.out.println("Book returned successfully.");
            
            // Check if there are any reservations for this book
            Reservation nextReservation = libraryManager.getNextReservation(bookId);
            if (nextReservation != null) {
                Patron reservedBy = libraryManager.findPatronById(nextReservation.getPatronId());
                System.out.println("This book is reserved by: " + reservedBy.getName());
                System.out.println("Reservation ID: " + nextReservation.getId());
                
                String fulfillChoice = getStringInput("Do you want to fulfill this reservation now? (y/n): ");
                if (fulfillChoice.equalsIgnoreCase("y")) {
                    boolean fulfilled = libraryManager.fulfillReservation(nextReservation.getId());
                    if (fulfilled) {
                        System.out.println("Reservation marked as fulfilled.");
                    } else {
                        System.out.println("Failed to fulfill reservation.");
                    }
                }
            }
        } else {
            System.out.println("Book return failed. Please check the transaction ID or book/patron IDs.");
        }
    }

    private void viewAllTransactions() {
        System.out.println("\n=== ALL TRANSACTIONS ===");
        
        LinkedList<Transaction> transactions = libraryManager.getAllTransactions();
        
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            displayTransactionList(transactions);
        }
    }

    private void viewActiveBorrows() {
        System.out.println("\n=== ACTIVE BORROWS ===");
        
        LinkedList<Transaction> activeTransactions = libraryManager.getActiveTransactions();
        
        if (activeTransactions.isEmpty()) {
            System.out.println("No active borrows found.");
        } else {
            displayTransactionList(activeTransactions);
        }
    }

    private void viewOverdueBooks() {
        System.out.println("\n=== OVERDUE BOOKS ===");
        
        LinkedList<Transaction> overdueTransactions = libraryManager.getOverdueTransactions();
        
        if (overdueTransactions.isEmpty()) {
            System.out.println("No overdue books found.");
        } else {
            System.out.println("The following books are overdue:");
            displayTransactionList(overdueTransactions);
        }
    }

    // RESERVATION MANAGEMENT MENU
    private void reservationManagementMenu() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== RESERVATION MANAGEMENT ===");
            System.out.println("1. Reserve Book");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. View All Reservations");
            System.out.println("4. View Active Reservations");
            System.out.println("5. Check Reservation Queue for Book");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    reserveBook();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 3:
                    viewAllReservations();
                    break;
                case 4:
                    viewActiveReservations();
                    break;
                case 5:
                    checkReservationQueue();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void reserveBook() {
        System.out.println("\n=== RESERVE BOOK ===");
        
        String bookId = getStringInput("Enter book ID: ");
        Book book = libraryManager.findBookById(bookId);
        
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }
        
        if (book.isAvailable()) {
            System.out.println("This book is currently available for borrowing. Would you like to borrow it instead?");
            String borrowChoice = getStringInput("Borrow instead of reserve? (y/n): ");
            
            if (borrowChoice.equalsIgnoreCase("y")) {
                String patronId = getStringInput("Enter patron ID: ");
                Patron patron = libraryManager.findPatronById(patronId);
                
                if (patron == null) {
                    System.out.println("Patron not found with ID: " + patronId);
                    return;
                }
                
                int daysToReturn = getIntInput("Enter number of days to borrow (max 30): ");
                if (daysToReturn <= 0 || daysToReturn > 30) {
                    System.out.println("Invalid number of days. Please enter a value between 1 and 30.");
                    return;
                }
                
                Transaction transaction = libraryManager.borrowBook(bookId, patronId, daysToReturn);
                
                if (transaction != null) {
                    System.out.println("Book borrowed successfully. Transaction ID: " + transaction.getId());
                    System.out.println("Due date: " + transaction.getDueDate());
                } else {
                    System.out.println("Failed to borrow book.");
                }
                return;
            }
        }
        
        String patronId = getStringInput("Enter patron ID: ");
        Patron patron = libraryManager.findPatronById(patronId);
        
        if (patron == null) {
            System.out.println("Patron not found with ID: " + patronId);
            return;
        }
        
        Reservation reservation = libraryManager.reserveBook(bookId, patronId);
        
        if (reservation != null) {
            System.out.println("Book reserved successfully. Reservation ID: " + reservation.getId());
            int queuePosition = libraryManager.getQueuePosition(bookId, patronId);
            System.out.println("Queue position: " + queuePosition);
        } else {
            System.out.println("Failed to reserve book.");
        }
    }

    private void cancelReservation() {
        System.out.println("\n=== CANCEL RESERVATION ===");
        
        String reservationId = getStringInput("Enter reservation ID: ");
        
        boolean cancelled = libraryManager.cancelReservation(reservationId);
        
        if (cancelled) {
            System.out.println("Reservation cancelled successfully.");
        } else {
            System.out.println("Reservation not found or already fulfilled.");
        }
    }

    private void viewAllReservations() {
        System.out.println("\n=== ALL RESERVATIONS ===");
        
        LinkedList<Reservation> reservations = libraryManager.getAllReservations();
        
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            displayReservationList(reservations);
        }
    }

    private void viewActiveReservations() {
        System.out.println("\n=== ACTIVE RESERVATIONS ===");
        
        LinkedList<Reservation> activeReservations = libraryManager.getActiveReservations();
        
        if (activeReservations.isEmpty()) {
            System.out.println("No active reservations found.");
        } else {
            displayReservationList(activeReservations);
        }
    }

    private void checkReservationQueue() {
        System.out.println("\n=== CHECK RESERVATION QUEUE ===");
        
        String bookId = getStringInput("Enter book ID: ");
        Book book = libraryManager.findBookById(bookId);
        
        if (book == null) {
            System.out.println("Book not found with ID: " + bookId);
            return;
        }
        
        LinkedList<Reservation> reservations = libraryManager.getReservationsByBookId(bookId);
        
        if (reservations.isEmpty()) {
            System.out.println("No reservations found for this book.");
        } else {
            System.out.println("Reservation queue for \"" + book.getTitle() + "\":");
            
            for (int i = 0; i < reservations.size(); i++) {
                Reservation reservation = reservations.get(i);
                Patron patron = libraryManager.findPatronById(reservation.getPatronId());
                
                System.out.println((i + 1) + ". " + patron.getName() + 
                        " (Reserved on: " + reservation.getReservationDate() + ")");
            }
        }
    }

    // SEARCH MENU
    private void searchMenu() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== SEARCH ===");
            System.out.println("1. Search Books by Title");
            System.out.println("2. Search Books by Author");
            System.out.println("3. Search Books by Keyword");
            System.out.println("4. Search Patrons by Name");
            System.out.println("0. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    searchBooksByTitle();
                    break;
                case 2:
                    searchBooksByAuthor();
                    break;
                case 3:
                    searchBooksByKeyword();
                    break;
                case 4:
                    searchPatronsByName();
                    break;
                case 0:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchBooksByTitle() {
        System.out.println("\n=== SEARCH BOOKS BY TITLE ===");
        
        String title = getStringInput("Enter title to search: ");
        
        LinkedList<Book> results = libraryManager.findBooksByTitle(title);
        
        if (results.isEmpty()) {
            System.out.println("No books found with title containing: " + title);
        } else {
            System.out.println("Found " + results.size() + " books:");
            displayBookList(results);
        }
    }

    private void searchBooksByAuthor() {
        System.out.println("\n=== SEARCH BOOKS BY AUTHOR ===");
        
        String author = getStringInput("Enter author to search: ");
        
        LinkedList<Book> results = libraryManager.findBooksByAuthor(author);
        
        if (results.isEmpty()) {
            System.out.println("No books found with author containing: " + author);
        } else {
            System.out.println("Found " + results.size() + " books:");
            displayBookList(results);
        }
    }

    private void searchBooksByKeyword() {
        System.out.println("\n=== SEARCH BOOKS BY KEYWORD ===");
        
        String keyword = getStringInput("Enter keyword to search: ");
        
        LinkedList<Book> results = libraryManager.searchBooksByKeyword(keyword);
        
        if (results.isEmpty()) {
            System.out.println("No books found with keyword: " + keyword);
        } else {
            System.out.println("Found " + results.size() + " books:");
            displayBookList(results);
        }
    }

    private void searchPatronsByName() {
        System.out.println("\n=== SEARCH PATRONS BY NAME ===");
        
        String name = getStringInput("Enter name to search: ");
        
        LinkedList<Patron> results = libraryManager.findPatronsByName(name);
        
        if (results.isEmpty()) {
            System.out.println("No patrons found with name containing: " + name);
        } else {
            System.out.println("Found " + results.size() + " patrons:");
            displayPatronList(results);
        }
    }

    // HELPER METHODS FOR DISPLAYING ENTITIES
    private void displayBookList(LinkedList<Book> books) {
        System.out.println("\nID\t| Title\t| Author\t| Available");
        System.out.println("----------------------------------------------------");
        
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book.getId() + "\t| " + 
                    truncateString(book.getTitle(), 20) + "\t| " + 
                    truncateString(book.getAuthor(), 20) + "\t| " + 
                    (book.isAvailable() ? "Yes" : "No"));
        }
    }

    private void displayBook(Book book) {
        System.out.println("ID: " + book.getId());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Publication Date: " + book.getPublicationDate());
        System.out.println("Genre: " + book.getGenre());
        System.out.println("Available: " + (book.isAvailable() ? "Yes" : "No"));
    }

    private void displayPatronList(LinkedList<Patron> patrons) {
        System.out.println("\nID\t| Name\t| Contact Info");
        System.out.println("----------------------------------------------------");
        
        for (int i = 0; i < patrons.size(); i++) {
            Patron patron = patrons.get(i);
            System.out.println(patron.getId() + "\t| " + 
                    truncateString(patron.getName(), 20) + "\t| " + 
                    truncateString(patron.getContactInfo(), 30));
        }
    }

    private void displayPatron(Patron patron) {
        System.out.println("ID: " + patron.getId());
        System.out.println("Name: " + patron.getName());
        System.out.println("Contact Info: " + patron.getContactInfo());
        System.out.println("Address: " + patron.getAddress());
        System.out.println("Membership Date: " + patron.getMembershipDate());
    }

    private void displayTransactionList(LinkedList<Transaction> transactions) {
        System.out.println("\nID\t| Book\t| Patron\t| Borrow Date\t| Due Date\t| Returned");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            Book book = libraryManager.findBookById(transaction.getBookId());
            Patron patron = libraryManager.findPatronById(transaction.getPatronId());
            
            System.out.println(transaction.getId() + "\t| " + 
                    truncateString(book.getTitle(), 20) + "\t| " + 
                    truncateString(patron.getName(), 20) + "\t| " + 
                    transaction.getBorrowDate() + "\t| " + 
                    transaction.getDueDate() + "\t| " + 
                    (transaction.isReturned() ? transaction.getReturnDate() : "No"));
        }
    }

    private void displayReservationList(LinkedList<Reservation> reservations) {
        System.out.println("\nID\t| Book\t| Patron\t| Reservation Date\t| Active");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            Book book = libraryManager.findBookById(reservation.getBookId());
            Patron patron = libraryManager.findPatronById(reservation.getPatronId());
            
            System.out.println(reservation.getId() + "\t| " + 
                    truncateString(book.getTitle(), 20) + "\t| " + 
                    truncateString(patron.getName(), 20) + "\t| " + 
                    reservation.getReservationDate() + "\t| " + 
                    (reservation.isActive() ? "Yes" : "No"));
        }
    }

    // UTILITY METHODS
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    private void exit() {
        System.out.println("\nThank you for using the Library Management System!");
        running = false;
        scanner.close();
    }
} 