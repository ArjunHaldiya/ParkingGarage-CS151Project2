//Gunraj
//car is a type of vehicle
// Hasham-made some changes 

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