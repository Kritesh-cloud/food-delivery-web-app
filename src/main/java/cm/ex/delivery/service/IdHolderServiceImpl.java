package cm.ex.delivery.service;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.IdHolder;
import cm.ex.delivery.repository.IdHolderRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.interfaces.IdHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IdHolderServiceImpl implements IdHolderService {

    @Autowired
    private IdHolderRepository idHolderRepository;

    @Override
    public IdHolder addToIdHolder(String dataId, String dataType, BrowseContent browseContentId) {
        return idHolderRepository.save(new IdHolder(dataId, dataType, browseContentId));
    }

    @Override
    public IdHolder findById(String itemId) {
        Optional<IdHolder> idHolder = idHolderRepository.findById(Long.valueOf(itemId));
        if (idHolder.isEmpty()) throw new NoSuchElementException("Item not found");
        return idHolder.get();
    }

    @Override
    public List<IdHolder> listIdHolderByBrowseContentId(String browseContentId) {
        List<IdHolder> idHolderList = idHolderRepository.findByBrowseContentId_Id(Long.valueOf(browseContentId));
        return idHolderList.isEmpty() ? List.of() : idHolderList;
    }

    @Override
    public List<IdHolder> listAllIdHolder() {
        List<IdHolder> idHolderList = idHolderRepository.findAll();
        return idHolderList.isEmpty() ? List.of() : idHolderList;
    }

    @Override
    public BasicResponse removeByBrowseContentIdAndItemId(String browseContentId, String itemId) {
        List<IdHolder> idHolderList = listIdHolderByBrowseContentId(browseContentId);
        IdHolder idHolder = findById(itemId);
        if (idHolderList.contains(idHolder)) {
            idHolderRepository.delete(idHolder);
            return BasicResponse.builder().status(true).code(200).message("Item deleted successfully").build();
        }
        return BasicResponse.builder().status(false).code(409).message("Unauthorized access to delete this item").build();
    }

    @Override
    public BasicResponse removeByBrowseContentId(String browseContentId) {
        List<IdHolder> idHolderList = idHolderRepository.findByBrowseContentId_Id(Long.valueOf(browseContentId));
        if (idHolderList.isEmpty())
            return BasicResponse.builder().status(true).code(200).message("No item in list to delete").build();

        idHolderRepository.deleteAllInBatch(idHolderList);
        return BasicResponse.builder().status(true).code(200).message("Item of given browse content id deleted successfully").build();
    }
}
