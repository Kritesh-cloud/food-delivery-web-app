package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository  extends JpaRepository<Image, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM image ORDER BY created_at DESC LIMIT 1")
    Optional<Image> findLastImage();
}
