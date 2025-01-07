package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.MenuItemResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface MenuItemService {

    public BasicResponse addMenuItem(String menuItem, String menuCategoryId, double price);

    public List<MenuItemResponse> listMenuItemByOrder(String menuCategoryName);

    public List<MenuItemResponse> listMenuItemByOrder(String menuCategoryName, String restaurantId);

    public MenuItem getById(String id);

    public MenuItemResponse getByIdResponse(String id);

    public BasicResponse updateOrder(int currentOrder, int newOrder);

    public BasicResponse removeByItemId(String id) throws AccessDeniedException;

    public BasicResponse removeByMenuCategoryId(String id) throws AccessDeniedException;
}
