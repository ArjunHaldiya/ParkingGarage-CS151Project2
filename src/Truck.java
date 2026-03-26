public class Truck extends Vehicle implements Parkable {
    private int payloadCapacity; 
    private String truckType;
    private int numberOfAxles;
    
    //Constructor for new truck
    public Truck(String vehicleId, String licensePlate, String ownerName,
                 int payloadCapacity, String truckType, int numberOfAxles) {
        super(vehicleId, licensePlate, ownerName);

        if (payloadCapacity <= 0) {
            throw new IllegalArgumentException("Payload capacity must be greater than 0.");
        }
        if (truckType == null || truckType.isBlank()) {
            throw new IllegalArgumentException("Truck type cannot be null or blank.");
        }
        if (numberOfAxles <= 0) {
            throw new IllegalArgumentException("Number of axles must be greater than 0.");
        }
        
        this.payloadCapacity = payloadCapacity;
        this.truckType       = truckType;
        this.numberOfAxles   = numberOfAxles;
    }
    //Getter and setter
    public int getPayloadCapacity() {return payloadCapacity;}

    public void setPayloadCapacity(int payloadCapacity) {
        if (payloadCapacity <= 0) {
            throw new IllegalArgumentException("Payload capacity must be greater than 0.");
        }
        this.payloadCapacity = payloadCapacity;
    }

    public String getTruckType() {return truckType;}

    public void setTruckType(String truckType) {
        if (truckType == null || truckType.isBlank()) {
            throw new IllegalArgumentException("Truck type cannot be null or blank.");
        }
        this.truckType = truckType;
    }
    public int getNumberOfAxles() {return numberOfAxles;}

    public void setNumberOfAxles(int numberOfAxles) {
        if (numberOfAxles <= 0) {
            throw new IllegalArgumentException("Number of axles must be greater than 0.");
        }
        this.numberOfAxles = numberOfAxles;
    }
    
    /*
     * Displays truck-specific information.
     */
    @Override
    public void displayVehicleInfo() {
        System.out.println("========================================");
        System.out.println("           TRUCK INFORMATION            ");
        System.out.println("========================================");
        System.out.println("Vehicle ID      : " + getVehicleId());
        System.out.println("License Plate   : " + getLicensePlate());
        System.out.println("Owner           : " + getOwnerName());
        System.out.println("Truck Type      : " + truckType);
        System.out.println("Payload         : " + payloadCapacity + " tons");
        System.out.println("Axles           : " + numberOfAxles);
        System.out.println("Parked          : " + (isParked() ? "Yes" : "No"));
        System.out.println("Hourly Rate     : $8.00");
        System.out.println("========================================");
    }


    @Override
    public String toString() {
        return "Truck{" +
                "vehicleId='"      + getVehicleId()     + '\'' +
                ", plate='"        + getLicensePlate()   + '\'' +
                ", owner='"        + getOwnerName()      + '\'' +
                ", type='"         + truckType           + '\'' +
                ", payload="       + payloadCapacity     + "t"  +
                ", axles="         + numberOfAxles       +
                ", parked="        + isParked()          +
                '}';
    }
}