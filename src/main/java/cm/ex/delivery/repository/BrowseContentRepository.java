package cm.ex.delivery.repository;

import cm.ex.delivery.entity.BrowseContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrowseContentRepository  extends JpaRepository<BrowseContent, Long> {

    Optional<BrowseContent> findByTitle(String title);

    List<BrowseContent> findAllByTitle(String title);

    @Query("SELECT b FROM BrowseContent b ORDER BY b.contentOrder ASC")
    List<BrowseContent> findAllByContentOrderAsc();

//    BrowseContent findById(String browseContentId);
}
