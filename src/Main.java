//Gunraj

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<Ticket> tickets = new ArrayList<>();
        ArrayList<ParkingSpot> spots = new ArrayList<>();

        //preloaded parking spots tests
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

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter vehicle ID: ");
                    String carId = scanner.nextLine();

                    System.out.print("Enter license plate: ");
                    String carPlate = scanner.nextLine();

                    System.out.print("Enter owner name: ");
                    String carOwner = scanner.nextLine();

                    System.out.print("Enter number of doors: ");
                    int doors = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter fuel type: ");
                    String fuelType = scanner.nextLine();

                    Car car = new Car(carId, carPlate, carOwner, doors, fuelType);
                    vehicles.add(car);
                    System.out.println("Car added successfully.");
                    break;

                case "2":
                    System.out.print("Enter vehicle ID: ");
                    String bikeId = scanner.nextLine();

                    System.out.print("Enter license plate: ");
                    String bikePlate = scanner.nextLine();

                    System.out.print("Enter owner name: ");
                    String bikeOwner = scanner.nextLine();

                    System.out.print("Enter engine size: ");
                    int engineSize = Integer.parseInt(scanner.nextLine());

                    System.out.print("Has helmet storage (true/false): ");
                    boolean hasHelmetStorage = Boolean.parseBoolean(scanner.nextLine());

                    Motorcycle motorcycle = new Motorcycle(
                            bikeId, bikePlate, bikeOwner, engineSize, hasHelmetStorage);
                    vehicles.add(motorcycle);
                    System.out.println("Motorcycle added successfully.");
                    break;

                case "3":
                    System.out.println("\nAvailable Spots:");
                    for (ParkingSpot spot : spots) {
                        if (spot.checkAvailability()) {
                            spot.displaySpotInfo();
                        }
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

                    System.out.print("Enter vehicle number: ");
                    int vehicleIndex = Integer.parseInt(scanner.nextLine()) - 1;

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

                        System.out.print("Enter entry hour (0-23): ");
                        int entryHour = Integer.parseInt(scanner.nextLine());

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

                    System.out.print("Enter ticket number: ");
                    int ticketIndex = Integer.parseInt(scanner.nextLine()) - 1;

                    if (ticketIndex < 0 || ticketIndex >= tickets.size()) {
                        System.out.println("Invalid ticket selection.");
                        break;
                    }

                    Ticket selectedTicket = tickets.get(ticketIndex);

                    System.out.print("Enter exit hour (0-23): ");
                    int exitHour = Integer.parseInt(scanner.nextLine());

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
}