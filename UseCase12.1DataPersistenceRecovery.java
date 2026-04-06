/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates data persistence and system recovery using
 * serialization and deserialization.
 *
 * @author MRIGANK
 * @version 12.0
 */

import java.io.*;
import java.util.*;

/* Reservation Class */
class Reservation implements Serializable {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("ReservationID: " + reservationId +
                " | Guest: " + guestName +
                " | RoomType: " + roomType);
    }
}


/* System State (Inventory + Bookings) */
class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState() {

        inventory = new HashMap<>();
        bookingHistory = new ArrayList<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }
}


/* Persistence Service */
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    /* Save state to file */
    public static void save(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    /* Load state from file */
    public static SystemState load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();

            System.out.println("System state recovered successfully.\n");

            return state;

        } catch (Exception e) {

            System.out.println("No previous state found. Starting fresh system.\n");

            return new SystemState();
        }
    }
}



/* Main Application */
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 12.0 ");
        System.out.println("=================================");


        /* Load saved system state */
        SystemState state = PersistenceService.load();


        /* Display inventory */
        System.out.println("Current Inventory:");

        for (Map.Entry<String, Integer> entry : state.inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }


        /* Simulate new booking */
        Reservation r1 = new Reservation("RES201", "Rahul", "Single Room");
        state.bookingHistory.add(r1);

        System.out.println("\nNew booking added:");
        r1.display();


        /* Display booking history */
        System.out.println("\nBooking History:");

        for (Reservation r : state.bookingHistory) {
            r.display();
        }


        /* Save state before shutdown */
        PersistenceService.save(state);
    }
}
