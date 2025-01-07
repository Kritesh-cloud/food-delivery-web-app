package cm.ex.delivery.repository;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.IdHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IdHolderRepository  extends JpaRepository<IdHolder, Long> {

    List<IdHolder> findByBrowseContentId_Id(Long browseContentId);

    // Custom query method to fetch IdHolders by BrowseContent
    List<IdHolder> findByBrowseContentId(BrowseContent browseContentId);

    // Method to find IdHolder by dataId and browseContentId
    Optional<IdHolder> findByDataIdAndBrowseContentId(String dataId, BrowseContent browseContentId);
}
