package services;

import datastructures.LinkedList;
import datastructures.Queue;
import models.Book;
import models.Patron;
import models.Reservation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ReservationService {
    private LinkedList<Reservation> reservations;
    private Map<String, Queue<Reservation>> reservationQueues; // Book ID -> Queue of reservations
    private BookService bookService;
    private PatronService patronService;
    private int nextId;

    public ReservationService(BookService bookService, PatronService patronService) {
        this.reservations = new LinkedList<>();
        this.reservationQueues = new HashMap<>();
        this.bookService = bookService;
        this.patronService = patronService;
        this.nextId = 1;
    }

    public Reservation reserveBook(String bookId, String patronId) {
        Book book = bookService.findBookById(bookId);
        Patron patron = patronService.findPatronById(patronId);

        if (book == null || patron == null) {
            return null;
        }

        // Check if patron already has a reservation for this book
        for (int i = 0; i < reservations.size(); i++) {
            Reservation existing = reservations.get(i);
            if (existing.isActive() && existing.getBookId().equals(bookId) && existing.getPatronId().equals(patronId)) {
                return existing; // Already reserved
            }
        }

        String id = "R" + String.format("%04d", nextId++);
        Reservation reservation = new Reservation(id, bookId, patronId, LocalDate.now());
        reservations.add(reservation);

        // Add to the queue for this book
        if (!reservationQueues.containsKey(bookId)) {
            reservationQueues.put(bookId, new Queue<>());
        }
        reservationQueues.get(bookId).enqueue(reservation);

        return reservation;
    }

    public boolean cancelReservation(String reservationId) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation == null || !reservation.isActive()) {
            return false;
        }

        reservation.setActive(false);
        
        // Remove from queue (we'll need to rebuild it)
        String bookId = reservation.getBookId();
        if (reservationQueues.containsKey(bookId)) {
            Queue<Reservation> oldQueue = reservationQueues.get(bookId);
            Queue<Reservation> newQueue = new Queue<>();
            
            while (!oldQueue.isEmpty()) {
                Reservation r = oldQueue.dequeue();
                if (!r.getId().equals(reservationId) && r.isActive()) {
                    newQueue.enqueue(r);
                }
            }
            reservationQueues.put(bookId, newQueue);
        }
        
        return true;
    }

    public Reservation getNextReservation(String bookId) {
        if (!reservationQueues.containsKey(bookId) || reservationQueues.get(bookId).isEmpty()) {
            return null;
        }
        return reservationQueues.get(bookId).peek();
    }

    public boolean fulfillReservation(String reservationId) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation == null || !reservation.isActive()) {
            return false;
        }

        Book book = bookService.findBookById(reservation.getBookId());
        if (book == null || !book.isAvailable()) {
            return false;
        }

        // Cancel the reservation
        reservation.setActive(false);
        
        // Remove from queue
        String bookId = reservation.getBookId();
        if (reservationQueues.containsKey(bookId) && !reservationQueues.get(bookId).isEmpty()) {
            reservationQueues.get(bookId).dequeue();
        }
        
        return true;
    }

    public Reservation findReservationById(String id) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }
        return null;
    }

    public LinkedList<Reservation> getAllReservations() {
        return reservations;
    }

    public LinkedList<Reservation> getActiveReservations() {
        LinkedList<Reservation> active = new LinkedList<>();
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            if (reservation.isActive()) {
                active.add(reservation);
            }
        }
        return active;
    }

    public LinkedList<Reservation> getReservationsByPatronId(String patronId) {
        LinkedList<Reservation> patronReservations = new LinkedList<>();
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            if (reservation.getPatronId().equals(patronId) && reservation.isActive()) {
                patronReservations.add(reservation);
            }
        }
        return patronReservations;
    }

    public LinkedList<Reservation> getReservationsByBookId(String bookId) {
        LinkedList<Reservation> bookReservations = new LinkedList<>();
        if (reservationQueues.containsKey(bookId)) {
            Queue<Reservation> queue = reservationQueues.get(bookId);
            // We need to copy the queue to not modify it
            Queue<Reservation> tempQueue = new Queue<>();
            
            while (!queue.isEmpty()) {
                Reservation r = queue.dequeue();
                bookReservations.add(r);
                tempQueue.enqueue(r);
            }
            
            // Restore the queue
            reservationQueues.put(bookId, tempQueue);
        }
        return bookReservations;
    }

    public int getQueuePosition(String bookId, String patronId) {
        if (!reservationQueues.containsKey(bookId)) {
            return -1;
        }
        
        Queue<Reservation> queue = reservationQueues.get(bookId);
        Queue<Reservation> tempQueue = new Queue<>();
        int position = 1;
        boolean found = false;
        
        while (!queue.isEmpty()) {
            Reservation r = queue.dequeue();
            if (r.getPatronId().equals(patronId) && r.isActive()) {
                found = true;
                break;
            }
            tempQueue.enqueue(r);
            position++;
        }
        
        // Restore the remaining items
        while (!queue.isEmpty()) {
            tempQueue.enqueue(queue.dequeue());
        }
        
        // Restore the queue
        reservationQueues.put(bookId, tempQueue);
        
        return found ? position : -1;
    }
} 