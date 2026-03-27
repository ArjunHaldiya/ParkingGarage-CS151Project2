import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private Truck truck;
    private ParkingSpot spot;
    private Ticket ticket;
    private ParkingGarage garage;

    @BeforeEach
    void setUp() {
        truck  = new Truck("T001", "TRUCK-01", "Arjun", 5, "Pickup", 2);
        spot   = new ParkingSpot("S01", "A1");
        ticket = new Ticket("TKT-0001", truck, spot, 8);
        garage = new ParkingGarage("Test Garage");
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

    // ── Ticket ──
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

    // ── ParkingGarage ──

    @Test void garageTest1() {
        assertTrue(garage.getParkingSpots().isEmpty());
        assertTrue(garage.getActiveTickets().isEmpty());
        assertTrue(garage.getParkedVehicles().isEmpty());

        garage.addParkingSpot(spot);
        assertEquals(1, garage.getParkingSpots().size());
        assertEquals(spot, garage.findAvailableSpot());

        // duplicate spot ID should be ignored
        garage.addParkingSpot(new ParkingSpot("S01", "dup"));
        assertEquals(1, garage.getParkingSpots().size());
    }

    // Verifies full park and remove vehicle flow updates all lists correctly
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

    // 101st spot must be rejected
    @Test void garage_100Cap() {
        for (int i = 0; i < 101; i++) {
            garage.addParkingSpot(new ParkingSpot("SP" + i, "L" + i));
        }
        assertEquals(100, ParkingGarage.MAXIMUM_INSTANCES);
        assertTrue(garage.getParkingSpots().size() <= ParkingGarage.MAXIMUM_INSTANCES);
    }

    // Verifies display methods and toString don't throw
    @Test void garage_outputMethods() {
        garage.addParkingSpot(spot);
        garage.parkVehicle(truck, 9);
        assertDoesNotThrow(() -> garage.displayAvailableSpots());
        assertDoesNotThrow(() -> garage.displayGarageStatus());
        assertTrue(garage.toString().contains("Test Garage"));
    }

    // ── Parkable
    @Test void parkable_Truck() {
        Parkable p = truck;

        assertEquals(24.00, p.calculateParkingFee(3), 0.001); // $8 * 3hrs

        p.parkVehicle(spot);
        assertTrue(spot.isOccupied());

        p.leaveSpot(spot);
        assertFalse(spot.isOccupied());
    }
}