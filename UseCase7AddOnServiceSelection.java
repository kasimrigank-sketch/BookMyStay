/**
 * Book My Stay App
 * Hotel Booking Management System
 *
 * Demonstrates Add-On Service Selection for reservations.
 *
 * @author MRIGANK
 * @version 7.0
 */

import java.util.*;


/* Add-On Service Class */
class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " : ₹" + cost);
    }
}


/* Add-On Service Manager */
class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    /* Attach service to reservation */
    public void addService(String reservationId, AddOnService service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added to Reservation " + reservationId + ": " + service.getServiceName());
    }

    /* Display services for reservation */
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (AddOnService s : services) {
            s.displayService();
        }
    }

    /* Calculate total cost */
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}



/* Main Application */
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" Book My Stay App ");
        System.out.println(" Hotel Booking Management System ");
        System.out.println(" Version : 7.0 ");
        System.out.println("=================================");

        /* Example Reservation ID */
        String reservationId = "RES-101";

        /* Create service manager */
        AddOnServiceManager manager = new AddOnServiceManager();


        /* Guest selects services */
        manager.addService(reservationId, new AddOnService("Breakfast", 500));
        manager.addService(reservationId, new AddOnService("Airport Pickup", 1200));
        manager.addService(reservationId, new AddOnService("Spa Access", 1500));


        /* Display selected services */
        manager.displayServices(reservationId);


        /* Calculate total add-on cost */
        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost : ₹" + totalCost);
    }
}
