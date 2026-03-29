
public class Car extends Vehicle implements Parkable {
    private int numberOfDoors;
    private String fuelType;

    public Car(String vehicleId, String licensePlate, String ownerName, int numberOfDoors, String fuelType) 
    {
        super(vehicleId, licensePlate, ownerName);
        this.numberOfDoors = numberOfDoors;
        this.fuelType = fuelType;
    }

    public int getNumberOfDoors() 
    { 
        return numberOfDoors; 
    }

    public String getFuelType() 
    { 
        return fuelType; 
    }

    public void setNumberOfDoors(int numberOfDoors) 
    { 
        this.numberOfDoors = numberOfDoors; 
    }

    public void setFuelType(String fuelType) 
    { 
        this.fuelType = fuelType; 
    }


    @Override
    public void parkVehicle(ParkingSpot spot) 
    {
        spot.assignVehicle(this);
        setParked(true);
    }

    @Override
    public void leaveSpot(ParkingSpot spot) {
        spot.removeVehicle();
        setParked(false);
    }


    @Override
    public String getPreferredSpotType() { return "STANDARD"; }

    public boolean isElectricFuel() {
        return fuelType.equalsIgnoreCase("electric") || fuelType.equalsIgnoreCase("hybrid");
    }

    public boolean isCompact() {
        return numberOfDoors <= 2;
    }

    public String getCarCategory() {
        if (isCompact()) return "Compact";
        if (numberOfDoors >= 5) return "Minivan";
        return "Standard";
    }

    public void updateConfiguration(int newDoors, String newFuel) {
        if (newDoors > 0) this.numberOfDoors = newDoors;
        if (newFuel != null && !newFuel.isBlank()) this.fuelType = newFuel;
        System.out.println("Car configuration updated: " + this.numberOfDoors + " doors, " + this.fuelType + " fuel.");
    }

    @Override
    public double calculateParkingFee(int hours)
    {
        int billable = Math.max(1, hours);
        return billable * 5.0;
    }

    @Override
    public void displayVehicleInfo() 
    {
        super.displayVehicleInfo();
        System.out.println("Doors        : " + numberOfDoors);
        System.out.println("Fuel Type    : " + fuelType);
    }

    @Override
    public String toString() 
    {
        return "Car{id=" + getVehicleId() + ", plate=" + getLicensePlate()
                + ", doors=" + numberOfDoors + ", fuel=" + fuelType + "}";
    }
}