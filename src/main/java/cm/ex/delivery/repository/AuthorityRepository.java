package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository  extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(String name);
}
