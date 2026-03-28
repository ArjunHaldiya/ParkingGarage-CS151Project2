//Gunraj

public class PickupTruck extends Vehicle implements Parkable 
{
    private double loadCapacity;

    public PickupTruck(String vehicleId, String licensePlate, String ownerName,
                 int payloadCapacity, String truckType, int numberOfAxles) {
        super(vehicleId, licensePlate, ownerName);
        this.payloadCapacity = payloadCapacity;
        this.truckType = truckType;
        this.numberOfAxles = numberOfAxles;
    }

    @Override
    public void parkVehicle(ParkingSpot spot) {
        spot.assignVehicle(this);
        System.out.println("Truck " + getLicensePlate() + " parked at spot " + spot.getSpotLabel());
    }

    @Override
    public void leaveSpot(ParkingSpot spot) {
        spot.removeVehicle();
        System.out.println("Truck " + getLicensePlate() + " left spot " + spot.getSpotLabel());
    }

    @Override
    public double calculateParkingFee(int hours) {
        if (hours <= 0) hours = 1;
        return hours * 8.00;
    }

    @Override
    public void displayVehicleInfo() {
        System.out.println("Truck | ID: " + getVehicleId() + " | Plate: " + getLicensePlate()
                + " | Owner: " + getOwnerName() + " | Type: " + truckType
                + " | Payload: " + payloadCapacity + "t | Axles: " + numberOfAxles);
    }

    public int getPayloadCapacity() { return payloadCapacity; }
    public void setPayloadCapacity(int payloadCapacity) { this.payloadCapacity = payloadCapacity; }
    public String getTruckType() { return truckType; }
    public void setTruckType(String truckType) { this.truckType = truckType; }
    public int getNumberOfAxles() { return numberOfAxles; }
    public void setNumberOfAxles(int numberOfAxles) { this.numberOfAxles = numberOfAxles; }

    @Override
    public String toString() {
        return "Truck{id=" + getVehicleId() + ", plate=" + getLicensePlate()
                + ", type=" + truckType + ", payload=" + payloadCapacity + "t}";
    }
}