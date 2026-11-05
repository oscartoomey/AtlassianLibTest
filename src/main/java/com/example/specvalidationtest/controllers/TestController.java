package com.example.specvalidationtest.controllers;
import com.example.specvalidationtest.model.*;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    @GetMapping("/debts/time-to-pay/quote/{customerReference}/{planId}")
    @ResponseStatus(HttpStatus.OK)
    public GetPlanResponse getTest(@PathVariable String customerReference, @PathVariable String planId) {
        PlanDetails plan = new PlanDetails(
                "caseId1",
                "planId1",
                "quoteId1",
                "2021-05-13",
                "instalmentAmount",
                "timeToPay",
                true,
                0,
                "BACS",
                "paymentRef123",
                0,
                0,
                0,
                0.24
        );

        PaymentHistoryResponse payment = new PaymentHistoryResponse(
                "2021-05-31",
                24.56
        );

        DebtItemChargeResponse debt = new DebtItemChargeResponse(
                "debtItemChrgId1",
                "1532",
                "1090",
                100,
                "2021-05-13",
                List.of(payment)
        );

        PaymentResponse payments = new PaymentResponse(
                "BACS",
                "paymentRef123"
        );

        CustomerPostCodeResponse customerPostcode = new CustomerPostCodeResponse(
                "NW9 5XW",
                "2021-05-13"
        );

        InstalmentResponse instalments = new InstalmentResponse(
                "debtItemChrgId1",
                "2021-05-13",
                100,
                10,
                0.25,
                1,
                10,
                100
        );

        CollectionModel initialCollection = new CollectionModel(
                "2022-06-18",
                1000
        );

        CollectionModel regularCollection = new CollectionModel(
                "2022-06-19",
                2000
        );

        PlanCollections planCollections = new PlanCollections(
                initialCollection,
                List.of(regularCollection)
        );

        return new GetPlanResponse(
                customerReference,
                "advisor",
                plan,
                List.of(debt),
                List.of(payments),
                List.of(customerPostcode),
                List.of(instalments),
                planCollections
        );
    }
}
