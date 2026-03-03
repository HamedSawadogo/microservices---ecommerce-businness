package org.ecommerce.productservice.application.services;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.BussinessException;
import org.ecommerce.productservice.application.dtos.CreateOderRequest;
import org.ecommerce.productservice.application.dtos.CreateOrderItemRequest;
import org.ecommerce.productservice.application.dtos.GetOrderResponse;
import org.ecommerce.productservice.application.repositories.OrderRepository;
import org.ecommerce.productservice.application.repositories.ProductRepository;
import org.ecommerce.productservice.domain.entities.orders.Order;
import org.ecommerce.productservice.domain.entities.orders.OrderItem;
import org.ecommerce.productservice.domain.entities.products.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  @Transactional
  public GetOrderResponse addOrderItem(CreateOrderItemRequest request) {
      Long userId = 1L;//TODO  should be changed
      Product product = productRepository.findOneForUpdate(request.productId()).orElseThrow();
      if (!product.isAvailableStock() || request.quantity() > product.getAvailableQuantity()) {
          throw new BussinessException("Product stock is out");
      }
      List<Order> orders = orderRepository.findByCreatedByUserIdInPending(userId);
      if (orders.isEmpty())  {
          Order order = Order.create(String.valueOf(userId), List.of(new OrderItem(product, request.quantity())));
          product.decreaseQuantity(request.quantity());
          orderRepository.save(order);
          return null;
      }
      var currentOderInPendingForPayment = orders.get(0);
      currentOderInPendingForPayment.addItem(new OrderItem(product, request.quantity()));
      product.decreaseQuantity(request.quantity());
      return null;
  }
}
