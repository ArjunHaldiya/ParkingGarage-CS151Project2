// Name: Fnu Hasham

public double processPayment(double amount) {
    if (amount <= 0) {
        System.out.println("Invalid payment amount.");
        return 0;
    }
    System.out.println("Payment of $" + amount +" processed.");
    return amount;
}

public boolean validatePayment(double amount) {
    return amount > 0;
}

public void printReceipt(double amount) {
    System.out.println("Receipt: Paid $" + amount);
}

public void refundPayment(double amount) {
    System.out.println("Refunded $" + amount);
}

public void displayPaymentStatus() {
    System.out.println("Payment system is active.");
}