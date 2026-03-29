import java.util.ArrayList;

public class ParkingGarage 
{

    public static final int MAXIMUM_INSTANCES = 100;

    private String garageName;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Ticket> activeTickets;
    private ArrayList<Ticket> paidTickets;
    private ArrayList<Vehicle> parkedVehicles;
    private int ticketCounter;

    public ParkingGarage(String garageName)
    {
        this.garageName = garageName;
        this.parkingSpots = new ArrayList<>();
        this.activeTickets = new ArrayList<>();
        this.paidTickets = new ArrayList<>();
        this.parkedVehicles = new ArrayList<>();
        this.ticketCounter = 1;
    }

    public void addParkingSpot(ParkingSpot spot) 
    {
        if (parkingSpots.size() >= MAXIMUM_INSTANCES) 
        {
            System.out.println("Error: garage has reached the maximum of " + MAXIMUM_INSTANCES + " spots.");
            return;
        }


        //there should be no duplicate spot IDs
        for (ParkingSpot s : parkingSpots) 
        {
            if (s.getSpotId().equals(spot.getSpotId())) {
                System.out.println("Error: spot " + spot.getSpotId() + " already exists.");
                return;
            }
        }
        parkingSpots.add(spot);
    }

    public ParkingSpot findAvailableSpot()
    {
        for (ParkingSpot spot : parkingSpots)
        {
            if (spot.checkAvailability()) return spot;
        }
        return null;
    }

    // finds an available spot of a specific type; falls back to any available spot
    public ParkingSpot findAvailableSpotOfType(String type)
    {
        for (ParkingSpot spot : parkingSpots)
        {
            if (spot.checkAvailability() && spot.getSpotType().equals(type)) return spot;
        }
        return findAvailableSpot(); // fallback to any available spot
    }

    public Ticket parkVehicle(Vehicle vehicle, int entryHour)
    {
        if (vehicle.isParked())
        {
            System.out.println("Error: " + vehicle.getLicensePlate() + " is already parked.");
            return null;
        }

        // validate license plate before parking
        if (!vehicle.validateLicensePlate()) {
            System.out.println("Error: vehicle has an invalid license plate.");
            return null;
        }

        // route vehicle to its preferred spot type
        ParkingSpot spot = findAvailableSpotOfType(((Parkable) vehicle).getPreferredSpotType());

        if (spot == null) {
            System.out.println("Error: no available spots.");
            return null;
        }

        //catching the SpotOccupiedException in case of a conflict
        try {
            ((Parkable) vehicle).parkVehicle(spot);
        } catch (SpotOccupiedException e) {
            System.out.println("Error parking vehicle: " + e.getMessage());
            return null;
        }

        vehicle.enterGarage();

        String ticketId = "TKT-" + String.format("%04d", ticketCounter++);
        Ticket ticket = new Ticket(ticketId, vehicle, spot, entryHour);
        ticket.generateTicket();

        activeTickets.add(ticket);
        parkedVehicles.add(vehicle);
        return ticket;
    }

    public boolean removeVehicle(String ticketId, int exitHour, String paymentMethod) {
        Ticket ticket = findTicketById(ticketId);

        //throwing VehicleNotFoundException if ticket doesn't exist
        if (ticket == null) {
            throw new VehicleNotFoundException("Ticket " + ticketId + " not found.");
        }

        double fee = ticket.calculateParkingFee(exitHour);

        PaymentSystem payment = new PaymentSystem("PAY-" + ticketId, paymentMethod, fee);

        //catching the InvalidPaymentException if the payment fails
        try {
            payment.processPayment(fee);         // validate amount
            payment.processPayment(ticket, fee); // process against ticket
        } catch (InvalidPaymentException e) {
            System.out.println("Payment error: " + e.getMessage());
            return false;
        }

        ((Parkable) ticket.getVehicle()).leaveSpot(ticket.getParkingSpot());
        ticket.getVehicle().leaveGarage();
        ticket.markAsPaid();

        payment.generateReceipt(fee);    // quick summary line
        payment.generateReceipt(ticket); // full formatted receipt
        payment.displayPaymentStatus(ticket.isPaid());

        activeTickets.remove(ticket);
        paidTickets.add(ticket);
        parkedVehicles.remove(ticket.getVehicle());

        System.out.println(ticket.getVehicle().getLicensePlate() + " has exited. Goodbye!");
        return true;
    }

