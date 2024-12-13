package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface BrowseContentService {

    public BasicResponse createBrowseContent(String itemId, String itemType);

    public List<BrowseContent> listAllBrowseContentByOrder();

    public BrowseContent getBrowseContentById(String browseContentId);

    public BrowseContent getBrowseContentByTitle(String title);

    public BrowseContent getBrowseContentByType(String type);

    public BasicResponse updateOrder(int currentOrder, int newOrder);

    public BasicResponse removeBrowseContentItem(String browseContentId, String itemId);

    public BasicResponse addBrowseContentItem(String browseContentId, String itemId);

    public BasicResponse removeBrowseContentById(String browseContentId);

}