package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Notification;
import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.senderId = :senderId ORDER BY n.createdAt ASC")
    List<Notification> findBySender(@Param("senderId") User senderId);

    @Query("SELECT n FROM Notification n JOIN n.userSet u WHERE u.id = :userId ORDER BY n.createdAt ASC")
    List<Notification> findByReceiver(@Param("userId") UUID userId);

}
