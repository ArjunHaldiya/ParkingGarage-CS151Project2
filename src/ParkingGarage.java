import java.util.ArrayList;

public class ParkingGarage {

    private String garageName;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Ticket> activeTickets;
    private ArrayList<Vehicle> parkedVehicles;
    private int ticketCounter;

    /**
     * Creates a new ParkingGarage with the given name and no spots pre-loaded.
     * use addParkingSpot(ParkingSpot) to populate the garage before use.
     * 
     */
    public ParkingGarage(String garageName) {
        if (garageName == null || garageName.isBlank()) {
            throw new IllegalArgumentException("Garage name cannot be null or blank.");
        }
        this.garageName      = garageName;
        this.parkingSpots    = new ArrayList<>();
        this.activeTickets   = new ArrayList<>();
        this.parkedVehicles  = new ArrayList<>();
        this.ticketCounter   = 1;
    }

    /**
     * Adds a new parking spot to the garage.
     * No duplicates
     */
    public void addParkingSpot(ParkingSpot spot) {
        if (spot == null) {
            System.out.println("ERROR: Cannot add a null parking spot.");
            return;
        }
        // Guard against duplicate spot IDs
        for (ParkingSpot existing : parkingSpots) {
            if (existing.getSpotId().equals(spot.getSpotId())) {
                System.out.println("ERROR: Spot ID " + spot.getSpotId() + " already exists in the garage.");
                return;
            }
        }
        parkingSpots.add(spot);
        System.out.println("Spot " + spot.getSpotLabel() + " added to " + garageName + ".");
    }

    /**
     * Searches the spot list and returns the first unoccupied spot.
     * Return first available spot or null
     */
    public ParkingSpot findAvailableSpot() {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.checkAvailability()) {
                return spot;
            }
        }
        return null; // garage is full
    }

    /**
     * Parks a vehicle in the garage.
     * return generated ticket or null
     */
    public Ticket parkVehicle(Vehicle vehicle, int entryHour) {
        //  Input validation 
        if (vehicle == null) {
            System.out.println("ERROR: Vehicle cannot be null.");
            return null;
        }
        if (entryHour < 0 || entryHour > 23) {
            System.out.println("ERROR: Entry hour must be between 0 and 23.");
            return null;
        }

        //  Check vehicle is not already in the garage 
        if (vehicle.isParked()) {
            System.out.println("ERROR: " + vehicle.getLicensePlate() + " is already parked.");
            return null;
        }

        //  Find a spot 
        ParkingSpot spot = findAvailableSpot();
        if (spot == null) {
            System.out.println("ERROR: Garage is full. No available spots for " + vehicle.getLicensePlate() + ".");
            return null;
        }

        //  Assign spot using Parkable interface 
        if (vehicle instanceof Parkable) {
            ((Parkable) vehicle).parkVehicle(spot);
        } else {
            System.out.println("ERROR: Vehicle type does not support parking operations.");
            return null;
        }

        //  Notify Vehicle to update its state 
        vehicle.enterGarage();

        //  Create ticket 
        String ticketId = "TKT-" + String.format("%04d", ticketCounter++);
        Ticket ticket = new Ticket(ticketId, vehicle, spot, entryHour);
        ticket.generateTicket();

        //  Update internal lists 
        activeTickets.add(ticket);
        parkedVehicles.add(vehicle);

        return ticket;
    }

}