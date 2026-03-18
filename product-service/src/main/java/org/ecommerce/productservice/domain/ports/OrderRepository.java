package org.ecommerce.productservice.domain.ports;

import jakarta.persistence.LockModeType;
import org.ecommerce.productservice.application.queries.OrderResponse;
import org.ecommerce.productservice.domain.aggregates.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.orderStatus='PENDING_FOR_PAYMENTS' and o.createdByUserId=:userId")
    List<Order> findByCreatedByUserIdInPending(@Param("userId") Long userId);

    @Query("select o  from Order o left join FETCH o.items i LEFT JOIN FETCH i.product  where o.createdByUserId=:userId")
    List<OrderResponse> findAllByCreatedByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o left join fetch o.items where o.orderStatus = 'PENDING_FOR_PAYMENTS' and o.id=:id")
    Optional<Order> findActiveOrderById(@Param("id") Long id);
}
