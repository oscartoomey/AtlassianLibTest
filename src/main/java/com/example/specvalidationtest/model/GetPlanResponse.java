package com.example.specvalidationtest.model;

import java.util.List;

public record GetPlanResponse(String customerReference,
                              String channelIdentifier,
                              PlanDetails plan,
                              List<DebtItemChargeResponse> debtItemCharges,
                              List<PaymentResponse> payments,
                              List<CustomerPostCodeResponse> customerPostCodes,
                              List<InstalmentResponse> instalments,
                              PlanCollections collections
                         ) {
}