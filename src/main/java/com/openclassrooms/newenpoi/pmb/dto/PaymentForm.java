package com.openclassrooms.newenpoi.pmb.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentForm {
    
	@NotNull(message = "Connection cannot be null.")
	private Long connection;
	
	@NotNull(message = "Amount cannot be null.")
    private Double amount;
	
	@NotEmpty(message = "Description cannot be empty.")
    private String description;
}
