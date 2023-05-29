package com.openclassrooms.newenpoi.pmb.dto;

import lombok.Data;

@Data
public class PaymentForm {
    private Long connection;
    private double amount;
    private String description;
}
