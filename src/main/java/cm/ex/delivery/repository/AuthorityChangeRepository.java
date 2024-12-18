package cm.ex.delivery.repository;

import cm.ex.delivery.entity.AuthorityChange;
import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityChangeRepository extends JpaRepository<AuthorityChange, Long> {

    List<AuthorityChange> findByNewAuthority(String newAuthority);

    List<AuthorityChange> findByRestaurantId(Restaurant restaurantId);

    Optional<AuthorityChange> getAuthorityChangeByUserId(User userId);
}


