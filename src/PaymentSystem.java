// Name: Fnu Hasham
//missing classes added that to the code
public class PaymentSystem 
{
    private String paymentId;
    private String paymentMethod;
    private double amount;

    //constructor
    public PaymentSystem(String paymentId, String paymentMethod, double amount) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    //getters & setters
    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public void setPaymentId(String paymentId) 
    {
        this.paymentId = paymentId;
    }

    public void setPaymentMethod(String paymentMethod) 
    {
        this.paymentMethod = paymentMethod;
    }

    public void setAmount(double amount) 
    {
        this.amount = amount;
    }

    //processsing a payment
    public double processPayment(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return 0;
        }
        this.amount = amount;
        System.out.println("Payment of $" + amount + " processed.");
        return amount;
    }

    //validating amount
    public boolean validatePayment(double amount) {
        return amount > 0;
    }

    //printing receipt
    public void generateReceipt(double amount) {
        System.out.println("Receipt: Paid $" + amount);
    }

    //refund
    public void refundPayment(double amount) {
        System.out.println("Refunded $" + amount);
    }

    //system status
    public void displayPaymentStatus() {
        System.out.println("Payment system is active.");
    }

    @Override
    public String toString() {
        return "PaymentSystem{paymentId='" + paymentId +
               "', method='" + paymentMethod +
               "', amount=" + amount + "}";
    }
}