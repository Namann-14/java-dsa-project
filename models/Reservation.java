package models;

import java.time.LocalDate;

public class Reservation {
    private String id;
    private String bookId;
    private String patronId;
    private LocalDate reservationDate;
    private boolean isActive;

    public Reservation(String id, String bookId, String patronId, LocalDate reservationDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.reservationDate = reservationDate;
        this.isActive = true;
    }

    public String getId() {
        return id;
    }

    public String getBookId() {
        return bookId;
    }

    public String getPatronId() {
        return patronId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation reservation = (Reservation) obj;
        return id.equals(reservation.id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id='" + id + '\'' +
                ", bookId='" + bookId + '\'' +
                ", patronId='" + patronId + '\'' +
                ", reservationDate=" + reservationDate +
                ", isActive=" + isActive +
                '}';
    }
} 