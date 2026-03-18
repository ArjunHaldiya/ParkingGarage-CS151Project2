public class Ticket{
    String ticketId;
    Vehicle vehicle;
    ParkingSpot parkingSpot;
    int entryhour;
    int exithour;
    doube totalFee;
    boolean isPaid;
    
    void generateTicket(){
    }

    double calculateParkingFees(){
        return totalFee;
    }

    void markAsPaid(){
        isPaid = true;
    }

    String displayTicketDetails(){
        return "Ticket Id: " + ticketId + " | total fee: " + totalFee + " | Is Paid: " + isPaid ; 
    }

}