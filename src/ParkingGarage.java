// Name: Fnu Hasham
//Gunraj - code review and cleanup

import java.util.ArrayList;

public class ParkingGarage 
{

    public static final int MAXIMUM_INSTANCES = 100;

    private String garageName;
    private ArrayList<ParkingSpot> parkingSpots;
    private ArrayList<Ticket> activeTickets;
    private ArrayList<Vehicle> parkedVehicles;
    private int ticketCounter;

    public ParkingGarage(String garageName) 
    {
        this.garageName = garageName;
        this.parkingSpots = new ArrayList<>();
        this.activeTickets = new ArrayList<>();
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

    public Ticket parkVehicle(Vehicle vehicle, int entryHour) 
    {
        if (vehicle.isParked()) 
        {
            System.out.println("Error: " + vehicle.getLicensePlate() + " is already parked.");
            return null;
        }

        ParkingSpot spot = findAvailableSpot();
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
            payment.processPayment(ticket, fee);
        } catch (InvalidPaymentException e) {
            System.out.println("Payment error: " + e.getMessage());
            return false;
        }

        ((Parkable) ticket.getVehicle()).leaveSpot(ticket.getParkingSpot());
        ticket.getVehicle().leaveGarage();
        ticket.markAsPaid();

        payment.generateReceipt(ticket);

        activeTickets.remove(ticket);
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

    public ArrayList<Vehicle> getParkedVehicles()   
    { 
        return parkedVehicles; 
    }

    @Override
    public String toString() 
    {
        return "ParkingGarage{name=" + garageName + ", total=" + parkingSpots.size()
                + ", occupied=" + parkedVehicles.size()
                + ", available=" + (parkingSpots.size() - parkedVehicles.size()) + "}";
    }
}