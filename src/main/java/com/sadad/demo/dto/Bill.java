package com.sadad.demo.dto;

import java.util.Objects;

public class Bill {
    private String fullName;
    private double amount;
    private String accountNumber;

    public Bill() {
    }

    public Bill(String fullName, long amount, String accountNumber) {
        this.fullName = fullName;
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public Bill setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Bill setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Bill setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Bill bill = (Bill) obj;
        return Objects.equals(fullName, bill.fullName) && Objects.equals(accountNumber, bill.accountNumber) && Objects.equals(amount, bill.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, amount, accountNumber);
    }
}
