//an abstract class for all vehicles in the parking system
public abstract class Vehicle {
    private String vehicleId;
    private String licensePlate;
    private String ownerName;
    private boolean isParked;

    //initializing the basic vehicle info
    public Vehicle(String vehicleId, String licensePlate, String ownerName) 
    {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.isParked = false; //assuming car is not parked when its created
    }

    //getters
    public String getVehicleId() 
    {
        return vehicleId;
    }

    public String getLicensePlate() 
    {
        return licensePlate;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }

    public boolean isParked() 
    {
        return isParked;
    }

    //setters
    public void setVehicleId(String vehicleId) 
    {
        this.vehicleId = vehicleId;
    }

    public void setLicensePlate(String licensePlate) 
    {
        this.licensePlate = licensePlate;
    }

    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public void setParked(boolean parked) 
    {
        isParked = parked;
    }

    //should be true when car is parked
    public void enterGarage() {
        isParked = true;
    }

    //indicates when the vehicle is leaving
    public void leaveGarage() {
        isParked = false;
    }

    //prints the basic info 
    public void displayVehicleInfo() {
        System.out.println("Vehicle ID : " + vehicleId);
        System.out.println("License Plate : " + licensePlate);
        System.out.println("Owner : " + ownerName);
    }

    
    @Override
    public String toString() 
    {
        return "Vehicle ID : " + vehicleId +
               ", Plate : " + licensePlate +
               ", Owner : " + ownerName;
    }
}