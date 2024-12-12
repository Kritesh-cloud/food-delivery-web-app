package cm.ex.delivery.repository;

import cm.ex.delivery.entity.IdHolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;;
import java.util.UUID;

public interface IdHolderRepository  extends JpaRepository<IdHolder, Long> {

    List<IdHolder> findByDataIdAndDataType(UUID dataId, String dataType);

    List<IdHolder> findByLongDataIdAndDataType(Long longDataId, String dataType);
}
