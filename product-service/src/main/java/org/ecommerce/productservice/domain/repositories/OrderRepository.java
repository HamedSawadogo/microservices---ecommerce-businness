package org.ecommerce.productservice.domain.repositories;

import jakarta.persistence.LockModeType;
import org.ecommerce.productservice.application.queries.GetOrderResponse;
import org.ecommerce.productservice.domain.entities.orders.Order;
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
    List<GetOrderResponse> findAllByCreatedByUserId(Long userId);

    @Query("select o.id as id, o.orderStatus as orderStatuys, o.items as items " +
            " from Order o left join FETCH o.items i LEFT JOIN FETCH i.product  where o.id=:id")
    Optional<GetOrderResponse> findByProjectionId(Long id);
}
