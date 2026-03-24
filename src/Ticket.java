public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private int entryHour;
    private int exitHour;
    private double totalFee;
    private boolean isPaid;

    /**
     * Creates a new Ticket for a vehicle assigned to a specific parking spot.
     * INCLUDE THESE
     * unique identifier for this ticket
     * the vehicle being parked
     * the spot assigned to the vehicle
     * the hour (0–23) the vehicle entered the garage
     */
    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot parkingSpot, int entryHour) {
        if (ticketId == null || ticketId.isBlank()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or blank.");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }
        if (parkingSpot == null) {
            throw new IllegalArgumentException("Parking spot cannot be null.");
        }
        if (entryHour < 0 || entryHour > 23) {
            throw new IllegalArgumentException("Entry hour must be between 0 and 23.");
        }

        this.ticketId    = ticketId;
        this.vehicle     = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryHour   = entryHour;
        this.exitHour    = -1;      // -1 means the vehicle has not exited yet
        this.totalFee    = 0.0;
        this.isPaid      = false;
    }
    /**
     * Getters and Setters
     */
    public String getTicketId()         { return ticketId; }
    public Vehicle getVehicle()         { return vehicle; }
    public ParkingSpot getParkingSpot() { return parkingSpot; }
    public int getEntryHour()           { return entryHour; }
    public int getExitHour()            { return exitHour; }
    public double getTotalFee()         { return totalFee; }
    public boolean isPaid()             { return isPaid; }


    /**
     * Prints a summary of the ticket at the moment of garage entry.
     * Called right after a ticket is created so the driver has a record.
     */
    public void generateTicket() {
        System.out.println("========================================");
        System.out.println("         PARKING TICKET ISSUED          ");
        System.out.println("========================================");
        System.out.println("Ticket ID   : " + ticketId);
        System.out.println("Vehicle     : " + vehicle.getLicensePlate()
                + " (" + vehicle.getOwnerName() + ")");
        System.out.println("Spot        : " + parkingSpot.getSpotLabel());
        System.out.println("Entry Hour  : " + entryHour + ":00");
        System.out.println("Status      : Active - vehicle is parked");
        System.out.println("========================================");
    }

    /**
     * Calculates the total parking fee based on hours parked. (use Parkable interface)
     * Sets exitHour to the provided value and delegates fee
     * 
     */
    public double calculateParkingFee(int exitHour) {
        if (exitHour < 0 || exitHour > 23) {
            throw new IllegalArgumentException("Exit hour must be between 0 and 23.");
        }
        if (exitHour < entryHour) {
            throw new IllegalArgumentException(
                    "Exit hour (" + exitHour + ") cannot be before entry hour (" + entryHour + ").");
        }

        this.exitHour = exitHour;
        int hoursParked = exitHour - entryHour;

        // Minimum charge of 1 hour so a parking is never free
        if (hoursParked == 0) {
            hoursParked = 1;
        }

        // (Car = $5/hr, Motorcycle = $3/hr) (use calculateParkingFee from Parkable)
        if (vehicle instanceof Parkable) 
        {
            this.totalFee = ((Parkable) vehicle).calculateParkingFee(hoursParked);
        }
        return this.totalFee;
    }
     /**
     * Marks the ticket as paid.
     * Only call if process payment succeeds.
     */
    public void markAsPaid() {
        if (exitHour == -1) {
            throw new IllegalStateException(
                    "Cannot mark ticket as paid before calculating the fee (vehicle has not exited).");
        }
        this.isPaid = true;
        System.out.println("Ticket " + ticketId + " has been marked as PAID.");
    }

    /**
     * Prints the full details of the ticket, including fee and payment status.
     */
    public void displayTicketDetails() {
        System.out.println("========================================");
        System.out.println("         PARKING TICKET DETAILS         ");
        System.out.println("========================================");
        System.out.println("Ticket ID   : " + ticketId);
        System.out.printf( "Owner       : %s%n", vehicle.getOwnerName());
        System.out.printf( "License     : %s%n", vehicle.getLicensePlate());
        System.out.printf( "Spot        : %s%n", parkingSpot.getSpotLabel());
        System.out.printf( "Entry Hour  : %d:00%n", entryHour);

        if (exitHour == -1) {
            System.out.println("Exit Hour   : Still parked");
            System.out.println("Total Fee   : Not yet calculated");
        } else {
            System.out.printf( "Exit Hour   : %d:00%n", exitHour);
            System.out.printf( "Hours Parked: %d%n", (exitHour - entryHour == 0 ? 1 : exitHour - entryHour));
            System.out.printf( "Total Fee   : $%.2f%n", totalFee);
        }

        System.out.println("Payment     : " + (isPaid ? "PAID" : "UNPAID"));
        System.out.println("========================================");
    }



    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='"    + ticketId                        + '\'' +
                ", plate='"     + vehicle.getLicensePlate()        + '\'' +
                ", spot='"      + parkingSpot.getSpotLabel()       + '\'' +
                ", entry="      + entryHour                        +
                ", exit="       + (exitHour == -1 ? "N/A" : exitHour) +
                ", fee=$"       + String.format("%.2f", totalFee)  +
                ", paid="       + isPaid                           +
                '}';
    }
   
}