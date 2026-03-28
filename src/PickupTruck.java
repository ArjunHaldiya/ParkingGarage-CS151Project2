//Arjun
//Gunraj code reviewing and cleaning up

public class PickupTruck extends Vehicle implements Parkable 
{
    private int payloadCapacity;
    private String truckType;
    private int numberOfAxles;

    public PickupTruck(String vehicleId, String licensePlate, String ownerName,
                       int payloadCapacity, String truckType, int numberOfAxles) 
    {
        super(vehicleId, licensePlate, ownerName);
        this.payloadCapacity = payloadCapacity;
        this.truckType = truckType;
        this.numberOfAxles = numberOfAxles;
    }

    @Override
    public void parkVehicle(ParkingSpot spot) 
    {
        spot.assignVehicle(this);
        setParked(true);
        System.out.println("Truck " + getLicensePlate() + " parked at spot " + spot.getSpotLabel());
    }

    @Override
    public void leaveSpot(ParkingSpot spot) 
    {
        spot.removeVehicle();
        setParked(false);
        System.out.println("Truck " + getLicensePlate() + " left spot " + spot.getSpotLabel());
    }

    @Override
    public double calculateParkingFee(int hours) 
    {
        int billableHours = Math.max(1, hours);
        return billableHours * 8.00;
    }

    @Override
    public void displayVehicleInfo() 
    {
        System.out.println("Truck | ID: " + getVehicleId() + " | Plate: " + getLicensePlate()
                + " | Owner: " + getOwnerName() + " | Type: " + truckType
                + " | Payload: " + payloadCapacity + "t | Axles: " + numberOfAxles);
    }

    public int getPayloadCapacity()    
    { 
        return payloadCapacity; 
    }
    
    public String getTruckType()       
    { 
        return truckType; 
    }
    
    public int getNumberOfAxles()     
    { 
        return numberOfAxles; 
    }

    public void setPayloadCapacity(int payloadCapacity) 
    { 
        this.payloadCapacity = payloadCapacity; 
    }

    public void setTruckType(String truckType)          
    { 
        this.truckType = truckType; 
    }

    public void setNumberOfAxles(int numberOfAxles)     
    { 
        this.numberOfAxles = numberOfAxles; 
    }

    @Override
    public String toString() 
    {
        return "Truck{id=" + getVehicleId() + ", plate=" + getLicensePlate()
                + ", type=" + truckType + ", payload=" + payloadCapacity + "t}";
    }
}