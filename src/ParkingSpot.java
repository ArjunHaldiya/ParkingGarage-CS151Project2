// Name: Fnu Hasham
//made some corrections - was missing the actual class

public class ParkingSpot 
{
    private String spotId;
    private Vehicle vehicle;

    public ParkingSpot(String spotId) 
    {
        this.spotId = spotId;
        this.vehicle = null;
    }

    public String getSpotId() 
    {
        return spotId;
    }

    public void setSpotId(String spotId) 
    {
        this.spotId = spotId;
    }

    public Vehicle getVehicle() 
    {
        return vehicle;
    }

    public Vehicle getAssignedVehicle()
    {
        return vehicle;
    }

    public boolean checkAvailability() 
    {
        return vehicle == null;
    }

    public boolean isOccupied()
    {
        return vehicle != null;
    }

    public void assignVehicle(Vehicle vehicle) 
    {
        if (this.vehicle == null) 
        {
            this.vehicle = vehicle;
        }
    }

    public void removeVehicle() 
    {
        this.vehicle = null;
    }

    public void displaySpotInfo() 
    {
        System.out.println("Spot ID: " + spotId);
        System.out.println("Occupied: " + (vehicle != null));
    }

    @Override
    public String toString() 
    {
        return "ParkingSpot{spotId='" + spotId + "', occupied=" + (vehicle != null) + "}";
    }

    public String getSpotLabel() 
    {
        return spotId;
    }
}