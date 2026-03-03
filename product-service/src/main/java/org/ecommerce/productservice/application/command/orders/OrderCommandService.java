package org.ecommerce.productservice.application.command.orders;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.domain.exceptions.BussinessException;
import org.ecommerce.productservice.application.queries.GetOrderResponse;
import org.ecommerce.productservice.domain.repositories.OrderRepository;
import org.ecommerce.productservice.domain.repositories.ProductRepository;
import org.ecommerce.productservice.domain.entities.orders.Order;
import org.ecommerce.productservice.domain.entities.orders.OrderItem;
import org.ecommerce.productservice.domain.entities.products.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCommandService {
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
      if (orders.isEmpty()) {
          Order order = Order.create(String.valueOf(userId), List.of(new OrderItem(product, request.quantity())));
          product.decreaseQuantity(request.quantity());
          Order saved = orderRepository.save(order);
          return orderRepository.findByProjectionId(saved.getId()).orElseThrow();
      }
      var currentOderInPendingForPayment = orders.get(0);
      currentOderInPendingForPayment.addItem(new OrderItem(product, request.quantity()));
      product.decreaseQuantity(request.quantity());
      return orderRepository.findByProjectionId(currentOderInPendingForPayment.getId()).orElseThrow();
  }

  @Transactional(readOnly = true)
  public List<GetOrderResponse> getAll() {
      return orderRepository.findAllByCreatedByUserId(1L);
  }
}
