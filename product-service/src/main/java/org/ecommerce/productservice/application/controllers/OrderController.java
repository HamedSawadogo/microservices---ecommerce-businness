package org.ecommerce.productservice.application.controllers;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.dtos.CreateOrderItemRequest;
import org.ecommerce.productservice.application.repositories.OrderRepository;
import org.ecommerce.productservice.application.services.OrderService;
import org.ecommerce.productservice.domain.entities.orders.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
  private final OrderRepository orderRepository;
  private final OrderService orderService;

  @PostMapping()
  public ResponseEntity<?> addItem(@RequestBody CreateOrderItemRequest request) {
      var created = orderService.addOrderItem(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping()
  @Transactional
  public ResponseEntity<List<Order>> findAll() {
      return ResponseEntity.ok(orderRepository.findAll());
  }
}
