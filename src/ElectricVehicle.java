public class ElectricVehicle extends Vehicle implements Parkable {
    private int batteryLevel;
    private int range;
    private String chargerType;
    private boolean needsCharging;

    public ElectricVehicle(String vehicleId, String licensePlate, String ownerName,int batteryLevel, int range, String chargerType) {
        super(vehicleId, licensePlate, ownerName);
        this.batteryLevel = batteryLevel;
        this.range = range;
        this.chargerType = chargerType;
        this.needsCharging = batteryLevel < 20;
    }

    public int getBatteryLevel()  
      {
         return batteryLevel;
        
        }
    public int getRange()          
     { return range; 

     }
    public String getChargerType() 

     { return chargerType;

      }
    public boolean isNeedsCharging() 

    { return needsCharging; 

    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel >= 0 && batteryLevel <= 100) {
           this.batteryLevel = batteryLevel;
            this.needsCharging = batteryLevel < 20;
        }
    }
    public void setRange(int range) 
     {
         this.range = range; 
         
        }

    public void setChargerType(String chargerType)
     { 
        this.chargerType = chargerType; 

     }
    public void setNeedsCharging(boolean needsCharging) { this.needsCharging = needsCharging; }

    public String checkBatteryLevel() {
        if (batteryLevel >= 80) {
            return "Battery is high: " + batteryLevel + "%";
        } else if (batteryLevel >= 20) {
            return "Battery is moderate: " + batteryLevel + "%";
        } else {
            return "Battery is low: " + batteryLevel + "% — charging recommended";
        }
    }

    public void requestChargingSpot() {
        if (needsCharging) {
            System.out.println(getLicensePlate() + " is requesting a " + chargerType + " charging spot.");
        } else {
            System.out.println(getLicensePlate() + " does not need charging.");
        }
    }

    @Override
    public void parkVehicle(ParkingSpot spot) {
        spot.assignVehicle(this);
        setParked(true);
    }

    @Override
    public void leaveSpot(ParkingSpot spot) {
        spot.removeVehicle();
        setParked(false);
    }

    @Override
    public String getPreferredSpotType() { return "EV"; }

    public void charge(int amount) {
        if (amount <= 0) {
            System.out.println("Invalid charge amount.");
            return;
        }
        setBatteryLevel(Math.min(100, batteryLevel + amount));
        System.out.println(getLicensePlate() + " charged to " + batteryLevel + "%.");
    }

    public int estimateRemainingRange() {
        return (int)(range * (batteryLevel / 100.0));
    }

    //assuming EVs get a slight discount
    @Override
    public double calculateParkingFee(int hours) {
        int billableHours = Math.max(1, hours);
        return billableHours * 4.0;
    }

    @Override
    public void displayVehicleInfo() {
        super.displayVehicleInfo();
        System.out.println("Battery Level : " + batteryLevel + "%");
        System.out.println("Range         : " + range + " miles");
        System.out.println("Charger Type  : " + chargerType);
        System.out.println("Needs Charge  : " + needsCharging);
    }

    @Override
    public String toString() {
        return "ElectricVehicle: " + super.toString() +
               ", Battery Level: " + batteryLevel +
               ", Range: " + range + "mi" +
               ", Charger: " + chargerType +
               ", Parked: " + isParked();
    }
}