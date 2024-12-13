package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.response.BasicResponse;

import java.util.List;

public interface MenuCategoryService {

    public BasicResponse addMenuCategory(String category);

    public List<MenuCategory> listMenuCategoryByOrder();

    public BasicResponse updateOrder(int currentOrder, int newOrder);

    public BasicResponse removeMenuCategory(String category);

}
