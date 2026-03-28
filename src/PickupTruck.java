//Gunraj

public class PickupTruck extends Vehicle implements Parkable 
{
    private double loadCapacity;

    public PickupTruck(String vehicleId, String licensePlate, String ownerName,
                       double loadCapacity) 
    {
        super(vehicleId, licensePlate, ownerName);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() 
    {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) 
    {
        if (loadCapacity > 0) {
            this.loadCapacity = loadCapacity;
        }
    }

    @Override
    public void parkVehicle(ParkingSpot spot) 
    {
        if (spot != null && spot.checkAvailability()) {
            spot.assignVehicle(this);
            setParked(true);
        }
    }

    @Override
    public void leaveSpot(ParkingSpot spot) 
    {
        if (spot != null) {
            spot.removeVehicle();
            setParked(false);
        }
    }

    @Override
    //trucks would cost more to park
    public double calculateParkingFee(int hours)
     {
        if (hours <= 0) return 0;
        return hours * 7.0; 
    }

    @Override
    public void displayVehicleInfo() 
    {
        super.displayVehicleInfo();
        System.out.println("Load Capacity: " + loadCapacity + " tons");
    }

    @Override
    public String toString() 
    {
        return "PickupTruck:" + super.toString() +
               ", Load Capacity: " + loadCapacity;
    }
}