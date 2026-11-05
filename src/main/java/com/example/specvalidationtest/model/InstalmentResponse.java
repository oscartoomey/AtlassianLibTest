package com.example.specvalidationtest.model;

public record InstalmentResponse(
        String debtItemChargeId,
        String dueDate,
        double amountDue,
        double expectedPayment,
        double interestRate,
        int instalmentNumber,
        double instalmentInterestAccrued,
        double instalmentBalance
) {
}
