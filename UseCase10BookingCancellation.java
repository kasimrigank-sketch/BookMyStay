/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates booking cancellation with inventory rollback.
 *
 * @author MRIGANK
 * @version 10.0
 */

import java.util.*;


/* Reservation Class */
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayReservation() {
        System.out.println(
                "ReservationID: " + reservationId +
                " | Guest: " + guestName +
                " | RoomType: " + roomType +
                " | RoomID: " + roomId
        );
    }
}


/* Inventory Service */
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* Booking History */
class BookingHistory {

    private Map<String, Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new HashMap<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedBookings.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return confirmedBookings.get(reservationId);
    }

    public void removeReservation(String reservationId) {
        confirmedBookings.remove(reservationId);
    }

    public void displayBookings() {

        System.out.println("\nConfirmed Bookings:");

        for (Reservation r : confirmedBookings.values()) {
            r.displayReservation();
        }
    }
}


/* Cancellation Service */
class CancellationService {

    private BookingHistory bookingHistory;
    private InventoryService inventory;

    private Stack<String> rollbackStack;

    public CancellationService(BookingHistory bookingHistory, InventoryService inventory) {

        this.bookingHistory = bookingHistory;
        this.inventory = inventory;

        rollbackStack = new Stack<>();
    }

    public void cancelReservation(String reservationId) {

        Reservation reservation = bookingHistory.getReservation(reservationId);

        if (reservation == null) {

            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        rollbackStack.push(reservation.getRoomId());

        inventory.incrementRoom(reservation.getRoomType());

        bookingHistory.removeReservation(reservationId);

        System.out.println("Reservation " + reservationId + " cancelled successfully.");
        System.out.println("Released Room ID: " + reservation.getRoomId());
    }

    public void showRollbackStack() {

        System.out.println("\nRollback Stack (Recently Released Rooms):");

        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}



/* Main Application */
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 10.0 ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();

        BookingHistory history = new BookingHistory();

        /* Confirmed bookings */
        history.addReservation(new Reservation("RES101", "Rahul", "Single Room", "SR-1"));
        history.addReservation(new Reservation("RES102", "Ananya", "Double Room", "DR-1"));

        history.displayBookings();

        CancellationService cancellationService =
                new CancellationService(history, inventory);

        /* Guest cancels booking */
        cancellationService.cancelReservation("RES101");

        /* Invalid cancellation attempt */
        cancellationService.cancelReservation("RES999");

        history.displayBookings();

        inventory.displayInventory();

        cancellationService.showRollbackStack();
    }
}
