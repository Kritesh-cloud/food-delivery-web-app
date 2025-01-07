package cm.ex.delivery.service.interfaces;

import cm.ex.delivery.entity.MenuCategory;
import cm.ex.delivery.response.BasicResponse;
import cm.ex.delivery.response.MenuCategoryResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface MenuCategoryService {

    public BasicResponse addMenuCategory(String category) throws AccessDeniedException;

    public List<MenuCategoryResponse> listMenuCategoryByOrder() throws AccessDeniedException;

    public List<MenuCategoryResponse> listMenuCategoryByOrder(String restaurantId);

    public BasicResponse updateOrder(int currentOrder, int newOrder) throws AccessDeniedException;

    public BasicResponse removeMenuCategory(String menuCategoryId);

}
