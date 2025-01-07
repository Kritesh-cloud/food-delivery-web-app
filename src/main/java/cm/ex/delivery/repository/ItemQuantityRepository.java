package cm.ex.delivery.repository;

import cm.ex.delivery.entity.ItemQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemQuantityRepository extends JpaRepository<ItemQuantity, UUID> {

}
