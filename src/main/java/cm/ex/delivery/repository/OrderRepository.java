package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Basket;
import cm.ex.delivery.entity.Order;
import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>{


    List<Order> findAllByStatus(String status);

    List<Order> findByBasketId(Basket basketId);

    List<Order> findByOwnerId(User ownerId);
}
