// Name: Fnu Hasham

//it was missing the actual class fixed that 
public class Motorcycle extends Vehicle implements Parkable
 {
    private int engineSize;
    private boolean hasHelmetStorage;

    public Motorcycle(String vehicleId, String licensePlate, String ownerName,
                      int engineSize, boolean hasHelmetStorage) 
    {
        super(vehicleId, licensePlate, ownerName);
        this.engineSize = engineSize;
        this.hasHelmetStorage = hasHelmetStorage;
    }

    //getters & setters 

    public int getEngineSize() 
    {
        return engineSize;
    }

    public void setEngineSize(int engineSize) 
    {
        this.engineSize = engineSize;
    }

    public boolean hasHelmetStorage() 
    {
        return hasHelmetStorage;
    }

    public void setHelmetStorage(boolean hasHelmetStorage) 
    {
        this.hasHelmetStorage = hasHelmetStorage;
    }

    @Override
    public void parkVehicle(ParkingSpot spot) 
    {
        if (spot != null && spot.checkAvailability()) 
        {
            spot.assignVehicle(this);
            setParked(true);
        }
    }

    @Override
    public void leaveSpot(ParkingSpot spot) 
    {
        if (spot != null) 
        {
            spot.removeVehicle();
            setParked(false);
        }
    }

    @Override
    public double calculateParkingFee(int hours) 
    {
        if (hours <= 0) {
            return 0;
        }
        return hours * 3.0;
    }

    @Override
    public void displayVehicleInfo() 
    {
        super.displayVehicleInfo();
        System.out.println("Engine Size: " + engineSize);
        System.out.println("Helmet Storage: " + hasHelmetStorage);
    }

    @Override
    public String toString() {
        return "Motorcycle: " + super.toString() +
               ", Engine Size: " + engineSize +
               ", Helmet Storage: " + hasHelmetStorage;
    }
}