package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository  extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthority(String authority);
}