    public void displayAvailableSpots() 
    {
        System.out.println("--- Available Spots in " + garageName + " ---");
        int count = 0;
        for (ParkingSpot spot : parkingSpots) {
            if (spot.checkAvailability()) {
                spot.displaySpotInfo();
                count++;
            }
        }
        if (count == 0) System.out.println("No available spots.");
        else System.out.println("Total available: " + count);
    }

    public void displayGarageStatus() 
    {
        System.out.println("========================================");
        System.out.println("  GARAGE STATUS: " + garageName);
        System.out.println("========================================");
        System.out.println("Total Spots  : " + parkingSpots.size());
        System.out.println("Occupied     : " + parkedVehicles.size());
        System.out.println("Available    : " + (parkingSpots.size() - parkedVehicles.size()));
        if (activeTickets.isEmpty()) {
            System.out.println("No active tickets.");
        } else {
            System.out.println("--- Active Tickets ---");
            for (Ticket t : activeTickets) System.out.println(t);
        }
        System.out.println("========================================");
    }

    private Ticket findTicketById(String ticketId) {
        for (Ticket t : activeTickets) {
            if (t.getTicketId().equals(ticketId)) return t;
        }
        return null;
    }

    public String getGarageName()                   
    { 
        return garageName; 
    }
    
    public void setGarageName(String garageName)    
    { 
        this.garageName = garageName; 
    }


    public ArrayList<ParkingSpot> getParkingSpots() 
    { 
        return parkingSpots; 
    }

    public ArrayList<Ticket> getActiveTickets()
    {
        return activeTickets;
    }

    public ArrayList<Ticket> getPaidTickets()
    {
        return paidTickets;
    }

    public void refundPayment(String ticketId)
    {
        for (Ticket t : paidTickets) {
            if (t.getTicketId().equals(ticketId)) {
                PaymentSystem refund = new PaymentSystem("REFUND-" + ticketId, "Refund", t.getTotalFee());
                refund.refundPayment(t.getTotalFee());
                paidTickets.remove(t);
                System.out.println("Refund of $" + String.format("%.2f", t.getTotalFee()) + " issued for ticket " + ticketId + ".");
                return;
            }
        }
        System.out.println("Paid ticket " + ticketId + " not found.");
    }

    public ArrayList<Vehicle> getParkedVehicles()   
    { 
        return parkedVehicles; 
    }

    public void displayGrid()
    {
        final int COLS = 5;
        System.out.println("========================================");
        System.out.println("   GARAGE GRID: " + garageName);
        System.out.println("   [STD] = Standard  [EV] = Electric  [LG] = Large");
        System.out.println("========================================");

        for (int i = 0; i < parkingSpots.size(); i++)
        {
            ParkingSpot spot = parkingSpots.get(i);

            // spot type label
            String typeTag;
            switch (spot.getSpotType()) {
                case "EV":    typeTag = "EV "; break;
                case "LARGE": typeTag = "LG "; break;
                default:      typeTag = "STD"; break;
            }

            // occupancy label
            String occupant;
            if (!spot.isOccupied()) {
                occupant = "----";
            } else {
                Vehicle v = spot.getAssignedVehicle();
                if      (v instanceof ElectricVehicle) occupant = "EV  ";
                else if (v instanceof PickupTruck)      occupant = "TRCK";
                else if (v instanceof Motorcycle)       occupant = "MOTO";
                else                                    occupant = "CAR ";
            }

            System.out.printf("[%s|%s:%s]", typeTag, spot.getSpotId(), occupant);

            if ((i + 1) % COLS == 0) System.out.println();
        }

        // newline if last row wasn't full
        if (parkingSpots.size() % COLS != 0) System.out.println();
        System.out.println("========================================");
    }

    @Override
    public String toString()
    {
        return "ParkingGarage{name=" + garageName + ", total=" + parkingSpots.size()
                + ", occupied=" + parkedVehicles.size()
                + ", available=" + (parkingSpots.size() - parkedVehicles.size()) + "}";
    }
}