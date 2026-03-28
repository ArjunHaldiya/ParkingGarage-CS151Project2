// Name: Fnu Hasham
//Gunraj - made some corrections, was missing the actual class and cleaned up code

public class ParkingSpot 
{
    private String spotId;
    private Vehicle assignedVehicle;

    public ParkingSpot(String spotId) 
    {
        this.spotId = spotId;
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
        System.out.println("Spot ID  : " + spotId);
        System.out.println("Status   : " + (isOccupied() ? "Occupied" : "Available"));
        if (isOccupied()) System.out.println("Vehicle  : " + assignedVehicle.getLicensePlate());
    }

    @Override
    public String toString() 
    {
        return "ParkingSpot{id=" + spotId + ", occupied=" + isOccupied() + "}";
    }
}