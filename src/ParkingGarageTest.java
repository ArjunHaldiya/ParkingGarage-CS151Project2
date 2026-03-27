public class ParkingGarageTest {
    public static void main(String[] args) {
        testCarParkingFee();
        testMotorcycleParkingFee();
        testVehicleParking();
        testTicketFeeCalculation();
    }

    public static void testCarParkingFee() {
        Car car = new Car("C1", "ABC123", "Gunraj", 4, "Gas");
        double fee = car.calculateParkingFee(2);

        if (fee == 10.0) {
            System.out.println("testCarParkingFee passed");
        } else {
            System.out.println("testCarParkingFee failed");
        }
    }

    public static void testMotorcycleParkingFee() {
        Motorcycle bike = new Motorcycle("M1", "XYZ999", "Hasham", 300, true);
        double fee = bike.calculateParkingFee(3);

        if (fee == 9.0) {
            System.out.println("testMotorcycleParkingFee passed");
        } else {
            System.out.println("testMotorcycleParkingFee failed");
        }
    }

    public static void testVehicleParking() {
        ParkingSpot spot = new ParkingSpot("S10");
        Car car = new Car("C2", "TEST22", "Arjun", 4, "Hybrid");

        car.parkVehicle(spot);

        if (!spot.checkAvailability() && car.isParked()) {
            System.out.println("testVehicleParking passed");
        } else {
            System.out.println("testVehicleParking failed");
        }
    }

    public static void testTicketFeeCalculation() {
        ParkingSpot spot = new ParkingSpot("S11");
        Car car = new Car("C3", "TICKET1", "Gunraj", 4, "Gas");
        car.parkVehicle(spot);

        Ticket ticket = new Ticket("T100", car, spot, 10);
        double fee = ticket.calculateParkingFee(12);

        if (fee == 10.0) {
            System.out.println("testTicketFeeCalculation passed");
        } else {
            System.out.println("testTicketFeeCalculation failed");
        }
    }
}