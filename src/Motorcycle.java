
//Gunraj - it was missing the actual class fixed that 
public class Motorcycle extends Vehicle implements Parkable
 {
    private int engineSize;
    private boolean hasHelmetStorage;

    public double calculateParkingFee(int hours) {
        if (hours <= 0) {
            return 0;
        }
        return hours * 3.0;
    }

    public boolean canFitInSpot(String spotType) {
        return spotType.equalsIgnoreCase("compact") || spotType.equalsIgnoreCase("motorcycle");
    }

    public void displayParkingMessage() {
        System.out.println("Motorcycle is parked successfully.");
    }

    public void printReceipt(int hours) {
        double fee = calculateParkingFee(hours);
        if (fee == 0) {
            System.out.println("Invalid parking hours.");
        } else {
            System.out.println("Motorcycle parking fee: $" + fee);
        }
    }
}