package org.ecom.backend.orders.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.orderStatus='PENDING_FOR_PAYMENTS'")
    Optional<Order> findByCreatedByUserIdInPending();

    @Query(""" 
            select o
            from Order o left join FETCH o.items i
            where o.createdByUserId=:userId
    """)
    List<Order> findAllByCreatedByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o left join fetch o.items where o.orderStatus = 'PENDING_FOR_PAYMENTS'")
    Optional<Order> findActiveOrderById();
}
