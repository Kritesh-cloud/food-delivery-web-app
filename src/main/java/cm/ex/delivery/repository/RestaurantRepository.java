package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository  extends JpaRepository<Restaurant, UUID> {

    List<Restaurant> findByName(String name);

    Optional<Restaurant> findByContactNumber(String contactNumber);

    Optional<Restaurant> findByEmail(String email);

    Optional<Restaurant> findByOwnerId(User ownerId);
}
