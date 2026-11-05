package com.example.specvalidationtest.model;

public record PlanDetails(String caseId,
                          String planId,
                          String quoteId,
                          String quoteDate,
                          String quoteType,
                          String paymentPlanType,
                          boolean thirdPartyBank,
                          int numberOfInstalments,
                          String initialPaymentMethod,
                          String initialPaymentReference,
                          int totalDebtIncInt,
                          double totalInterest,
                          int interestAccrued,
                          double planInterest) {
}
