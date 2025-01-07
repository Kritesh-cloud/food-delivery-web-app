package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Restaurant;
import cm.ex.delivery.entity.StaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffIdRepository extends JpaRepository<StaffId, Long> {

    // Query to find all staff IDs by restaurant ID
    @Query("SELECT s.staffId FROM StaffId s WHERE s.restaurant.id = :restaurantId")
    List<String> findStaffIdsByRestaurantId(@Param("restaurantId") UUID restaurantId);

    // Optional: Find all StaffId entities by restaurant ID
    List<StaffId> findByRestaurantId(UUID restaurantId);

    Optional<StaffId> findByStaffIdAndRestaurant(String staffId, Restaurant restaurant);

}
//List<StaffId> findByBrowseContentId_Id(Long browseContentId);