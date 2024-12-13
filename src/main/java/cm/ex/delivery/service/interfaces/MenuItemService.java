package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.entity.MenuItem;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface MenuItemService {

    public BasicResponse addMenuItem(MenuItem menuItem, MenuCategory menuCategory);

    public List<MenuItem> listMenuItemByOrder();

    public List<MenuItem> listByMenuCategory(MenuCategory menuCategory);

    public MenuItem getById(String id);

    public BasicResponse updateOrder(int currentOrder, int newOrder);

    public BasicResponse removeByItemId(String id);

    public BasicResponse removeByItemCategoryId(String id);
}
