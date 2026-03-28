//Gunraj

//an abstract class for all vehicles in the parking system
public abstract class Vehicle {
    private String vehicleId;
    private String licensePlate;
    private String ownerName;
    private boolean isParked;

    //initializing the basic vehicle info
    public Vehicle(String vehicleId, String licensePlate, String ownerName) 
    {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.ownerName = ownerName;
        this.isParked = false; //assuming car is not parked when its created
    }

    //getters
    public String getVehicleId() 
    {
        return vehicleId;
    }

    public String getLicensePlate() 
    {
        return licensePlate;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }

    public boolean isParked() 
    {
        return isParked;
    }

    //setters
    public void setVehicleId(String vehicleId) 
    {
        this.vehicleId = vehicleId;
    }

    //modifying the logic here for these 2 methods
    public void setLicensePlate(String licensePlate) throws InvalidLicensePlateException 
    {
        if (licensePlate == null || licensePlate.isBlank()) 
        {
            throw new InvalidLicensePlateException("License plate cannot be blank.");
        }
    
        if (licensePlate.length() < 5) 
        {
            throw new InvalidLicensePlateException("License plate must be at least 5 characters long.");
        }
    
        this.licensePlate = licensePlate;
    }
    
    public void setOwnerName(String ownerName) 
    {
        if (ownerName != null && !ownerName.isBlank()) 
        {
            this.ownerName = ownerName;
        }
    }

    public void setParked(boolean parked) 
    {
        isParked = parked;
    }

    //should be true when car is parked
    public void enterGarage() {
        isParked = true;
    }

    //indicates when the vehicle is leaving
    public void leaveGarage() {
        isParked = false;
    }

    //prints the basic info 
    public void displayVehicleInfo() {
        System.out.println("Vehicle ID : " + vehicleId);
        System.out.println("License Plate : " + licensePlate);
        System.out.println("Owner : " + ownerName);
        System.out.println("Parked : " + isParked);
    }

    //checking if the license plate is not null/blank
    public boolean validateLicensePlate() 
    {
    return licensePlate != null && !licensePlate.isBlank();
    }

    
    @Override
    public String toString() 
    {
        return "Vehicle ID : " + vehicleId +
               ", Plate : " + licensePlate +
               ", Owner : " + ownerName;
    }


    //adding some exceptions to test 

    public class SpotOccupiedException extends Exception 
    {
        public SpotOccupiedException(String message) 
        {
            super(message);
        }
    }

    public class InvalidPaymentException extends Exception 
    {
        public InvalidPaymentException(String message) 
        {
            super(message);
        }
    }


    public class VehicleNotFoundException extends Exception 
    {
        public VehicleNotFoundException(String message) 
        {
            super(message);
        }
    }

    //if license plate number entered is invalid 
    public class InvalidLicensePlateException extends Exception 
    {
        public InvalidLicensePlateException(String message) {
            super(message);
        }
    }


    
}