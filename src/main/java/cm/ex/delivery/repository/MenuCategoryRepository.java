package cm.ex.delivery.repository;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuCategoryRepository  extends JpaRepository<MenuCategory, Long> {

    List<MenuCategory> findByRestaurantId(Restaurant restaurantId);
}
