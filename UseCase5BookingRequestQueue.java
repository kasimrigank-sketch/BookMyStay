/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates booking request intake using Queue (FIFO).
 *
 * @author MRIGANK
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;


/* Reservation Class */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest : " + guestName + " | Requested Room : " + roomType);
    }
}


/* Booking Request Queue */
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /* Add booking request */
    public void addRequest(Reservation reservation) {

        requestQueue.add(reservation);

        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    /* Display queued requests */
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }
}



/* Main Application */
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 5.0 ");
        System.out.println("=================================\n");


        /* Initialize booking queue */
        BookingRequestQueue bookingQueue = new BookingRequestQueue();


        /* Guests submit booking requests */
        bookingQueue.addRequest(new Reservation("Rahul", "Single Room"));
        bookingQueue.addRequest(new Reservation("Ananya", "Double Room"));
        bookingQueue.addRequest(new Reservation("Vikram", "Suite Room"));


        /* Display queue (FIFO order preserved) */
        bookingQueue.displayQueue();

        System.out.println("\nRequests are stored in arrival order and waiting for allocation.");

    }
}
