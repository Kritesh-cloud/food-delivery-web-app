package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.response.BasicResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface MenuItemService {

    public BasicResponse addMenuItem(String menuItem, String menuCategoryName, double price);

    public List<MenuItem> listMenuItemByOrder(String menuCategoryName);

    public MenuItem getById(String id);

    public BasicResponse updateOrder(int currentOrder, int newOrder);

    public BasicResponse removeByItemId(String id) throws AccessDeniedException;

    public BasicResponse removeByMenuCategoryId(String id) throws AccessDeniedException;
}
