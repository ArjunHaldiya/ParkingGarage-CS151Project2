//Gunraj

public class SystemTest {
    public static void main(String[] args) {
        System.out.println("===== SYSTEM TEST START =====");

        // Create parking spot
        ParkingSpot spot = new ParkingSpot("S1");

        // Create vehicles
        Car car = new Car("C1", "ABC123", "Raj", 4, "Gas");
        Motorcycle bike = new Motorcycle("M1", "XYZ999", "Hasham", 300, true);

        // Test parking a car
        car.parkVehicle(spot);
        System.out.println("Car parked: " + car.isParked());
        System.out.println("Spot available after parking car: " + spot.checkAvailability());

        // Create ticket
        Ticket ticket = new Ticket("T1", car, spot, 10);
        ticket.generateTicket();

        // Calculate parking fee
        double fee = ticket.calculateParkingFee(12);
        System.out.println("Calculated fee: $" + fee);

        // Process payment
        PaymentSystem payment = new PaymentSystem("P1", "Card", fee);

        if (payment.validatePayment(fee)) {
            payment.processPayment(fee);
            payment.generateReceipt(fee);
            ticket.markAsPaid();
        }

        // Vehicle leaves spot
        car.leaveSpot(spot);
        System.out.println("Car parked after leaving: " + car.isParked());
        System.out.println("Spot available after leaving: " + spot.checkAvailability());

        // Display info
        System.out.println("\n--- Vehicle Info ---");
        car.displayVehicleInfo();
        bike.displayVehicleInfo();

        System.out.println("\n--- Ticket Info ---");
        ticket.displayTicketDetails();

        System.out.println("\n===== SYSTEM TEST END =====");
    }
}