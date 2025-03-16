package com.affiliateSWD.affiliate_marketing.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PaymentRequest {
    @NotNull(message = "Amount cannot be null")
    private Double amount;
}
