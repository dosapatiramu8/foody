package com.foody.common.model.details;

import com.foody.common.model.response.payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetails {
    private String paymentId;
    private String transactionId;
    private String paymentMethod; // e.g., Credit Card, Debit Card, PayPal, UPI
    private PaymentStatus paymentStatus; // e.g., Completed, Pending, Failed
    private double totalAmount;
    private String currency; // e.g., USD, EUR, INR
    private LocalDateTime paymentDate;
    private String payerId; // User ID of the person making the payment
    private String payeeId; // User ID of the person receiving the payment
    private String paymentDescription; // Description or purpose of the payment
    private LocalDateTime lastUpdated; // Timestamp of the last update to the payment

    // Getters and Setters
}