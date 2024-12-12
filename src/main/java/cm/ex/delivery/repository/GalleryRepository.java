package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GalleryRepository  extends JpaRepository<Gallery, UUID> {

    Optional<Gallery> findByOwnerIdAndOwnerType(String ownerId, String ownerType);
}
