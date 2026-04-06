/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates error handling and validation for booking input.
 *
 * @author MRIGANK
 * @version 9.0
 */

import java.util.HashMap;
import java.util.Map;


/* Custom Exception */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


/* Inventory Service */
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public boolean isValidRoomType(String roomType) {
        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) throws InvalidBookingException {

        int available = getAvailability(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No available rooms for type: " + roomType);
        }

        inventory.put(roomType, available - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* Booking Validator */
class InvalidBookingValidator {

    public static void validate(String guestName, String roomType, InventoryService inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected: " + roomType);
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("Selected room type is currently unavailable.");
        }
    }
}



/* Booking Service */
class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String guestName, String roomType) {

        try {

            InvalidBookingValidator.validate(guestName, roomType, inventory);

            inventory.decrementRoom(roomType);

            System.out.println("Reservation confirmed for " + guestName +
                    " | Room Type: " + roomType);

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}



/* Main Application */
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 9.0 ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);


        /* Valid booking */
        bookingService.bookRoom("Rahul", "Single Room");

        /* Invalid room type */
        bookingService.bookRoom("Ananya", "Luxury Room");

        /* Empty guest name */
        bookingService.bookRoom("", "Double Room");

        /* Overbooking attempt */
        bookingService.bookRoom("Vikram", "Suite Room");
        bookingService.bookRoom("Priya", "Suite Room");

        inventory.displayInventory();
    }
}
