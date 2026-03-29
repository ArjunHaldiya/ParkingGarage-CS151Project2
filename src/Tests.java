import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private PickupTruck truck;
    private Car car;
    private Motorcycle motorcycle;
    private ElectricVehicle ev;
    private ParkingSpot spot;
    private Ticket ticket;
    private ParkingGarage garage;

    @BeforeEach
    void setUp() {
        truck      = new PickupTruck("T001", "TRUCK-01", "Arjun", 5, "Pickup", 2);
        car        = new Car("C001", "ABC123", "Gunraj", 4, "Gas");
        motorcycle = new Motorcycle("M001", "XYZ999", "Hasham", 300, true);
        ev         = new ElectricVehicle("E001", "EV-001", "Arjun", 15, 200, "DC Fast");
        spot       = new ParkingSpot("S01");
        ticket     = new Ticket("TKT-0001", truck, spot, 8);
        garage     = new ParkingGarage("Test Garage");
    }

        // ===== CAR TESTS =====

    @Test void car_parkingFee() {
        assertEquals(10.00, car.calculateParkingFee(2), 0.001);  // $5/hr * 2
        assertEquals(5.00,  car.calculateParkingFee(0), 0.001);  // min 1 hour
        assertEquals(5.00,  car.calculateParkingFee(1), 0.001);
    }

    @Test void car_parkingFlow() {
        car.parkVehicle(spot);
        assertFalse(spot.checkAvailability());
        assertTrue(car.isParked());
        assertEquals(car, spot.getAssignedVehicle());

        car.leaveSpot(spot);
        assertTrue(spot.checkAvailability());
        assertFalse(car.isParked());
    }

    @Test void car_constructorAndGetters() {
        assertEquals("C001", car.getVehicleId());
        assertEquals("ABC123", car.getLicensePlate());
        assertEquals("Gunraj", car.getOwnerName());
        assertEquals(4, car.getNumberOfDoors());
        assertEquals("Gas", car.getFuelType());
        assertFalse(car.isParked());
    }

    @Test void car_outputMethods() {
        assertTrue(car.toString().contains("ABC123"));
        assertDoesNotThrow(() -> car.displayVehicleInfo());
    }

    // ===== MOTORCYCLE TESTS =====

    @Test void motorcycle_parkingFee() {
        assertEquals(9.00, motorcycle.calculateParkingFee(3), 0.001);  // $3/hr * 3
        assertEquals(3.00, motorcycle.calculateParkingFee(0), 0.001);  // min 1 hour
        assertEquals(3.00, motorcycle.calculateParkingFee(1), 0.001);
    }

    @Test void motorcycle_parkingFlow() {
        motorcycle.parkVehicle(spot);
        assertTrue(spot.isOccupied());
        assertTrue(motorcycle.isParked());

        motorcycle.leaveSpot(spot);
        assertFalse(spot.isOccupied());
        assertFalse(motorcycle.isParked());
    }

    @Test void motorcycle_constructorAndGetters() {
        assertEquals("M001", motorcycle.getVehicleId());
        assertEquals(300, motorcycle.getEngineSize());
        assertTrue(motorcycle.hasHelmetStorage());
    }

    // ===== ELECTRIC VEHICLE TESTS =====

    @Test void ev_parkingFee() {
        assertEquals(8.00, ev.calculateParkingFee(2), 0.001);   // $4/hr * 2
        assertEquals(4.00, ev.calculateParkingFee(0), 0.001);   // min 1 hour
    }

    @Test void ev_parkingFlow() {
        ev.parkVehicle(spot);
        assertTrue(spot.isOccupied());
        assertTrue(ev.isParked());

        ev.leaveSpot(spot);
        assertFalse(spot.isOccupied());
        assertFalse(ev.isParked());
    }

    @Test void ev_batteryAndCharging() {
        // battery level 15 → needsCharging = true
        assertTrue(ev.isNeedsCharging());
        assertDoesNotThrow(() -> ev.requestChargingSpot());

        // battery update
        ev.setBatteryLevel(80);
        assertFalse(ev.isNeedsCharging());
        assertTrue(ev.checkBatteryLevel().contains("high"));
    }

    // ===== TICKET TESTS (Car) =====

    @Test void ticket_carFeeCalculation() {
        ParkingSpot carSpot = new ParkingSpot("S02");
        car.parkVehicle(carSpot);
        Ticket carTicket = new Ticket("TKT-0002", car, carSpot, 10);

        double fee = carTicket.calculateParkingFee(12); // 2 hrs * $5 = $10
        assertEquals(10.00, fee, 0.001);
    }

    // ===== EXCEPTION TESTS =====

    @Test void exception_spotOccupied() {
        spot.assignVehicle(car);
        assertThrows(SpotOccupiedException.class, () -> spot.assignVehicle(truck));
    }

    @Test void exception_invalidPayment() {
        PaymentSystem ps = new PaymentSystem("P1", "Cash", 0);
        assertThrows(InvalidPaymentException.class, () -> ps.processPayment(-5.0));
    }

    @Test void exception_vehicleNotFound() {
        assertThrows(VehicleNotFoundException.class,
                () -> garage.removeVehicle("TKT-FAKE", 10, "Cash"));
    }

    @Test void exception_invalidLicensePlate() {
        assertThrows(InvalidLicensePlateException.class,
                () -> car.setLicensePlate(""));
        assertThrows(InvalidLicensePlateException.class,
                () -> car.setLicensePlate("AB")); // too short (< 3 chars)
    }

    @Test void truck_constructorAndGetters() {
        assertEquals("T001", truck.getVehicleId());
        assertEquals("TRUCK-01", truck.getLicensePlate());
        assertEquals("Arjun", truck.getOwnerName());
        assertEquals(5, truck.getPayloadCapacity());
        assertEquals("Pickup", truck.getTruckType());
        assertEquals(2, truck.getNumberOfAxles());
        assertFalse(truck.isParked());
    }

    @Test void truck_parkingFlowAndSetters() {
        assertEquals(32.00, truck.calculateParkingFee(4), 0.001); // $8/hr * 4
        assertEquals(8.00,  truck.calculateParkingFee(0), 0.001); // min 1 hour

        truck.parkVehicle(spot);
        assertTrue(spot.isOccupied());
        assertEquals(truck, spot.getAssignedVehicle());

        truck.leaveSpot(spot);
        assertFalse(spot.isOccupied());
        assertNull(spot.getAssignedVehicle());

        truck.setTruckType("Flatbed");
        truck.setPayloadCapacity(20);
        truck.setNumberOfAxles(6);
        assertEquals("Flatbed", truck.getTruckType());
        assertEquals(20, truck.getPayloadCapacity());
        assertEquals(6, truck.getNumberOfAxles());
    }

    @Test void truck_outputMethods() {
        assertTrue(truck.toString().contains("TRUCK-01"));
        assertTrue(truck.toString().contains("Pickup"));
        assertDoesNotThrow(() -> truck.displayVehicleInfo());
    }

    @Test void ticket_defaultStateAndPaymentFlow() {
        assertEquals("TKT-0001", ticket.getTicketId());
        assertEquals(-1, ticket.getExitHour());
        assertFalse(ticket.isPaid());
        assertEquals(0.0, ticket.getTotalFee(), 0.001);

        double fee = ticket.calculateParkingFee(11); // 3hrs * $8 = $24
        assertEquals(24.00, fee, 0.001);
        assertEquals(11, ticket.getExitHour());
        assertEquals(24.00, ticket.getTotalFee(), 0.001);

        ticket.markAsPaid();
        assertTrue(ticket.isPaid());
    }

    @Test void ticket_minimumFeeAndOutputMethods() {
        assertEquals(8.00, ticket.calculateParkingFee(8), 0.001); // same hour = 1hr min
        assertDoesNotThrow(() -> ticket.generateTicket());
        assertDoesNotThrow(() -> ticket.displayTicketDetails());
        assertTrue(ticket.toString().contains("TKT-0001"));
    }

    @Test void garageTest1() {
        assertTrue(garage.getParkingSpots().isEmpty());
        assertTrue(garage.getActiveTickets().isEmpty());
        assertTrue(garage.getParkedVehicles().isEmpty());

        garage.addParkingSpot(spot);
        assertEquals(1, garage.getParkingSpots().size());
        assertEquals(spot, garage.findAvailableSpot());

        // duplicate spot ID should be ignored
        garage.addParkingSpot(new ParkingSpot("S01"));
        assertEquals(1, garage.getParkingSpots().size());
    }

    @Test void garageTest2() {
        garage.addParkingSpot(spot);

        Ticket t = garage.parkVehicle(truck, 9);
        assertNotNull(t);
        assertEquals(1, garage.getParkedVehicles().size());
        assertEquals(1, garage.getActiveTickets().size());
        assertTrue(spot.isOccupied());
        assertNull(garage.findAvailableSpot()); // no spots left

        garage.removeVehicle(t.getTicketId(), 12, "Cash");
        assertTrue(garage.getParkedVehicles().isEmpty());
        assertTrue(garage.getActiveTickets().isEmpty());
        assertFalse(spot.isOccupied());
    }

    @Test void garage_100Cap() {
        for (int i = 0; i < 101; i++) {
            garage.addParkingSpot(new ParkingSpot("SP" + i));
        }
        assertEquals(100, ParkingGarage.MAXIMUM_INSTANCES);
        assertTrue(garage.getParkingSpots().size() <= ParkingGarage.MAXIMUM_INSTANCES);
    }

    @Test void garage_outputMethods() {
        garage.addParkingSpot(spot);
        garage.parkVehicle(truck, 9);
        assertDoesNotThrow(() -> garage.displayAvailableSpots());
        assertDoesNotThrow(() -> garage.displayGarageStatus());
        assertTrue(garage.toString().contains("Test Garage"));
    }

    @Test void parkable_Truck() {
        Parkable p = truck;

        assertEquals(24.00, p.calculateParkingFee(3), 0.001); // $8 * 3hrs

        p.parkVehicle(spot);
        assertTrue(spot.isOccupied());

        p.leaveSpot(spot);
        assertFalse(spot.isOccupied());
    }
}