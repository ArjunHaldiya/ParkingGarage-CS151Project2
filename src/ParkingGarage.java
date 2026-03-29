// Name: Fnu Hasham
//Gunraj - made some corrections, was missing the actual class and cleaned up code

public class ParkingSpot
{
    private String spotId;
    private String spotType; // "STANDARD", "EV", "LARGE for PickupTruck"
    private Vehicle assignedVehicle;

    public ParkingSpot(String spotId)
    {
        this.spotId = spotId;
        this.spotType = "STANDARD";
        this.assignedVehicle = null;
    }

    public ParkingSpot(String spotId, String spotType)
    {
        this.spotId = spotId;
        this.spotType = spotType;
        this.assignedVehicle = null;
    }

    public String getSpotId()
    {
        return spotId;
    }
    public String getSpotLabel()
    {
        return spotId;
    } //alias used by vehicle classes
    public String getSpotType()
    {
        return spotType;
    }
    public Vehicle getAssignedVehicle()
    {
        return assignedVehicle;
    }
    public boolean isOccupied() 
    { 
        return assignedVehicle != null;
    }

    public boolean checkAvailability() 
    { 
        return assignedVehicle == null;
    }

    public void assignVehicle(Vehicle vehicle) 
    {
        if (this.assignedVehicle != null) 
        {
            throw new SpotOccupiedException("Spot " + spotId + " is already occupied.");
        }
        this.assignedVehicle = vehicle;
    }

    public void removeVehicle() 
    {
        this.assignedVehicle = null;
    }

    public void displaySpotInfo()
    {
        System.out.println("Spot ID  : " + spotId + " [" + spotType + "]");
        System.out.println("Status   : " + (isOccupied() ? "Occupied" : "Available"));
        if (isOccupied()) System.out.println("Vehicle  : " + assignedVehicle.getLicensePlate());
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
        return "ParkingSpot{id=" + spotId + ", type=" + spotType + ", occupied=" + isOccupied() + "}";
    }
}