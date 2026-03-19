package org.ecom.backend;

import org.ecom.backend.orders.web.OrderController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;


@WebMvcTest(controllers = OrderController.class)
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {


}
