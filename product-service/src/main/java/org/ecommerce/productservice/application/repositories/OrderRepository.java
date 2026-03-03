package org.ecommerce.productservice.application.repositories;

import jakarta.persistence.LockModeType;
import org.ecommerce.productservice.domain.entities.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.orderStatus='PENDING_FOR_PAYMENTS' and o.createdByUserId=:userId")
    List<Order> findByCreatedByUserIdInPending(@Param("userId") Long userId);
}
