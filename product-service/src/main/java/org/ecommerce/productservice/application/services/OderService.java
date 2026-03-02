package org.ecommerce.productservice.application.services;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.BussinessException;
import org.ecommerce.productservice.application.dtos.CreateOderRequest;
import org.ecommerce.productservice.application.dtos.CreateOrderItemRequest;
import org.ecommerce.productservice.application.dtos.GetOrderResponse;
import org.ecommerce.productservice.application.repositories.OrderRepository;
import org.ecommerce.productservice.application.repositories.ProductRepository;
import org.ecommerce.productservice.domain.entities.orders.Order;
import org.ecommerce.productservice.domain.entities.products.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OderService {
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
          Order order = Order.create(request.userId(), List.of(request.item()));
          product.decreaseQuantity(request.quantity());
          return orderRepository.save(order);
      }
      var currentOderInPendingForPayment = orders.getFirst();
      currentOderInPendingForPayment.addItem(request.item());
      product.decreaseQuantity(request.quantity());
      return orderRepository.save(currentOderInPendingForPayment);
  }
}
