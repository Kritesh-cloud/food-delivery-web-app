package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BasketRepository extends JpaRepository<Basket, UUID>{

    Optional<Basket> findByOwnerId(UUID ownerId);
}
