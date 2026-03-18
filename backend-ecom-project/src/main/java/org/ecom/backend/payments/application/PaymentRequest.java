package org.ecom.backend.payments.application;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecom.backend.orders.domain.Order;
import org.ecom.backend.payments.domain.PaymentMethod;
import org.ecom.backend.comons.valueobjects.Money;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @NotNull
    private Money amount;
    @NotNull
    private PaymentMethod paymentMethod;
    @Nullable
    private Order order;
}
