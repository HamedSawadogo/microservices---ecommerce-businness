package org.ecommerce.productservice.application.controllers;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.repositories.OrderRepository;
import org.ecommerce.productservice.domain.entities.orders.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
  private final OrderRepository orderRepository;

  @PostMapping()
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
      var created = Order.create(String.valueOf(order.getCreatedByUserId()), order.getItems());
      return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(created));
  }

  @GetMapping()
  public ResponseEntity<List<Order>> findAll() {
      return ResponseEntity.ok(orderRepository.findAll());
  }
}
