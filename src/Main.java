//Gunraj
// Hasham: updated
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //central garage manager — pre-loaded with 3 spots
        ParkingGarage garage = new ParkingGarage("Main Garage");
        garage.addParkingSpot(new ParkingSpot("S1"));
        garage.addParkingSpot(new ParkingSpot("S2"));
        garage.addParkingSpot(new ParkingSpot("S3"));

        boolean running = true;

        while (running) {
            System.out.println("\n===== Parking Garage System =====");
            System.out.println("1. Add Car");
            System.out.println("2. Add Motorcycle");
            System.out.println("3. Add Pickup Truck");
            System.out.println("4. Add Electric Vehicle");
            System.out.println("5. View Available Spots");
            System.out.println("6. Park Vehicle");
            System.out.println("7. Calculate Fee and Pay");
            System.out.println("8. View Tickets");
            System.out.println("9. Update Parked Vehicle");
            System.out.println("10. View Garage Status");
            System.out.println("11. Exit");
            System.out.println("(Type EXIT at any prompt to quit)");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();
            checkExit(choice, scanner);

            switch (choice) {
                case "1": {
                    if (garage.getParkedVehicles().size() >= ParkingGarage.MAXIMUM_INSTANCES) {
                        System.out.println("Max vehicles reached."); break;
                    }
                    System.out.print("Enter vehicle ID: ");
                    String carId = scanner.nextLine().trim();
                    checkExit(carId, scanner);

                    System.out.print("Enter license plate: ");
                    String carPlate = scanner.nextLine().trim();
                    checkExit(carPlate, scanner);

                    System.out.print("Enter owner name: ");
                    String carOwner = scanner.nextLine().trim();
                    checkExit(carOwner, scanner);

                    int doors = readInt(scanner, "Enter number of doors: ");

                    System.out.print("Enter fuel type: ");
                    String fuelType = scanner.nextLine().trim();
                    checkExit(fuelType, scanner);

                    int entryHour = readIntInRange(scanner, "Enter entry hour (0-23): ", 0, 23);

                    Car car = new Car(carId, carPlate, carOwner, doors, fuelType);
                    garage.parkVehicle(car, entryHour);
                    System.out.println("Car added and parked successfully.");
                    break;
                }

                case "2": {
                    if (garage.getParkedVehicles().size() >= ParkingGarage.MAXIMUM_INSTANCES) {
                        System.out.println("Max vehicles reached."); break;
                    }
                    System.out.print("Enter vehicle ID: ");
                    String bikeId = scanner.nextLine().trim();
                    checkExit(bikeId, scanner);

                    System.out.print("Enter license plate: ");
                    String bikePlate = scanner.nextLine().trim();
                    checkExit(bikePlate, scanner);

                    System.out.print("Enter owner name: ");
                    String bikeOwner = scanner.nextLine().trim();
                    checkExit(bikeOwner, scanner);

                    int engineSize = readInt(scanner, "Enter engine size (cc): ");
                    boolean hasHelmetStorage = readBoolean(scanner, "Has helmet storage (true/false): ");
                    int entryHour = readIntInRange(scanner, "Enter entry hour (0-23): ", 0, 23);

                    Motorcycle motorcycle = new Motorcycle(bikeId, bikePlate, bikeOwner, engineSize, hasHelmetStorage);
                    garage.parkVehicle(motorcycle, entryHour);
                    System.out.println("Motorcycle added and parked successfully.");
                    break;
                }

                case "3": {
                    if (garage.getParkedVehicles().size() >= ParkingGarage.MAXIMUM_INSTANCES) {
                        System.out.println("Max vehicles reached."); break;
                    }
                    System.out.print("Enter vehicle ID: ");
                    String truckId = scanner.nextLine().trim();
                    checkExit(truckId, scanner);

                    System.out.print("Enter license plate: ");
                    String truckPlate = scanner.nextLine().trim();
                    checkExit(truckPlate, scanner);

                    System.out.print("Enter owner name: ");
                    String truckOwner = scanner.nextLine().trim();
                    checkExit(truckOwner, scanner);

                    int payload = readInt(scanner, "Enter payload capacity (tons): ");

                    System.out.print("Enter truck type (Pickup/Semi/Flatbed): ");
                    String truckType = scanner.nextLine().trim();
                    checkExit(truckType, scanner);

                    int axles = readInt(scanner, "Enter number of axles: ");
                    int entryHour = readIntInRange(scanner, "Enter entry hour (0-23): ", 0, 23);

                    PickupTruck truck = new PickupTruck(truckId, truckPlate, truckOwner, payload, truckType, axles);
                    garage.parkVehicle(truck, entryHour);
                    System.out.println("Pickup Truck added and parked successfully.");
                    break;
                }

                case "4": {
                    if (garage.getParkedVehicles().size() >= ParkingGarage.MAXIMUM_INSTANCES) {
                        System.out.println("Max vehicles reached."); break;
                    }
                    System.out.print("Enter vehicle ID: ");
                    String evId = scanner.nextLine().trim();
                    checkExit(evId, scanner);

                    System.out.print("Enter license plate: ");
                    String evPlate = scanner.nextLine().trim();
                    checkExit(evPlate, scanner);

                    System.out.print("Enter owner name: ");
                    String evOwner = scanner.nextLine().trim();
                    checkExit(evOwner, scanner);

                    int battery = readIntInRange(scanner, "Enter battery level (0-100): ", 0, 100);
                    int range   = readInt(scanner, "Enter range (miles): ");

                    System.out.print("Enter charger type (Level 1/Level 2/DC Fast): ");
                    String charger = scanner.nextLine().trim();
                    checkExit(charger, scanner);

                    int entryHour = readIntInRange(scanner, "Enter entry hour (0-23): ", 0, 23);

                    ElectricVehicle ev = new ElectricVehicle(evId, evPlate, evOwner, battery, range, charger);
                    garage.parkVehicle(ev, entryHour);
                    System.out.println("Electric Vehicle added and parked successfully.");
                    break;
                }

                case "5":
                    garage.displayAvailableSpots();
                    break;

                case "6":
                    //vehicles are parked automatically when added via options 1-4
                    if (garage.getParkedVehicles().isEmpty()) {
                        System.out.println("No vehicles currently parked.");
                    } else {
                        System.out.println("Active vehicles in garage: " + garage.getParkedVehicles().size());
                        for (int i = 0; i < garage.getParkedVehicles().size(); i++) {
                            System.out.println((i + 1) + ". " + garage.getParkedVehicles().get(i));
                        }
                    }
                    break;

                case "7": {
                    if (garage.getActiveTickets().isEmpty()) {
                        System.out.println("No active tickets found."); break;
                    }
                    System.out.println("Select a ticket:");
                    for (int i = 0; i < garage.getActiveTickets().size(); i++) {
                        System.out.println((i + 1) + ". " + garage.getActiveTickets().get(i));
                    }

                    int ticketIndex = readInt(scanner, "Enter ticket number: ") - 1;

                    if (ticketIndex < 0 || ticketIndex >= garage.getActiveTickets().size()) {
                        System.out.println("Invalid ticket selection."); break;
                    }

                    Ticket selectedTicket = garage.getActiveTickets().get(ticketIndex);
                    int exitHour = readIntInRange(scanner, "Enter exit hour (0-23): ", 0, 23);

                    System.out.print("Enter payment method (Cash/Card): ");
                    String method = scanner.nextLine().trim();
                    checkExit(method, scanner);

                    //VehicleNotFoundException is caught here if ticket ID is invalid
                    try {
                        garage.removeVehicle(selectedTicket.getTicketId(), exitHour, method);
                    } catch (VehicleNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                }

                case "8":
                    if (garage.getActiveTickets().isEmpty()) {
                        System.out.println("No tickets available.");
                    } else {
                        for (Ticket ticket : garage.getActiveTickets()) {
                            ticket.displayTicketDetails();
                        }
                    }
                    break;

                case "9": {
                    //allow user to correct a parked vehicle's license plate or owner name
                    if (garage.getParkedVehicles().isEmpty()) {
                        System.out.println("No vehicles currently parked."); break;
                    }
                    System.out.println("Select vehicle to update:");
                    for (int i = 0; i < garage.getParkedVehicles().size(); i++) {
                        System.out.println((i + 1) + ". " + garage.getParkedVehicles().get(i));
                    }

                    int index = readInt(scanner, "Enter vehicle number: ") - 1;

                    if (index < 0 || index >= garage.getParkedVehicles().size()) {
                        System.out.println("Invalid selection."); break;
                    }

                    Vehicle v = garage.getParkedVehicles().get(index);
                    System.out.println("1. Update License Plate");
                    System.out.println("2. Update Owner Name");
                    String fieldChoice = scanner.nextLine().trim();

                    if (fieldChoice.equals("1")) {
                        System.out.print("Enter new license plate: ");
                        String newPlate = scanner.nextLine().trim();
                        checkExit(newPlate, scanner);
                        try {
                            v.setLicensePlate(newPlate);
                            System.out.println("License plate updated to: " + v.getLicensePlate());
                        } catch (InvalidLicensePlateException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else if (fieldChoice.equals("2")) {
                        System.out.print("Enter new owner name: ");
                        String newOwner = scanner.nextLine().trim();
                        checkExit(newOwner, scanner);
                        v.setOwnerName(newOwner);
                        System.out.println("Owner updated to: " + v.getOwnerName());
                    } else {
                        System.out.println("Invalid option.");
                    }
                    break;
                }

                case "10":
                    garage.displayGarageStatus();
                    break;

                case "11":
                    running = false;
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    //check if user typed EXIT — exits the program immediately
    public static void checkExit(String input, Scanner scanner) {
        if (input.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting system...");
            scanner.close();
            System.exit(0);
        }
    }

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            checkExit(input, scanner);
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
            if (value >= min && value <= max) return value;
            System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
        }
    }

    public static boolean readBoolean(Scanner scanner, String prompt) 
    
    {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            checkExit(input, scanner);
            if (input.equals("true"))       return true;
            else if (input.equals("false")) return false;
            else System.out.println("Invalid input. Please enter true or false.");
        }
    }
}
