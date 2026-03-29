public class PaymentSystem {
    private String paymentId;
    private String paymentMethod;
    private double amount;

    public PaymentSystem(String paymentId, String paymentMethod, double amount) 
    {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public boolean validatePayment(double amount) 
    {
        return amount > 0;
    }

    //process payment by amount — throws InvalidPaymentException if amount is invalid
    public double processPayment(double amount) 
    {
        if (!validatePayment(amount)) {
            throw new InvalidPaymentException("Invalid payment amount: $" + amount);
        }
        System.out.println("Payment of $" + amount + " processed via " + paymentMethod + ".");
        return amount;
    }

    //process payment linked to a specific ticket
    public boolean processPayment(Ticket ticket, double amount) 
    {
        if (ticket == null || !validatePayment(amount)) 
        {
            throw new InvalidPaymentException("Invalid payment.");
        }
        if (ticket.isPaid()) {
            throw new InvalidPaymentException("Ticket " + ticket.getTicketId() + " has already been paid.");
        }
        System.out.println("Payment of $" + amount + " processed for ticket " + ticket.getTicketId());
        return true;
    }

    //generate receipt for a dollar amount
    public void generateReceipt(double amount) 
    {
        if (amount > 0) {
            System.out.println("Receipt: Paid $" + amount);
        } else {
            System.out.println("No valid payment made.");
        }
    }

    //generate detailed receipt linked to a ticket
    public void generateReceipt(Ticket ticket) 
    {
        if (ticket == null) {
            System.out.println("Cannot generate receipt. Ticket is null.");
            return;
        }
        System.out.println("========================================");
        System.out.println("               RECEIPT                  ");
        System.out.println("========================================");
        System.out.println("Ticket ID  : " + ticket.getTicketId());
        System.out.println("Vehicle    : " + ticket.getVehicle().getLicensePlate());
        System.out.println("Method     : " + paymentMethod);
        System.out.printf( "Amount Paid: $%.2f%n", ticket.getTotalFee());
        System.out.println("Status     : " + (ticket.isPaid() ? "PAID" : "UNPAID"));
        System.out.println("========================================");
    }

    public boolean refundPayment(double amount) 
    {
        if (amount <= 0) {
            System.out.println("Invalid refund amount.");
            return false;
        }
        System.out.println("Refunded $" + amount);
        return true;
    }

    public void displayPaymentStatus(boolean paid) 
    {
        if (paid) {
            System.out.println("Payment completed successfully.");
        } else {
            System.out.println("Payment pending.");
        }
    }
}