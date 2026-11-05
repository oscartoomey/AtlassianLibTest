package com.example.specvalidationtest;
import com.atlassian.oai.validator.report.JsonValidationReportFormat;
import com.atlassian.oai.validator.report.SimpleValidationReportFormat;
import jakarta.xml.bind.helpers.ValidationEventLocatorImpl;
import org.springframework.boot.test.context.SpringBootTest;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.Request;
import com.atlassian.oai.validator.model.Response;
import com.atlassian.oai.validator.model.SimpleRequest;
import com.atlassian.oai.validator.model.SimpleResponse;
import com.atlassian.oai.validator.report.ValidationReport;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpecValidationCoreLibTest {

    private static final OpenApiInteractionValidator VALIDATOR = OpenApiInteractionValidator
            .createForSpecificationUrl("time-to-pay-v1.0.0.yaml")
            .build();

    @Test
    void validRequestAndResponsePass() {
        Request request = SimpleRequest.Builder.post("/debts/time-to-pay/quote")
                .withHeader("Authorization", "Bearer Test")
                .withContentType("application/json")
                .withBody("""
                        {
                          "regimeType": "SA",
                          "customerReference": "uniqRef1234",
                          "channelIdentifier": "selfService",
                          "plan": {
                            "quoteType": "duration",
                            "quoteDate": "2021-05-13",
                            "instalmentStartDate": "2021-05-13",
                            "instalmentAmount": 100,
                            "frequency": "annually",
                            "duration": 12,
                            "initialPaymentAmount": 100,
                            "initialPaymentDate": "2021-05-13",
                            "paymentPlanType": "timeToPay"
                          },
                          "customerPostCodes": [
                            { "addressPostcode": "NW9 5XW", "postcodeDate": "2021-05-13" }
                          ],
                          "debtItemCharges": [
                            {
                              "debtItemChargeId": "debtItemChrgId1",
                              "mainTrans": "1546",
                              "subTrans": "1090",
                              "originalDebtAmount": 100,
                              "interestStartDate": "2021-05-13",
                              "paymentHistory": [
                                { "paymentDate": "2021-05-13", "paymentAmount": 100 }
                              ]
                            }
                          ]
                        }
                        """)
                .build();

        Response response = SimpleResponse.Builder.ok()
                .withContentType("application/json")
                .withBody("""
                        {
                          "quoteReference": "quoteRef1234",
                          "customerReference": "uniqRef1234",
                          "quoteType": "duration",
                          "quoteDate": "2021-05-13",
                          "numberOfInstalments": 2,
                          "totalDebtIncInt": 210.50,
                          "interestAccrued": 5.25,
                          "planInterest": 5.25,
                          "totalInterest": 10.50,
                          "instalments": [
                            {
                              "debtItemChargeId": "debtItemChrgId1",
                              "dueDate": "2021-06-13",
                              "amountDue": 105.25,
                              "expectedPayment": 105.25,
                              "interestRate": 2.75,
                              "instalmentNumber": 1,
                              "instalmentInterestAccrued": 2.60,
                              "instalmentBalance": 105.25
                            },
                            {
                              "debtItemChargeId": "debtItemChrgId1",
                              "dueDate": "2021-07-13",
                              "amountDue": 105.25,
                              "expectedPayment": 105.25,
                              "interestRate": 2.75,
                              "instalmentNumber": 2,
                              "instalmentInterestAccrued": 2.65,
                              "instalmentBalance": 0
                            }
                          ],
                          "collections": {
                            "initialCollection": { "dueDate": "2021-05-13", "amountDue": 100 },
                            "regularCollections": [
                              { "dueDate": "2021-06-13", "amountDue": 105.25 },
                              { "dueDate": "2021-07-13", "amountDue": 105.25 }
                            ]
                          }
                        }
                        """)
                .build();

        ValidationReport report = VALIDATOR.validate(request, response);

        //concise messaging
        String errors = report.getMessages().stream()
                .map(m -> m.getContext()
                        .flatMap(ValidationReport.MessageContext::getPointers)
                        .map(p -> p.getInstance())
                        .orElse("(no field)")
                        + " → " + m.getMessage())
                .collect(Collectors.joining("\n"));

        System.out.println(errors);

        assertFalse(report.hasErrors(), JsonValidationReportFormat.getInstance().apply(report));
    }

    @Test
    void invalidRequestAndResponsePass() {
        Request request = SimpleRequest.Builder.post("/debts/time-to-pay/quote")
                .withHeader("Authorization", "Bearer Test")
                .withContentType("application/json")
                .withBody("""
                        {
                          "regimeType": "SA",
                          "customerReference": "uniqRef1234",
                          "channelIdentifier": "selfService",
                          "plan": {
                            "quoteType": "duration",
                            "quoteDate": "2021-05-13",
                            "instalmentStartDate": "2021-05-13",
                            "instalmentAmount": 100,
                            "frequency": "annually",
                            "duration": "12",
                            "initialPaymentAmount": 100,
                            "initialPaymentDate": "2021-05-13",
                            "paymentPlanType": "timeToPay"
                          },
                          "customerPostCodes": [
                            { "addressPostcode": "NW9 5XW", "postcodeDate": "2021-05-13" }
                          ],
                          "debtItemCharges": [
                            {
                              "debtItemChargeId": "debtItemChrgId1",
                              "mainTrans": "1546",
                              "subTrans": "1090",
                              "originalDebtAmount": 100,
                              "interestStartDate": "2021-05-13",
                              "paymentHistory": [
                                { "paymentDate": "2021-05-13", "paymentAmount": 100 }
                              ]
                            }
                          ]
                        }
                        """)
                .build();

        Response response = SimpleResponse.Builder.ok()
                .withContentType("application/json")
                .withBody("""
                        {
                          "quoteReference": "quoteRef1234",
                          "customerReference": "uniqRef1234",
                          "quoteType": "duration",
                          "quoteDate": "2021-05-13",
                          "numberOfInstalments": 2,
                          "totalDebtIncInt": 210.50,
                          "interestAccrued": 5.25,
                          "planInterest": "5.25",
                          "totalInterest": 10.50,
                          "instalments": [
                            {
                              "debtItemChargeId": "debtItemChrgId1",
                              "dueDate": "2021-06-13",
                              "amountDue": 105.25,
                              "expectedPayment": 105.25,
                              "interestRate": 2.75,
                              "instalmentNumber": 1,
                              "instalmentInterestAccrued": 2.60,
                              "instalmentBalance": 105.25
                            },
                            {
                              "debtItemChargeId": "debtItemChrgId1",
                              "dueDate": "2021-07-13",
                              "amountDue": 105.25,
                              "expectedPayment": 105.25,
                              "interestRate": 2.75,
                              "instalmentNumber": 2,
                              "instalmentInterestAccrued": 2.65,
                              "instalmentBalance": 0
                            }
                          ],
                          "collections": {
                            "initialCollection": { "dueDate": "2021-05-13", "amountDue": 100 },
                            "regularCollections": [
                              { "dueDate": "2021-06-13", "amountDue": 105.25 },
                              { "dueDate": "2021-07-13", "amountDue": 105.25 }
                            ]
                          }
                        }
                        """)
                .build();

        ValidationReport report = VALIDATOR.validate(request, response);

        //concise messaging
        String errors = report.getMessages().stream()
                .map(m -> m.getContext()
                        .flatMap(ValidationReport.MessageContext::getPointers)
                        .map(p -> p.getInstance())
                        .orElse("(no field)")
                        + " → " + m.getMessage())
                .collect(Collectors.joining("\n"));

        System.out.println(errors);

        assertFalse(report.hasErrors(), JsonValidationReportFormat.getInstance().apply(report));
    }


}
