/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates read-only room search using centralized inventory.
 *
 * @author MRIGANK
 * @version 4.0
 */

import java.util.HashMap;
import java.util.Map;


/* Abstract Room Class */
abstract class Room {

    protected String type;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + type);
        System.out.println("Beds : " + beds);
        System.out.println("Size : " + size + " sq ft");
        System.out.println("Price per night : ₹" + price);
    }

    public String getType() {
        return type;
    }
}


/* Single Room */
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 2500);
    }
}


/* Double Room */
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 4000);
    }
}


/* Suite Room */
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 600, 8000);
    }
}


/* Centralized Inventory */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);   // Example unavailable room
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


/* Search Service */
class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchRooms(Room[] rooms) {

        System.out.println("Available Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getType());

            /* Filter unavailable rooms */
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms : " + available);
                System.out.println("-------------------------------");

            }
        }
    }
}



/* Main Application */
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 4.0 ");
        System.out.println("=================================\n");


        /* Initialize Inventory */
        RoomInventory inventory = new RoomInventory();

        /* Room Objects */
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        /* Search Service */
        RoomSearchService searchService = new RoomSearchService(inventory);

        /* Guest searches for available rooms */
        searchService.searchRooms(rooms);

    }
}
