/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates reservation confirmation and safe room allocation.
 *
 * @author MRIGANK
 * @version 6.0
 */

import java.util.*;


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
}


/* Inventory Service */
class InventoryService {

    private HashMap<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* Booking Service */
class BookingService {

    private Queue<Reservation> requestQueue;
    private InventoryService inventory;

    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> allocatedRoomsByType;

    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, InventoryService inventory) {

        this.requestQueue = requestQueue;
        this.inventory = inventory;

        allocatedRoomIds = new HashSet<>();
        allocatedRoomsByType = new HashMap<>();
    }


    /* Process booking requests */
    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();

            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing booking for " + reservation.getGuestName());

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRoomIds.add(roomId);

                allocatedRoomsByType
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decrementRoom(roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Guest : " + reservation.getGuestName());
                System.out.println("Room Type : " + roomType);
                System.out.println("Assigned Room ID : " + roomId);

            } else {

                System.out.println("Reservation Failed for " + reservation.getGuestName());
                System.out.println("No available rooms for type: " + roomType);
            }
        }
    }


    /* Generate unique room ID */
    private String generateRoomId(String roomType) {

        String prefix = roomType.substring(0, 2).toUpperCase();
        String roomId;

        do {
            roomId = prefix + "-" + roomCounter++;
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}



/* Main Application */
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 6.0 ");
        System.out.println("=================================");


        /* Create inventory */
        InventoryService inventory = new InventoryService();


        /* Booking request queue */
        Queue<Reservation> requestQueue = new LinkedList<>();

        requestQueue.add(new Reservation("Rahul", "Single Room"));
        requestQueue.add(new Reservation("Ananya", "Double Room"));
        requestQueue.add(new Reservation("Vikram", "Suite Room"));
        requestQueue.add(new Reservation("Priya", "Suite Room")); // may fail


        /* Booking service */
        BookingService bookingService = new BookingService(requestQueue, inventory);


        /* Process bookings */
        bookingService.processBookings();


        /* Display updated inventory */
        inventory.displayInventory();
    }
}
