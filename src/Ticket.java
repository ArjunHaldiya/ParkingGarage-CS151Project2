public class Ticket {

    private String ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private int entryHour;
    private int exitHour;
    private double totalFee;
    private boolean isPaid;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot parkingSpot, int entryHour) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryHour = entryHour;
        this.exitHour = -1;
        this.totalFee = 0.0;
        this.isPaid = false;
    }

    public void generateTicket() {
        System.out.println("========================================");
        System.out.println("         PARKING TICKET ISSUED          ");
        System.out.println("========================================");
        System.out.println("Ticket ID  : " + ticketId);
        System.out.println("Vehicle    : " + vehicle.getLicensePlate() + " (" + vehicle.getOwnerName() + ")");
        System.out.println("Spot       : " + parkingSpot.getSpotLabel());
        System.out.println("Entry Hour : " + entryHour + ":00");
        System.out.println("========================================");
    }

    public double calculateParkingFee(int exitHour) {
        if (exitHour < entryHour) {
            System.out.println("Error: exit hour cannot be before entry hour.");
            return 0;
        }

        this.exitHour = exitHour;
        int hoursParked = exitHour - entryHour;

        if (hoursParked == 0) hoursParked = 1;

        this.totalFee = ((Parkable) vehicle).calculateParkingFee(hoursParked);
        return this.totalFee;
    }

    public void markAsPaid() {
        if (exitHour == -1) {
            System.out.println("Error: cannot mark as paid before vehicle exits.");
            return;
        }
        this.isPaid = true;
        System.out.println("Ticket " + ticketId + " marked as PAID.");
    }

    public void displayTicketDetails() {
        System.out.println("========================================");
        System.out.println("         PARKING TICKET DETAILS         ");
        System.out.println("========================================");
        System.out.println("Ticket ID  : " + ticketId);
        System.out.println("Owner      : " + vehicle.getOwnerName());
        System.out.println("License    : " + vehicle.getLicensePlate());
        System.out.println("Spot       : " + parkingSpot.getSpotLabel());
        System.out.println("Entry Hour : " + entryHour + ":00");

        if (exitHour == -1) {
            System.out.println("Exit Hour  : Still parked");
            System.out.println("Total Fee  : Not yet calculated");
        } else {
            System.out.println("Exit Hour  : " + exitHour + ":00");
            System.out.println("Hours      : " + (exitHour - entryHour == 0 ? 1 : exitHour - entryHour));
            System.out.printf( "Total Fee  : $%.2f%n", totalFee);
        }

        System.out.println("Payment    : " + (isPaid ? "PAID" : "UNPAID"));
        System.out.println("========================================");
    }

    public String getTicketId()         { return ticketId; }
    public Vehicle getVehicle()         { return vehicle; }
    public ParkingSpot getParkingSpot() { return parkingSpot; }
    public int getEntryHour()           { return entryHour; }
    public int getExitHour()            { return exitHour; }
    public double getTotalFee()         { return totalFee; }
    public boolean isPaid()             { return isPaid; }

    @Override
    public String toString() {
        return "Ticket{id=" + ticketId + ", plate=" + vehicle.getLicensePlate()
                + ", spot=" + parkingSpot.getSpotLabel() + ", entry=" + entryHour
                + ", exit=" + (exitHour == -1 ? "N/A" : exitHour)
                + ", fee=$" + String.format("%.2f", totalFee) + ", paid=" + isPaid + "}";
    }
}