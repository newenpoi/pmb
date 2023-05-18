package com.openclassrooms.newenpoi.pmb.dto;

import lombok.Data;

@Data
public class PaymentForm {
    private Long connection;
    private Integer amount;
    private String description;
}
