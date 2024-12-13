package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Gallery;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GalleryRepository  extends JpaRepository<Gallery, UUID> {

    Optional<Gallery> findByRestaurantId(Restaurant restaurantId);

    Optional<Gallery> findByUserId(User userId);
}
