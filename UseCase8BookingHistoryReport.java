/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates booking history tracking and reporting.
 *
 * @author MRIGANK
 * @version 8.0
 */

import java.util.*;


/* Reservation Class */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println(
                "Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType
        );
    }
}


/* Booking History */
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    /* Add confirmed booking */
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    /* Retrieve all reservations */
    public List<Reservation> getReservations() {
        return history;
    }
}


/* Booking Report Service */
class BookingReportService {

    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    /* Display booking history */
    public void showAllBookings() {

        System.out.println("\nBooking History:");

        for (Reservation r : bookingHistory.getReservations()) {
            r.displayReservation();
        }
    }

    /* Generate summary report */
    public void generateSummaryReport() {

        Map<String, Integer> report = new HashMap<>();

        for (Reservation r : bookingHistory.getReservations()) {

            report.put(
                    r.getRoomType(),
                    report.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\nBooking Summary Report:");

        for (Map.Entry<String, Integer> entry : report.entrySet()) {
            System.out.println(entry.getKey() + " Bookings : " + entry.getValue());
        }
    }
}



/* Main Application */
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 8.0 ");
        System.out.println("=================================");

        /* Initialize booking history */
        BookingHistory history = new BookingHistory();

        /* Confirmed reservations */
        history.addReservation(new Reservation("RES101", "Rahul", "Single Room"));
        history.addReservation(new Reservation("RES102", "Ananya", "Double Room"));
        history.addReservation(new Reservation("RES103", "Vikram", "Suite Room"));
        history.addReservation(new Reservation("RES104", "Priya", "Single Room"));

        /* Admin requests reports */
        BookingReportService reportService = new BookingReportService(history);

        reportService.showAllBookings();

        reportService.generateSummaryReport();
    }
}
