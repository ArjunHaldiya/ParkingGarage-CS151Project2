public class PaymentSystem {

    public double processPayment(double amount) {
        if (!validatePayment(amount)) {
            System.out.println("Invalid payment amount.");
            return 0;
        }

        System.out.println("Payment of $" + amount + " processed.");
        return amount;
    }

    public boolean processPayment(Ticket ticket, double amount) {
        if (ticket == null || !validatePayment(amount)) {
            System.out.println("Invalid payment.");
            return false;
        }

        System.out.println("Payment of $" + amount + " processed for ticket " + ticket.getTicketId());
        return true;
    }

    public boolean validatePayment(double amount) {
        return amount > 0;
    }

    public void printReceipt(double amount) {
        if (amount > 0) {
            System.out.println("Receipt: Paid $" + amount);
        } else {
            System.out.println("No valid payment made.");
        }
    }

    public void generateReceipt(Ticket ticket) {
        if (ticket == null) {
            System.out.println("Cannot generate receipt. Ticket is null.");
            return;
        }

        System.out.println("========================================");
        System.out.println("               RECEIPT                  ");
        System.out.println("========================================");
        System.out.println("Ticket ID  : " + ticket.getTicketId());
        System.out.printf("Amount Paid: $%.2f%n", ticket.getTotalFee());
        System.out.println("Status     : " + (ticket.isPaid() ? "PAID" : "UNPAID"));
        System.out.println("========================================");
    }

    public boolean refundPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid refund amount.");
            return false;
        }

        System.out.println("Refunded $" + amount);
        return true;
    }

    public void displayPaymentStatus(boolean paid) {
        if (paid) {
            System.out.println("Payment completed successfully.");
        } else {
            System.out.println("Payment pending.");
        }
    }
}