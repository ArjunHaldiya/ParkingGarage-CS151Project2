import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();
        ArrayList<ParkingSpot> spots = new ArrayList<>();

        // preloaded parking spots
        spots.add(new ParkingSpot("S1"));
        spots.add(new ParkingSpot("S2"));
        spots.add(new ParkingSpot("S3"));

        boolean running = true;
        int ticketCounter = 1;

        while (running) {
            System.out.println("\n===== Parking Garage System =====");
            System.out.println("1. Add Car");
            System.out.println("2. Add Motorcycle");
            System.out.println("3. View Available Spots");
            System.out.println("4. Park Vehicle");
            System.out.println("5. Calculate Fee and Pay");
            System.out.println("6. View Tickets");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter vehicle ID: ");
                    String carId = scanner.nextLine().trim();

                    System.out.print("Enter license plate: ");
                    String carPlate = scanner.nextLine().trim();

                    System.out.print("Enter owner name: ");
                    String carOwner = scanner.nextLine().trim();

                    int doors = readInt(scanner, "Enter number of doors: ");

                    System.out.print("Enter fuel type: ");
                    String fuelType = scanner.nextLine().trim();

                    Car car = new Car(carId, carPlate, carOwner, doors, fuelType);
                    vehicles.add(car);
                    System.out.println("Car added successfully.");
                    break;

                case "2":
                    System.out.print("Enter vehicle ID: ");
                    String bikeId = scanner.nextLine().trim();

                    System.out.print("Enter license plate: ");
                    String bikePlate = scanner.nextLine().trim();

                    System.out.print("Enter owner name: ");
                    String bikeOwner = scanner.nextLine().trim();

                    int engineSize = readInt(scanner, "Enter engine size: ");
                    boolean hasHelmetStorage = readBoolean(scanner, "Has helmet storage (true/false): ");

                    Motorcycle motorcycle = new Motorcycle(
                            bikeId, bikePlate, bikeOwner, engineSize, hasHelmetStorage);
                    vehicles.add(motorcycle);
                    System.out.println("Motorcycle added successfully.");
                    break;

                case "3":
                    System.out.println("\nAvailable Spots:");
                    boolean foundAvailableSpot = false;

                    for (ParkingSpot spot : spots) {
                        if (spot.checkAvailability()) {
                            spot.displaySpotInfo();
                            foundAvailableSpot = true;
                        }
                    }

                    if (!foundAvailableSpot) {
                        System.out.println("No available parking spots.");
                    }
                    break;

                case "4":
                    if (vehicles.isEmpty()) {
                        System.out.println("No vehicles available to park.");
                        break;
                    }

                    System.out.println("Select a vehicle to park:");
                    for (int i = 0; i < vehicles.size(); i++) {
                        System.out.println((i + 1) + ". " + vehicles.get(i));
                    }

                    int vehicleIndex = readInt(scanner, "Enter vehicle number: ") - 1;

                    if (vehicleIndex < 0 || vehicleIndex >= vehicles.size()) {
                        System.out.println("Invalid vehicle selection.");
                        break;
                    }

                    Vehicle selectedVehicle = vehicles.get(vehicleIndex);

                    ParkingSpot availableSpot = null;
                    for (ParkingSpot spot : spots) {
                        if (spot.checkAvailability()) {
                            availableSpot = spot;
                            break;
                        }
                    }

                    if (availableSpot == null) {
                        System.out.println("No available parking spots.");
                        break;
                    }

                    if (selectedVehicle instanceof Parkable) {
                        ((Parkable) selectedVehicle).parkVehicle(availableSpot);

                        int entryHour = readIntInRange(scanner, "Enter entry hour (0-23): ", 0, 23);

                        Ticket ticket = new Ticket(
                                "T" + ticketCounter,
                                selectedVehicle,
                                availableSpot,
                                entryHour
                        );

                        ticket.generateTicket();
                        tickets.add(ticket);
                        ticketCounter++;
                    } else {
                        System.out.println("Selected vehicle cannot be parked.");
                    }
                    break;

                case "5":
                    if (tickets.isEmpty()) {
                        System.out.println("No active tickets found.");
                        break;
                    }

                    System.out.println("Select a ticket:");
                    for (int i = 0; i < tickets.size(); i++) {
                        System.out.println((i + 1) + ". " + tickets.get(i));
                    }

                    int ticketIndex = readInt(scanner, "Enter ticket number: ") - 1;

                    if (ticketIndex < 0 || ticketIndex >= tickets.size()) {
                        System.out.println("Invalid ticket selection.");
                        break;
                    }

                    Ticket selectedTicket = tickets.get(ticketIndex);

                    int exitHour = readIntInRange(scanner, "Enter exit hour (0-23): ", 0, 23);

                    double fee = selectedTicket.calculateParkingFee(exitHour);
                    System.out.println("Total fee: $" + fee);

                    PaymentSystem payment = new PaymentSystem("P" + ticketIndex, "Card", fee);

                    if (payment.validatePayment(fee)) {
                        payment.processPayment(fee);
                        payment.generateReceipt(fee);
                        selectedTicket.markAsPaid();

                        Vehicle vehicle = selectedTicket.getVehicle();
                        ParkingSpot spot = selectedTicket.getParkingSpot();

                        if (vehicle instanceof Parkable) {
                            ((Parkable) vehicle).leaveSpot(spot);
                        }
                    } else {
                        System.out.println("Payment validation failed.");
                    }
                    break;

                case "6":
                    if (tickets.isEmpty()) {
                        System.out.println("No tickets available.");
                    } else {
                        for (Ticket ticket : tickets) {
                            ticket.displayTicketDetails();
                        }
                    }
                    break;

                case "7":
                    running = false;
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = readInt(scanner, prompt);

            if (value >= min && value <= max) {
                return value;
            }

            System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
        }
    }

    public static boolean readBoolean(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("true")) {
                return true;
            } else if (input.equals("false")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter true or false.");
            }
        }
    }
}