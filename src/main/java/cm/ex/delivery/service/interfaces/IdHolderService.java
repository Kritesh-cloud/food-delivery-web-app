package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.BrowseContent;
import cm.ex.delivery.entity.IdHolder;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface IdHolderService {

    public IdHolder addToIdHolder(String dataId, String dataType, BrowseContent browseContentId);

//    public IdHolder findById(String itemId);

    public List<IdHolder> listIdHolderByBrowseContentId(String browseContentId);

    public List<IdHolder> listAllIdHolder();

    public List<IdHolder> listAllIdHolderDivideByContentId();

    public BasicResponse removeByBrowseContentIdAndItemId(String browseContentId, String itemId);

    public BasicResponse removeByBrowseContentId(String browseContentId);
}
