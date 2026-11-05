package com.example.specvalidationtest.model;

import java.util.List;

public record DebtItemChargeResponse(
        String debtItemChargeId,
        String mainTrans,
        String subTrans,
        double originalDebtAmount,
        String interestStartDate,
        List<PaymentHistoryResponse> paymentHistory
) {
}
