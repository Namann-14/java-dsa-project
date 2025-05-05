package services;

import datastructures.LinkedList;
import models.Book;
import models.Patron;
import models.Transaction;

import java.time.LocalDate;

public class TransactionService {
    private LinkedList<Transaction> transactions;
    private BookService bookService;
    private PatronService patronService;
    private int nextId;

    public TransactionService(BookService bookService, PatronService patronService) {
        this.transactions = new LinkedList<>();
        this.bookService = bookService;
        this.patronService = patronService;
        this.nextId = 1;
    }

    public Transaction borrowBook(String bookId, String patronId, int daysToReturn) {
        Book book = bookService.findBookById(bookId);
        Patron patron = patronService.findPatronById(patronId);

        if (book == null || patron == null) {
            return null;
        }

        if (!book.isAvailable()) {
            return null;
        }

        String id = "T" + String.format("%04d", nextId++);
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(daysToReturn);
        Transaction transaction = new Transaction(id, bookId, patronId, borrowDate, dueDate);
        
        transactions.add(transaction);
        bookService.updateBookAvailability(bookId, false);
        
        return transaction;
    }

    public boolean returnBook(String transactionId) {
        Transaction transaction = findTransactionById(transactionId);
        
        if (transaction == null || transaction.isReturned()) {
            return false;
        }
        
        transaction.setReturnDate(LocalDate.now());
        bookService.updateBookAvailability(transaction.getBookId(), true);
        
        return true;
    }

    public Transaction findTransactionById(String id) {
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }
        return null;
    }

    public LinkedList<Transaction> getAllTransactions() {
        return transactions;
    }

    public LinkedList<Transaction> getActiveTransactions() {
        LinkedList<Transaction> active = new LinkedList<>();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (!transaction.isReturned()) {
                active.add(transaction);
            }
        }
        return active;
    }

    public LinkedList<Transaction> getTransactionsByPatronId(String patronId) {
        LinkedList<Transaction> patronTransactions = new LinkedList<>();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.getPatronId().equals(patronId)) {
                patronTransactions.add(transaction);
            }
        }
        return patronTransactions;
    }

    public LinkedList<Transaction> getTransactionsByBookId(String bookId) {
        LinkedList<Transaction> bookTransactions = new LinkedList<>();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (transaction.getBookId().equals(bookId)) {
                bookTransactions.add(transaction);
            }
        }
        return bookTransactions;
    }

    public LinkedList<Transaction> getOverdueTransactions() {
        LinkedList<Transaction> overdue = new LinkedList<>();
        LocalDate today = LocalDate.now();
        
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (!transaction.isReturned() && transaction.getDueDate().isBefore(today)) {
                overdue.add(transaction);
            }
        }
        return overdue;
    }
} 