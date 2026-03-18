package org.ecommerce.productservice.infrastructure.in.rest;

import lombok.RequiredArgsConstructor;
import org.ecommerce.productservice.application.commands.CreateOrderItemRequest;
import org.ecommerce.productservice.application.commands.handlers.OrderCommandService;
import org.ecommerce.productservice.application.queries.OrderQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
  private final OrderCommandService orderService;
  private final OrderQueryService orderQueryService;

  @PostMapping()
  public ResponseEntity<?> addItem(@RequestBody CreateOrderItemRequest request) {
      var created = orderService.addOrderItem(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @GetMapping()
  public ResponseEntity<?> findAll() {
      return ResponseEntity.ok(orderQueryService.getAll());
  }
}
