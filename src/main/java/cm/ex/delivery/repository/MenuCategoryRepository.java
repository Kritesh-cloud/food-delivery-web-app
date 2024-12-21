package cm.ex.delivery.repository;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

    Optional<MenuCategory> findByName(String name);

    List<MenuCategory> findByRestaurantId(Restaurant restaurantId);

    @Query("SELECT mc FROM MenuCategory mc WHERE mc.restaurantId = :restaurant ORDER BY mc.categoryOrder ASC")
    List<MenuCategory> findByRestaurantOrdered(@Param("restaurant") Restaurant restaurant);

    @Query("SELECT mc FROM MenuCategory mc WHERE mc.categoryOrder = :categoryOrder AND mc.restaurantId = :restaurant")
    Optional<MenuCategory> findByCategoryOrderAndRestaurant(
            @Param("categoryOrder") int categoryOrder,
            @Param("restaurant") Restaurant restaurant
    );

    @Query("SELECT mc FROM MenuCategory mc WHERE mc.name = :name AND mc.restaurantId = :restaurantId")
    Optional<MenuCategory> findByNameAndRestaurant(
            @Param("name") String name,
            @Param("restaurantId") Restaurant restaurantId
    );

    @Query("SELECT mc FROM MenuCategory mc WHERE mc.id = :id AND mc.restaurantId = :restaurantId")
    Optional<MenuCategory> findByIdAndRestaurant(
            @Param("id") String id,
            @Param("restaurantId") Restaurant restaurantId
    );

}
