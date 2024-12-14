package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Basket;
import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BasketRepository extends JpaRepository<Basket, UUID>{

    Optional<Basket> findByOwnerId(User ownerId);
}
