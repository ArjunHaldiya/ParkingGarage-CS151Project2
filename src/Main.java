import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();
        ArrayList<ParkingSpot> spots = new ArrayList<>();

        // preloaded parking spots
        spots.add(new ParkingSpot("S1", "A1"));
        spots.add(new ParkingSpot("S2", "A2"));
        spots.add(new ParkingSpot("S3", "A3"));

        boolean running = true;
        int ticketCounter = 1;

        while (running) {
            System.out.println("\n===== Parking Garage System =====");
            System.out.println("1. Add Car");
            System.out.println("2. Add Motorcycle");
            System.out.println("3. Add Truck");
            System.out.println("4. Add Electric Vehicle");
            System.out.println("5. View Available Spots");
            System.out.println("6. Park Vehicle");
            System.out.println("7. Calculate Fee and Pay");
            System.out.println("8. View Tickets");
            System.out.println("9. View Garage Status");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            String choice = getInput(scanner);

            switch (choice) {
                case "1":
                    if (vehicles.size() >= 100) { System.out.println("Max vehicles reached."); break; }
                    System.out.print("Enter vehicle ID: ");
                    String carId = getInput(scanner);
                    System.out.print("Enter license plate: ");
                    String carPlate = getInput(scanner);
                    System.out.print("Enter owner name: ");
                    String carOwner = getInput(scanner);
                    System.out.print("Enter number of doors: ");
                    int doors = getInt(scanner);
                    System.out.print("Enter fuel type: ");
                    String fuelType = getInput(scanner);
                    vehicles.add(new Car(carId, carPlate, carOwner, doors, fuelType));
                    System.out.println("Car added successfully.");
                    break;

                case "2":
                    if (vehicles.size() >= 100) { System.out.println("Max vehicles reached."); break; }
                    System.out.print("Enter vehicle ID: ");
                    String bikeId = getInput(scanner);
                    System.out.print("Enter license plate: ");
                    String bikePlate = getInput(scanner);
                    System.out.print("Enter owner name: ");
                    String bikeOwner = getInput(scanner);
                    System.out.print("Enter engine size: ");
                    int engineSize = getInt(scanner);
                    System.out.print("Has helmet storage (true/false): ");
                    boolean hasHelmetStorage = Boolean.parseBoolean(getInput(scanner));
                    vehicles.add(new Motorcycle(bikeId, bikePlate, bikeOwner, engineSize, hasHelmetStorage));
                    System.out.println("Motorcycle added successfully.");
                    break;

                case "3":
                    if (vehicles.size() >= 100) { System.out.println("Max vehicles reached."); break; }
                    System.out.print("Enter vehicle ID: ");
                    String truckId = getInput(scanner);
                    System.out.print("Enter license plate: ");
                    String truckPlate = getInput(scanner);
                    System.out.print("Enter owner name: ");
                    String truckOwner = getInput(scanner);
                    System.out.print("Enter payload capacity (tons): ");
                    int payload = getInt(scanner);
                    System.out.print("Enter truck type (Pickup/Semi/Flatbed): ");
                    String truckType = getInput(scanner);
                    System.out.print("Enter number of axles: ");
                    int axles = getInt(scanner);
                    vehicles.add(new Truck(truckId, truckPlate, truckOwner, payload, truckType, axles));
                    System.out.println("Truck added successfully.");
                    break;

                case "4":
                    if (vehicles.size() >= 100) { System.out.println("Max vehicles reached."); break; }
                    System.out.print("Enter vehicle ID: ");
                    String evId = getInput(scanner);
                    System.out.print("Enter license plate: ");
                    String evPlate = getInput(scanner);
                    System.out.print("Enter owner name: ");
                    String evOwner = getInput(scanner);
                    System.out.print("Enter battery level (0-100): ");
                    int battery = getInt(scanner);
                    System.out.print("Enter range (miles): ");
                    int range = getInt(scanner);
                    System.out.print("Enter charger type (Level 1/Level 2/DC Fast): ");
                    String chargerType = getInput(scanner);
                    vehicles.add(new ElectricVehicle(evId, evPlate, evOwner, battery, range, chargerType));
                    System.out.println("Electric Vehicle added successfully.");
                    break;

                case "5":
                    System.out.println("\nAvailable Spots:");
                    for (ParkingSpot spot : spots) {
                        if (spot.checkAvailability()) spot.displaySpotInfo();
                    }
                    break;

                case "6":
                    if (vehicles.isEmpty()) { System.out.println("No vehicles available to park."); break; }

                    System.out.println("Select a vehicle to park:");
                    for (int i = 0; i < vehicles.size(); i++) {
                        System.out.println((i + 1) + ". " + vehicles.get(i)
                                + (vehicles.get(i).isParked() ? " [PARKED]" : ""));
                    }

                    System.out.print("Enter vehicle number: ");
                    int vehicleIndex = getInt(scanner) - 1;
                    if (vehicleIndex < 0 || vehicleIndex >= vehicles.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Vehicle selectedVehicle = vehicles.get(vehicleIndex);
                    if (selectedVehicle.isParked()) { System.out.println("That vehicle is already parked."); break; }

                    ParkingSpot availableSpot = null;
                    for (ParkingSpot spot : spots) {
                        if (spot.checkAvailability()) { availableSpot = spot; break; }
                    }
                    if (availableSpot == null) { System.out.println("No available parking spots."); break; }

                    System.out.print("Enter entry hour (0-23): ");
                    int entryHour = getInt(scanner);
                    if (entryHour < 0 || entryHour > 23) { System.out.println("Invalid hour."); break; }

                    ((Parkable) selectedVehicle).parkVehicle(availableSpot);
                    selectedVehicle.enterGarage();
                    Ticket ticket = new Ticket("T" + ticketCounter, selectedVehicle, availableSpot, entryHour);
                    ticket.generateTicket();
                    tickets.add(ticket);
                    ticketCounter++;
                    break;

                case "7":
                    if (tickets.isEmpty()) { System.out.println("No active tickets found."); break; }

                    System.out.println("Select a ticket:");
                    for (int i = 0; i < tickets.size(); i++) {
                        System.out.println((i + 1) + ". " + tickets.get(i));
                    }

                    System.out.print("Enter ticket number: ");
                    int ticketIndex = getInt(scanner) - 1;
                    if (ticketIndex < 0 || ticketIndex >= tickets.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Ticket selectedTicket = tickets.get(ticketIndex);
                    System.out.print("Enter exit hour (0-23): ");
                    int exitHour = getInt(scanner);
                    if (exitHour < 0 || exitHour > 23) { System.out.println("Invalid hour."); break; }

                    double fee = selectedTicket.calculateParkingFee(exitHour);
                    System.out.println("Total fee: $" + fee);

                    PaymentSystem payment = new PaymentSystem("P" + ticketIndex, "Card", fee);
                    if (payment.validatePayment(fee)) {
                        payment.processPayment(fee);
                        payment.printReceipt(fee);
                        selectedTicket.markAsPaid();
                        ((Parkable) selectedTicket.getVehicle()).leaveSpot(selectedTicket.getParkingSpot());
                        selectedTicket.getVehicle().leaveGarage();
                        tickets.remove(ticketIndex);
                    }
                    break;

                case "8":
                    if (tickets.isEmpty()) {
                        System.out.println("No active tickets.");
                    } else {
                        for (Ticket t : tickets) t.displayTicketDetails();
                    }
                    break;

                case "9":
                    int occupied = 0;
                    for (ParkingSpot spot : spots) {
                        if (!spot.checkAvailability()) occupied++;
                    }
                    System.out.println("\n===== Garage Status =====");
                    System.out.println("Total Spots   : " + spots.size());
                    System.out.println("Occupied      : " + occupied);
                    System.out.println("Available     : " + (spots.size() - occupied));
                    System.out.println("Active Tickets: " + tickets.size());
                    break;

                case "10":
                    running = false;
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Returns input, exits if user types EXIT (extra credit)
    static String getInput(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting. Have a nice day, Goodbye!");
            System.exit(0);
        }
        return input;
    }

    // Re-prompts until a valid integer is entered
    static int getInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("EXIT")) {
                System.out.println("Exiting.Have a nice day, Goodbye!");
                System.exit(0);
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("Try again: ");
            }
        }
    }
}