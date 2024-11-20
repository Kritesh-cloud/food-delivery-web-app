package cm.ex.delivery.repository;

import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    @Query( nativeQuery = true, value = "SELECT * FROM users u WHERE u.id = :userId")
    Optional<User> findByUserId(UUID userId);

    @Query(nativeQuery = true, value = "SELECT * FROM user u WHERE u.id = UNHEX(REPLACE(:id, '-', '')) LIMIT 1")
    User findUserByUserId(String id);

    @Query( nativeQuery = true, value = "SELECT u.* FROM users u " +
            "JOIN user_authority_list ual ON u.id = ual.user_id " +
            "JOIN user_authority ua ON ua.id = ual.authority_id " +
            "WHERE ua.authority = :authority")
    List<User> findUserByAuthority(@Param("authority") String authority);
}

/*

SELECT u.* FROM users u JOIN user_authority_list ual ON u.id = ual.user_id JOIN user_authority ua ON ua.id = ual.authority_id WHERE ua.authority = 'moderator';
SELECT u.* FROM users u JOIN user_authority_list ual ON u.id = ual.user_id JOIN user_authority ua ON ua.id = ual.authority_id WHERE ua.authority = :authority

*/