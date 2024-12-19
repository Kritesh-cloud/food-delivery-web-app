package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Basket;
import cm.ex.delivery.entity.Order;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>{

    @Query("SELECT o FROM Order o WHERE o.buyer = :userId AND o.active = true")
    Optional<Order> findActiveOrderByUserId(@Param("userId") User userId);

    List<Order> findAllByStatus(String status);

    List<Order> findByBasketId(Basket basketId);

    List<Order> findByBuyerId(User buyer);

    List<Order> findByDeliveryPersonId(User deliveryPerson);

    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> listByRestaurantId(Restaurant restaurant);
}