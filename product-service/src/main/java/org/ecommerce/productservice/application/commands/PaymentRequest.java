package org.ecommerce.productservice.application.commands;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ecommerce.productservice.domain.aggregates.Order;
import org.ecommerce.productservice.domain.enums.PaymentMethod;
import org.ecommerce.productservice.domain.valueobjects.Money;

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
