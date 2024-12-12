package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository  extends JpaRepository<Image, UUID> {
}
