/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates centralized inventory management using HashMap.
 *
 * @author MRIGANK
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;


/* Inventory Class */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    /* Constructor initializes room availability */
    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    /* Method to get availability */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /* Method to update availability */
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    /* Method to display inventory */
    public void displayInventory() {

        System.out.println("Current Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}



/* Main Application Class */
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 3.1 ");
        System.out.println("=================================\n");


        /* Initialize Inventory */
        RoomInventory inventory = new RoomInventory();


        /* Display Inventory */
        inventory.displayInventory();


        /* Example update */
        System.out.println("\nUpdating Single Room availability...");

        inventory.updateAvailability("Single Room", 4);


        /* Display Updated Inventory */
        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

    }
}
