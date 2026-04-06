/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates concurrent booking simulation using threads
 * and synchronized access to shared resources.
 *
 * @author MRIGANK
 * @version 11.0
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


/* Shared Inventory Service */
class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    /* Critical Section */
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName()
                    + " allocated " + roomType
                    + " to " + guestName);

            return true;

        } else {

            System.out.println(Thread.currentThread().getName()
                    + " failed booking for " + guestName
                    + " (No rooms available)");

            return false;
        }
    }

    public void displayInventory() {

        System.out.println("\nFinal Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}


/* Concurrent Booking Processor */
class BookingProcessor implements Runnable {

    private Queue<Reservation> bookingQueue;
    private InventoryService inventory;

    public BookingProcessor(Queue<Reservation> bookingQueue, InventoryService inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            /* Synchronized queue access */
            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty()) {
                    return;
                }

                reservation = bookingQueue.poll();
            }

            inventory.allocateRoom(
                    reservation.getRoomType(),
                    reservation.getGuestName()
            );
        }
    }
}



/* Main Application */
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 11.0 ");
        System.out.println("=================================");


        Queue<Reservation> bookingQueue = new LinkedList<>();

        /* Simulated guest booking requests */
        bookingQueue.add(new Reservation("Rahul", "Single Room"));
        bookingQueue.add(new Reservation("Ananya", "Single Room"));
        bookingQueue.add(new Reservation("Vikram", "Single Room"));
        bookingQueue.add(new Reservation("Priya", "Double Room"));
        bookingQueue.add(new Reservation("Arjun", "Suite Room"));


        InventoryService inventory = new InventoryService();


        /* Multiple booking threads */
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-2");
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, inventory), "Thread-3");


        t1.start();
        t2.start();
        t3.start();


        try {

            t1.join();
            t2.join();
            t3.join();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }


        inventory.displayInventory();
    }
}
