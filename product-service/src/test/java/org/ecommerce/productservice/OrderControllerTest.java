package org.ecommerce.productservice;

import org.ecommerce.productservice.infrastructure.in.rest.OrderController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


@WebMvcTest(controllers = OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {


}
