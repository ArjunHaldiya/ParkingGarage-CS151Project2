public class ElectricVehicle extends Vehicle implements Parkable 
{
    private int batteryLevel;
    private boolean isCharging;

    public ElectricVehicle(String vehicleId, String licensePlate, String ownerName,
                           int batteryLevel, boolean isCharging) 
    {
        super(vehicleId, licensePlate, ownerName);
        this.batteryLevel = batteryLevel;
        this.isCharging = isCharging;
    }

    public int getBatteryLevel() 
    {
        return batteryLevel;
    }

    public boolean isCharging() 
    {
        return isCharging;
    }

    public void setBatteryLevel(int batteryLevel) 
    {
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel;
        }
    }

    public void setCharging(boolean charging) 
    {
        isCharging = charging;
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
    //assming EV's would get a discount
    public double calculateParkingFee(int hours) 
    {
        if (hours <= 0) return 0;
        return hours * 4.0;
    }

    @Override
    public void displayVehicleInfo() 
    {
        super.displayVehicleInfo();
        System.out.println("Battery Level: " + batteryLevel + "%");
        System.out.println("Charging: " + isCharging);
    }

    @Override
    public String toString() 
    {
        return "ElectricVehicle: " + super.toString() +
               ", Battery Level: " + batteryLevel +
               ", Charging: " + isCharging;
    }
}