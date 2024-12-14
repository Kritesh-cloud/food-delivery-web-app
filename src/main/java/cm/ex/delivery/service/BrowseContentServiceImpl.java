package cm.ex.delivery.service;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.repository.BrowseContentRepository;
import cm.ex.delivery.repository.IdHolderRepository;
import cm.ex.delivery.repository.UserRepository;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.service.interfaces.BrowseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BrowseContentServiceImpl implements BrowseContentService {

    @Autowired
    private BrowseContentRepository browseContentRepository;

    @Autowired
    private IdHolderServiceImpl idHolderService;

    @Override
    public BasicResponse createBrowseContent(String title, String itemType) {
        browseContentRepository.save(new BrowseContent(title, itemType));
        return BasicResponse.builder().status(true).code(200).message("Browse Content created successfully").build();
    }

    @Override
    public BasicResponse addItemToBrowseContent(String browseContentId, String itemId) {
        Optional<BrowseContent> browseContent = browseContentRepository.findById(Long.valueOf(browseContentId));
        if (browseContent.isEmpty()) throw new NoSuchElementException("Browse content not found");

        idHolderService.addToIdHolder(itemId, browseContent.get().getType(), browseContent.get());
        return BasicResponse.builder().status(true).code(200).message("Item added to Browse Content successfully").build();
    }

    @Override
    public List<BrowseContent> listAllBrowseContentByOrder() {
        List<BrowseContent> browseContentList = browseContentRepository.findAllByContentOrderAsc();
        return browseContentList.isEmpty() ? List.of() : browseContentList;
    }

    @Override
    public BrowseContent getBrowseContentById(String browseContentId) {
        Optional<BrowseContent> browseContent = browseContentRepository.findById(Long.valueOf(browseContentId));
        return browseContent.orElseGet(BrowseContent::new);
    }

    @Override
    public BrowseContent getBrowseContentByTitle(String title) {
        Optional<BrowseContent> browseContent = browseContentRepository.findByTitle(title);
        return browseContent.orElseGet(BrowseContent::new);
    }

    @Override
    public BasicResponse updateOrder(int currentOrder, int newOrder) {
        return null;
    }

    @Override
    public BasicResponse removeBrowseContentItem(String browseContentId, String itemId) {
        idHolderService.removeByBrowseContentIdAndItemId(browseContentId, itemId);
        return BasicResponse.builder().status(true).code(200).message("Item removed from Browse Content successfully").build();
    }

    @Override
    public BasicResponse removeBrowseContentById(String browseContentId) {
        Optional<BrowseContent> browseContent = browseContentRepository.findById(Long.valueOf(browseContentId));
        if (browseContent.isEmpty()) throw new NoSuchElementException("Browse content not found");

        browseContentRepository.delete(browseContent.get());
        return BasicResponse.builder().status(true).code(200).message("Browse Content deleted successfully").build();
    }
}
