public interface Parkable {

    /**
     * Parks the vehicle in the given spot.
     * mark the spot as occupied and update the vehicle's parked state.
     */
    void parkVehicle(ParkingSpot spot);

    /**
     * Removes the vehicle from the given spot.
     * Should mark the spot as unoccupied and update the vehicle's parked state.
     */
    void leaveSpot(ParkingSpot spot);

    /**
     * Calculates the parking fee based on the number of hours parked.
     *   Car        = $5.00 / hour
     *   Motorcycle = $3.00 / hour
     * return total fee as double
     */
    double calculateParkingFee(int hours);
}