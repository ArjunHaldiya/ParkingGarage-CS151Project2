
public class Motorcycle extends Vehicle implements Parkable {
    private int engineSize;
    private boolean hasHelmetStorage;

    public Motorcycle(String vehicleId, String licensePlate, String ownerName,
                      int engineSize, boolean hasHelmetStorage) {
        super(vehicleId, licensePlate, ownerName);
        this.engineSize = engineSize;
        this.hasHelmetStorage = hasHelmetStorage;
    }

    public int getEngineSize()      
        { return engineSize; 

        }
    public boolean hasHelmetStorage()   
    { return hasHelmetStorage; 
        
    }

    public void setEngineSize(int engineSize) 
        { this.engineSize = engineSize; 

        }
        
    public void setHasHelmetStorage(boolean hasHelmetStorage) 
    { this.hasHelmetStorage = hasHelmetStorage; 

    }

    @Override
    public void parkVehicle(ParkingSpot spot)
     {
        spot.assignVehicle(this);
        setParked(true);
    }

    @Override
    public void leaveSpot(ParkingSpot spot) 
    {
        spot.removeVehicle();
        setParked(false);
    }

    //minimum 1 hour billing
    @Override
    public double calculateParkingFee(int hours) 
    {
        int billableHours = Math.max(1, hours);
        return billableHours * 3.0;
    }

    public boolean canFitInSpot(String spotType) 
    {
        return spotType.equalsIgnoreCase("compact") || spotType.equalsIgnoreCase("motorcycle");
    }

    public void displayParkingMessage() {
        System.out.println("Motorcycle is parked successfully.");
    }

    public void printReceipt(int hours) {
        double fee = calculateParkingFee(hours);
        System.out.println("Motorcycle parking fee: $" + fee);
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Engine Size     : " + engineSize + "cc");
        System.out.println("Helmet Storage  : " + hasHelmetStorage);
    }

    @Override
    public String toString() {
        return "Motorcycle{id=" + getVehicleId() + ", plate=" + getLicensePlate()
                + ", engine=" + engineSize + "cc, helmetStorage=" + hasHelmetStorage + "}";
    }
}