package org.ecommerce.productservice.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.command.orders.CreateOrderItemRequest;
import org.ecommerce.productservice.application.command.orders.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
  private final OrderService orderService;

  @PostMapping()
  public ResponseEntity<?> addItem(@RequestBody CreateOrderItemRequest request) {
      var created = orderService.addOrderItem(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping()
  @Transactional
  public ResponseEntity<?> findAll() {
      return ResponseEntity.ok(orderService.getAll());
  }
}
