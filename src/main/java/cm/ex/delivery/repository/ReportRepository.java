package cm.ex.delivery.repository;

import cm.ex.delivery.entity.Report;
import cm.ex.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r WHERE r.senderId = :senderId ORDER BY r.createdAt ASC")
    List<Report> findBySender(@Param("senderId") User senderId);
}
