package com.example.megacitycab.model.combined;

public class RidePayment {
    private double totalAfterTax;
    private double tax;
    private double total;

    public RidePayment(double totalAfterTax, double tax, double total) {
        this.totalAfterTax = totalAfterTax;
        this.tax = tax;
        this.total = total;
    }

    public RidePayment() {

    }

    public double getTotalAfterTax() {
        return totalAfterTax;
    }

    public void setTotalAfterTax(double totalAfterTax) {
        this.totalAfterTax = totalAfterTax;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
