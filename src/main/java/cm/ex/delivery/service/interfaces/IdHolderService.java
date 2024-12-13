package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.IdHolder;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface IdHolderService {

    public BasicResponse addToIdHolder(String id, String type);

    public List<IdHolder> listIdHolderByBrowseContentId(String browseContentId);

    public List<IdHolder> listAllIdHolder();

    public BasicResponse removeByBrowseContentIdAndItemId(String browseContentId, String itemId);

    public BasicResponse removeByBrowseContentId(String browseContentId);
}
