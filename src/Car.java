//car is a type of vehicle
public class Car extends Vehicle 
{
    private int numberOfDoors;
    private String fuelType;

    //constructor would call on the parent class 
    public Car(String vehicleId, String licensePlate, String ownerName,int numberOfDoors, String fuelType) 
    {
        super(vehicleId, licensePlate, ownerName);
        this.numberOfDoors = numberOfDoors;
        this.fuelType = fuelType;
    }

    //getters
    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public String getFuelType() {
        return fuelType;
    }

    // setters
    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}