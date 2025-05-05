package services;

import datastructures.BinarySearchTree;
import datastructures.LinkedList;
import models.Book;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Consumer;

public class BookService {
    private LinkedList<Book> books;
    private BinarySearchTree<Book> booksByTitle;
    private BinarySearchTree<Book> booksByAuthor;
    private BinarySearchTree<Book> booksById;
    private int nextId;

    public BookService() {
        books = new LinkedList<>();
        booksByTitle = new BinarySearchTree<>(Comparator.comparing(Book::getTitle));
        booksByAuthor = new BinarySearchTree<>(Comparator.comparing(Book::getAuthor));
        booksById = new BinarySearchTree<>(Comparator.comparing(Book::getId));
        nextId = 1;
    }

    public Book addBook(String title, String author, String isbn, LocalDate publicationDate, String genre) {
        String id = "B" + String.format("%04d", nextId++);
        Book book = new Book(id, title, author, isbn, publicationDate, genre);
        books.add(book);
        booksByTitle.insert(book);
        booksByAuthor.insert(book);
        booksById.insert(book);
        return book;
    }

    public boolean removeBook(String id) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId().equals(id)) {
                books.remove(book);
                Book placeholder = new Book(id, "", "", "", LocalDate.now(), "");
                booksByTitle.delete(placeholder);
                booksByAuthor.delete(placeholder);
                booksById.delete(placeholder);
                return true;
            }
        }
        return false;
    }

    public Book findBookById(String id) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public LinkedList<Book> findBooksByTitle(String title) {
        LinkedList<Book> result = new LinkedList<>();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public LinkedList<Book> findBooksByAuthor(String author) {
        LinkedList<Book> result = new LinkedList<>();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public LinkedList<Book> getAllBooks() {
        return books;
    }

    public void displayBooksSortedByTitle(Consumer<Book> displayFunction) {
        booksByTitle.inorderTraversal(displayFunction);
    }

    public void displayBooksSortedByAuthor(Consumer<Book> displayFunction) {
        booksByAuthor.inorderTraversal(displayFunction);
    }

    public void updateBook(Book book) {
        Book existingBook = findBookById(book.getId());
        if (existingBook != null) {
            // Remove from trees
            Book placeholder = new Book(book.getId(), "", "", "", LocalDate.now(), "");
            booksByTitle.delete(placeholder);
            booksByAuthor.delete(placeholder);
            
            // Update the book
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setPublicationDate(book.getPublicationDate());
            existingBook.setGenre(book.getGenre());
            
            // Reinsert into trees
            booksByTitle.insert(existingBook);
            booksByAuthor.insert(existingBook);
        }
    }

    public void updateBookAvailability(String bookId, boolean available) {
        Book book = findBookById(bookId);
        if (book != null) {
            book.setAvailable(available);
        }
    }
} 