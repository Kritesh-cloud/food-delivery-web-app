package cm.ex.delivery.repository;

import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query(nativeQuery = true,
            value = "SELECT u.id, u.created_at, u.email, u.name, u.password, u.profile_url, u.updated_at\n" +
            "FROM users u\n" +
            "JOIN user__authorities ua ON u.id = ua.user_id\n" +
            "JOIN authority a ON ua.authority_id = a.id\n" +
            "WHERE a.authority = :authority")
    List<User> listByUserAuthority(String authority);
}
